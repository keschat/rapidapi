package com.rapidapi.core.http;

import java.net.URL;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.conscrypt.OpenSSLProvider;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.ee11.servlet.DefaultServlet;
import org.eclipse.jetty.ee11.servlet.ServletContextHandler;
import org.eclipse.jetty.ee11.servlet.ServletHolder;
import org.eclipse.jetty.http.UriCompliance;
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
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidapi.core.config.ApplicationConfig;
import com.rapidapi.core.config.SSLConfig;
import com.rapidapi.core.config.SecurityConfig;
import com.rapidapi.core.config.ServerConfig;

/**
 * Jetty 12 embedded server with:
 * - HTTP -> HTTPS automatic redirect for ALL paths
 * - HTTP/2 over TLS (ALPN)
 * - Graceful shutdown (in-flight requests)
 * - Clean CDI/Weld lifecycle
 */
public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    private final WeldContainer container;

    public JettyServer(WeldContainer container) {
        this.container = container;
    }

    public void run() throws Exception {
        // === Load configurations ===
        var securityConfig = container.select(SecurityConfig.class).get();
        var serverConfig = container.select(ServerConfig.class).get();
        var appConfig = container.select(ApplicationConfig.class).get();

        logger.info("Starting {} v{} ({})", appConfig.getName(), appConfig.getVersion(), appConfig.getEnvironment());

        if (securityConfig.getCrypto().getCryptoProvider() == "conscrypt") {
            try {
                Security.insertProviderAt(new OpenSSLProvider(), 1);
                logger.info("Conscrypt OpenSSL provider installed");
            } catch (Throwable t) {
                logger.warn("Conscrypt not available; falling back to default JSSE: {}", t.toString());
            }
        } else if (securityConfig.getCrypto().getCryptoProvider() == "bouncy-castle") {
            logger.info("Using Bouncy Castle provider...");
            try {
                Security.addProvider(new BouncyCastleProvider());
                logger.info("BouncyCastle provider installed");
            } catch (Throwable t) {
                logger.warn("BouncyCastle not available: {}", t.toString());
            }
        } else {
            logger.info("No security provider specified. Using default JSSE...");
        }

        SSLConfig sslConfig = container.select(SSLConfig.class).get();
        logger.info("Configurations loaded successfully");

        // === Jetty Server ===
        Server server = new Server();

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

        // CAUTION: trustAll is for development only
        // ssl.setTrustAll(Boolean.TRUE.equals(sslConfig.isTrustAll()));
        // Development settings - remove in production
        // ssl.setEndpointIdentificationAlgorithm(null);

        // Use OpenSSL provider since we're using OpenSSLProvider
        // Optional: prefer Conscrypt if installed
        switch (securityConfig.getCrypto().getCryptoProvider()) {
            case "conscrypt" -> ssl.setProvider("Conscrypt");
            case "bouncy-castle" -> ssl.setProvider("BC");
            default -> ssl.setProvider("JSSE");
        }

        // ALPN + HTTP/2 over TLS
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol("h2");

        // Build connectors
        // HTTPS (8443): ssl -> alpn -> http/1.1 (HTTP/2 will be negotiated by ALPN)
        ServerConnector https = new ServerConnector(
                server,
                new SslConnectionFactory(ssl, alpn.getProtocol()),
                alpn,
                new HttpConnectionFactory(httpsConfig));
        https.setName("HTTPS");
        https.setPort(serverConfig.getHttpsPort());
        https.setIdleTimeout(serverConfig.getIdleTimeout());

        // HTTP (8080) plain: only used to redirect to HTTPS
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        http.setName("HTTP");
        http.setPort(serverConfig.getHttpPort());
        http.setIdleTimeout(serverConfig.getIdleTimeout());

        server.setConnectors(new Connector[] { https, http });

        // ---- App Handlers ----
        // 1) All insecure requests are redirected to HTTPS
        SecuredRedirectHandler redirectAll = new SecuredRedirectHandler();

        // 2) Your webapp context (static + Jersey)
        ServletContextHandler app = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        app.setContextPath(serverConfig.getContextPath());
        // Static resources (optional)
        app.addServlet(DefaultServlet.class, serverConfig.getContextPath());

        // Set base resource for static files
        URL staticResource = getResourceFromClasspath(serverConfig.getStaticResourcePath());
        if (staticResource != null) {
            app.setBaseResource(app.newResource(staticResource));
            app.setWelcomeFiles(serverConfig.getWelcomeFiles());
        }

        // Jersey REST under /api/*
        ServletHolder jersey = app.addServlet(ServletContainer.class, serverConfig.getApiPath());
        jersey.setInitOrder(1);
        jersey.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.rapidapi.rest.resource");
        // Enable Jackson as JSON provider
        jersey.setInitParameter(
                "jersey.config.server.provider.classnames",
                "org.glassfish.jersey.media.json.JsonJacksonFeature");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.addHandler(app);

        // Compose handler chain: redirect first, then app contexts
        Handler.Sequence chain = new Handler.Sequence();
        chain.setHandlers(new Handler[] { redirectAll, contexts });
        graceful.setHandler(chain);

        // Optionally add a JVM shutdown hook to call server.stop() explicitly
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("JVM shutdown hook: stopping Jetty...");
            try {
                server.stop();
            } catch (Exception e) {
                logger.warn("Error while stopping Jetty", e);
            }
        }, "jetty-shutdown"));

        try {
            server.start();
            logger.info("\n\r🌐🌐🌐🌐🌐 Server started 🚀🚀🚀\uD83D\uDE80\uD83D\uDE80 on ports HTTP: {}  HTTPS: {}",
                    http.getLocalPort(),
                    https.getLocalPort());
            server.join();
        } catch (Throwable t) {
            logger.error("Failed to start server", t);
            try {
                server.stop();
            } catch (Exception e) {
                logger.warn("Error while stopping Jetty server", e);
            }
            throw t;
        }
    }

    private static URL getResourceFromClasspath(String path) {
        if (path == null)
            return null;
        ClassLoader cl = JettyServer.class.getClassLoader();
        URL url = cl.getResource(path);
        return url; // may be null; caller decides how to handle
    }

}
