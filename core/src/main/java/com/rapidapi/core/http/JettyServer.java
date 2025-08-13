package com.rapidapi.core.http;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidapi.core.config.ConfigProvider;
import com.rapidapi.core.config.DIConfig;

public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    // private static final String ROOT_CONTEXT = "/";
    // private static final String API_PATH = "/api/*";

    public void run() throws Exception {
        logger.info("Jetty server initialization begins...");

        // Security.insertProviderAt(new OpenSSLProvider(), 1);
        Security.addProvider(new BouncyCastleProvider());

        // Register configuration service
        // ServiceLocatorUtilities.addClasses(serviceLocator, RestApplication.class);
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        // locator.inject(new AbstractBinder() {
        //     @Override
        //     protected void configure() {
        //         bind(ConfigProvider.class).to(ConfigProvider.class);
        //     }
        // });

        final ResourceConfig config = new ResourceConfig(DIConfig.class);         
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                // Additional bindings can be added here
                // bindFactory(HttpSessionFactory.class).to(HttpSession.class);
            }
        });

        // Get configuration instance
        ConfigProvider appConfig = locator.getService(ConfigProvider.class);

        logger.info("Application configuration loaded instance: {}", appConfig);

        // final var SECURE_PORT = appConfig.getInt("server.ssl.port");
        // final var KEYSTORE_PATH = appConfig.getString("server.ssl.keystore.path");
        // final var KEYSTORE_PASS =
        // appConfig.getString("server.ssl.keystore.password");

        // Server server = new Server();

        // GracefulHandler gracefulHandler = new GracefulHandler();
        // server.setHandler(gracefulHandler);
        // // Set the Server stopTimeout to wait at most
        // // 10 seconds for existing requests to complete.
        // server.setStopTimeout(10_000);

        // // For the SecureRequestCustomizer, Jetty defaults are:
        // // - isSniRequired() -> false
        // // - isSniHostCheck() -> true
        // // - getStsMaxAge() -> -1 (no max age)
        // // - isStsIncludeSubDomains() -> false
        // SecureRequestCustomizer secureReqCust = new SecureRequestCustomizer();
        // secureReqCust.setStsMaxAge(31536000); // 1 year in seconds
        // secureReqCust.setSniHostCheck(false);
        // secureReqCust.setSniRequired(false);

        // // The HTTP configuration.
        // HttpConfiguration httpConfig = new HttpConfiguration(http_config());
        // httpConfig.setSecureScheme("https");
        // httpConfig.setSecurePort(SECURE_PORT);
        // httpConfig.addCustomizer(secure_req_customizer());
        // // The secure HTTP configuration.

        // HttpConfiguration secureConfig = new HttpConnectionFactory(https_config);

        // // Connection factories
        // SslContextFactory.Server sslCtx = ssl_ctx_factory(KEYSTORE_PATH,
        // KEYSTORE_PASS);
        // HttpConnectionFactory https = https_conn_factory(secureConfig);
        // HTTP2CServerConnectionFactory http2 = http2_conn_factory(secureConfig);
        // ALPNServerConnectionFactory alpn = alpn_conn_factory();
        // SslConnectionFactory ssl = ssl_conn_factory(sslCtx, alpn.getProtocol());
        // ServerConnector secureConnector = secure_connector(server, ssl, alpn, http2,
        // https,
        // secureConfig.getSecurePort());

        // // Second, create the plain connector for HTTP.
        // HttpConnectionFactory http = http_conn_factory(httpConfig);
        // ServerConnector httpConnector = http_connector(server, http,
        // appConfig.getInt("server.port"));

        // server.setConnectors(new Connector[] { secureConnector, httpConnector });
        // logger.info("Connectors set successfully");

        // // Set up a listener so that when the secure connector starts,
        // // it configures the other connectors that have not started yet.
        // secureConnector.addEventListener(new NetworkConnector.Listener() {
        // @Override
        // public void onOpen(NetworkConnector connector) {
        // var port = connector.getLocalPort();
        // // Configure the plain connector for secure redirects from http to https.
        // httpConfig.setSecurePort(port);
        // }
        // });

        // ContextHandler context = new ContextHandler(new Handler.Abstract() {
        // @Override
        // public boolean handle(Request request, Response response, Callback callback)
        // {
        // callback.succeeded();
        // return true;
        // }
        // }, "/blank");

        // ServletContextHandler restContext = new
        // ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        // restContext.setContextPath(ROOT_CONTEXT);
        // restContext.setBaseResource(restContext.newResource(JettyServer.class.getClassLoader().getResource("templates")));
        // restContext.addServlet(DefaultServlet.class, ROOT_CONTEXT);

        // // Adds Servlet that will handle requests on /api/*
        // ServletHolder jerseyServlet = restContext.addServlet(ServletContainer.class,
        // API_PATH);
        // jerseyServlet.setInitOrder(1);

        // // set package where rest resources are located.
        // jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
        // RestApplication.class.getCanonicalName());
        // // jerseyServlet.setInitParameter("jersey.config.server.provider.packages",
        // "com.rapidapi.rest.resource");
        // // jerseyServlet.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS,
        // ApiApplication.class.getName());

        // ContextHandlerCollection contexts = new ContextHandlerCollection();
        // contexts.setHandlers(new Handler[] { context, restContext });
        // gracefulHandler.setHandler(contexts);

        // try {
        // server.start();
        // logger.info("🚀🚀🚀🚀🚀🚀 Server started on HTTP: {} and HTTPS: {}",
        // httpConnector.getLocalPort(),
        // secureConnector.getLocalPort());
        // server.join();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }
}
