package com.rapidapi.core.infrastructure.server;

import java.net.URL;
import java.security.Security;


import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.ee11.servlet.DefaultServlet;
import org.eclipse.jetty.ee11.servlet.ServletContextHandler;
import org.eclipse.jetty.ee11.servlet.ServletHolder;
import org.eclipse.jetty.ee11.servlet.SessionHandler;
import org.eclipse.jetty.http.UriCompliance;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.GracefulHandler;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.servlet.ServletContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.rapidapi.core.config.ApplicationConfig;
import com.rapidapi.core.config.SSLConfig;
import com.rapidapi.core.config.SecurityConfig;
import com.rapidapi.core.config.ServerConfig;
import com.rapidapi.core.config.JwtConfig;
import com.rapidapi.core.config.CryptoConfig;
import com.rapidapi.core.config.TemplateConfig;
import com.rapidapi.core.infrastructure.config.ConfigProvider;
import com.rapidapi.core.infrastructure.loader.TemplateEngineFactory;



/**
 * Jetty 12 embedded server with: - HTTP -> HTTPS automatic redirect for ALL
 * paths - HTTP/2 over TLS (ALPN) - Graceful shutdown (in-flight requests) -
 * Clean CDI/Weld lifecycle
 */
public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    // === Jetty Server ===
    private Server server;
    private QueuedThreadPool threadPool;

    public JettyServer() {
        threadPool = new QueuedThreadPool();
        threadPool.setName("server");
        server = new Server(threadPool);
    }

    public void start() throws Exception {
        // === Load configurations manually ===
        var configProvider = new ConfigProvider();
        var jwtConfig = new JwtConfig(configProvider);
        var cryptoConfig = new CryptoConfig(configProvider);
        var securityConfig = new SecurityConfig(jwtConfig, cryptoConfig);
        var serverConfig = new ServerConfig(configProvider);
        var appConfig = new ApplicationConfig(configProvider);
        var sslConfig = new SSLConfig(configProvider);
        logger.info("Configurations loaded successfully");

        logger.info("Starting {} {} ({})", appConfig.getName(), appConfig.getVersion(), appConfig.getEnvironment());

        // Security providers will be configured by servlet context listener

        // Graceful shutdown of in-flight requests
        // Graceful shutdown is handled via server.setStopTimeout() + shutdown hook
        GracefulHandler graceful = new GracefulHandler();
        server.setHandler(graceful);
        server.setStopTimeout(serverConfig.getStopTimeout());
        // server.setStopAtShutdown(true); // Backup if shutdown hook fails

        // ---- HTTP and HTTPS configurations ----
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(serverConfig.getHttpsPort());
        httpConfig.setSendServerVersion(true);
        httpConfig.setSendXPoweredBy(false);
        httpConfig.setSendDateHeader(false);
        httpConfig.setUriCompliance(UriCompliance.RFC3986);

        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        src.setStsMaxAge(31536000); // HSTS 1 year
        src.setSniHostCheck(false);
        src.setSniRequired(false);
        httpsConfig.addCustomizer(src);

        // ---- SSL Context ----
        SslContextFactory.Server ssl = new SslContextFactory.Server();
        
        // Load keystore from classpath if path is classpath-relative
        URL ksUrl = getResourceFromClasspath(sslConfig.getKeystorePath());
        ssl.setKeyStorePath(ksUrl != null ? ksUrl.toString() : sslConfig.getKeystorePath());
        ssl.setKeyStorePassword(sslConfig.getKeystorePassword());
        ssl.setKeyManagerPassword(sslConfig.getKeyManagerPassword());
        
        // ALPN + HTTP/2 over TLS
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol("h2");
        
        // HTTPS (8443): ssl -> alpn -> http/1.1 (HTTP/2 will be negotiated by ALPN)
        ServerConnector https = new ServerConnector(server, new SslConnectionFactory(ssl, alpn.getProtocol()), alpn,
                new HttpConnectionFactory(httpsConfig));
        https.setName("HTTPS");
        https.setPort(serverConfig.getHttpsPort());
        https.setIdleTimeout(serverConfig.getIdleTimeout());
        
        // HTTP (8080) plain: redirects to HTTPS
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        http.setName("HTTP");
        http.setPort(serverConfig.getHttpPort());
        http.setIdleTimeout(serverConfig.getIdleTimeout());

        server.setConnectors(new Connector[] { https, http });

        // ---- App Handlers ----
        // 1) All insecure requests are redirected to HTTPS
        SecuredRedirectHandler redirectAll = new SecuredRedirectHandler();
        
        // 2) Your webapp context (static + Jersey) with session support
        ServletContextHandler app = new ServletContextHandler(ServletContextHandler.SESSIONS);
        app.setContextPath(serverConfig.getContextPath());
        
        // Configure session management
        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setMaxInactiveInterval(serverConfig.getIdleTimeout() / 1000);
        app.setSessionHandler(sessionHandler);

        // CDI is initialized manually in Bootstrap, no need for servlet listener

        // Static resources (optional)
        app.addServlet(DefaultServlet.class, "/static/*");

        // Jersey REST + MVC under /api/* and /domains/*
        ServletHolder jersey = app.addServlet(ServletContainer.class, "/*");
        jersey.setInitOrder(0);
        
        String packageScan = "com.rapidapi.core,com.rapidapi.domains";
        logger.info("Jersey scanning packages: {}", packageScan);
        jersey.setInitParameter("jersey.config.server.provider.packages", packageScan);
        jersey.setInitParameter("jersey.config.servlet.filter.forwardOn404", "true");
        jersey.setInitParameter("jersey.config.server.tracing.type", "ALL");
        jersey.setInitParameter("jersey.config.server.tracing.threshold", "VERBOSE");
        
        // Enable Jakarta MVC with Krazo - configurable template engine
        var templateConfig = new TemplateConfig(configProvider);
        var templateFactory = new TemplateEngineFactory(templateConfig);
        String[] providers = templateFactory.getRequiredProviders();
        String providerList = String.join(",", providers);
        logger.info("Jersey providers: {}", providerList);
        jersey.setInitParameter("jersey.config.server.provider.classnames", providerList);
        
        // MVC Configuration
        jersey.setInitParameter("jakarta.mvc.engine.ViewEngine.viewFolder", "/templates/");
        jersey.setInitParameter("jersey.config.server.wadl.disableWadl", "true");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.addHandler(app);
        
        // Compose handler chain: redirect first, then app contexts
        Handler.Sequence chain = new Handler.Sequence();
        chain.setHandlers(new Handler[] { redirectAll, contexts });
        graceful.setHandler(chain);

        StatisticsHandler statisticsHandler = new StatisticsHandler();
        statisticsHandler.setServer(server);

        // Optionally add a JVM shutdown hook to call server.stop() explicitly
        // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        //     logger.info("JVM shutdown hook: stopping Jetty...");
        //     try {
        //         server.stop();
        //     } catch (Exception e) {
        //         logger.warn("Error while stopping Jetty", e);
        //     }
        // }, "jetty-shutdown"));

        // Add graceful shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (server.isRunning()) {
                    logger.info("Graceful shutdown initiated...");
                    server.stop();
                }
            } catch (Exception e) {
                logger.error("Error during graceful shutdown", e);
            }
        }, "jetty-graceful-shutdown"));

        try {
            server.start();
            logger.info(
                    "\n\r🌐🌐🌐🌐🌐 Server 🌐🌐🌐🌐🌐 started  on ports HTTP: {}  HTTPS: {} 🚀🚀🚀\uD83D\uDE80\uD83D\uDE80",
                    http.getLocalPort(), https.getLocalPort());
            server.join();
        } catch (Throwable t) {
            logger.error("Failed to start server", t);
            // try {
            // server.stop();
            // } catch (Exception e) {
            // logger.warn("Error while stopping Jetty server", e);
            // }
            throw t;
        }
    }

    public void stop() throws Exception {
        logger.info("Shutting down server...");

        if (server != null && server.isRunning()) {
            server.stop();
        }

        logger.info("Server shutdown successfully");
    }

    private static URL getResourceFromClasspath(String path) {
        if (path == null)
            return null;
        ClassLoader cl = JettyServer.class.getClassLoader();
        URL url = cl.getResource(path);
        return url; // may be null; caller decides how to handle
    }

}
