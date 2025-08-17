# rapidapi
Rapid Api Demo App

# Java tutorial API
--- Link: https://youtu.be/vBbcDLyzcjs?list=PLUL7fmH2R8yz1l88jnWGAkoYB87BKabLp


mvn <args> -rf :core

mvn clean compile -rf :core



Config Classes Created:
SecurityConfigKey - Enum with all security configuration keys

JwtConfig - Handles JWT-specific configuration

CryptoConfig - Handles cryptographic configuration including provider

SecurityConfig - Parent config that aggregates JWT and Crypto configs

Usage Example:

// In JettyServer or other components
SecurityConfig securityConfig = container.select(SecurityConfig.class).get();
String jwtSecret = securityConfig.getJwt().getSecret();
String cryptoProvider = securityConfig.getCrypto().getCryptoProvider();

---------------------------

Changes Made:
Application.java:
Removed SSLConfig import and selection

Simplified constructor call - only passes the container

Single responsibility - only manages CDI lifecycle

JettyServer.java:
Handles its own config dependencies - selects SSLConfig internally

Flexible for future configs - can easily add other config selections like:

SSLConfig sslConfig = container.select(SSLConfig.class).get();
ServerConfig serverConfig = container.select(ServerConfig.class).get();
SecurityConfig securityConfig = container.select(SecurityConfig.class).get();

Benefits:
Better encapsulation - JettyServer manages its own dependencies

Future-proof - easy to add more config classes without changing Application

Cleaner separation - Application only handles CDI lifecycle, JettyServer handles all server-related configs

More maintainable - config dependencies are localized where they're used

---------------------------

Industry standard is to separate concerns by domain. I recommend:

ServerConfig - Server-specific settings (ports, paths, timeouts)

ApplicationConfig - App metadata (name, version, description)

Configuration Structure:
ServerConfig - Server runtime settings:
HTTP/HTTPS ports

Context and API paths

Static resource configuration

Timeouts and server behavior

ApplicationConfig - Application metadata:
Name, version, description

Environment information

App-level settings

Benefits:
Domain Separation - Server vs Application concerns

Configurable - No more hardcoded values

Environment-specific - Different settings per environment

Maintainable - Easy to modify without code changes

Industry Standard - Follows Spring Boot/Micronaut patterns

Usage in JettyServer:

// In JettyServer or other components
ServerConfig serverConfig = container.select(ServerConfig.class).get();
ApplicationConfig appConfig = container.select(ApplicationConfig.class).get();
String jwtSecret = securityConfig.getJwt().getSecret();
String cryptoProvider = securityConfig.getCrypto().getCryptoProvider();

The JettyServer now uses all configuration values from YAML instead of hardcoded constants, making it fully configurable and environment-aware.

---------------------------

# myapitutorial
java API tutorial using jetty server

# setting application config
src/main/resources/
├── application.yml          # Base/fallback config
├── application-dev.yml      # Development config
├── application-prod.yml     # Production config
└── application-staging.yml  # Staging config (optional)

// These are used to create the connectors for the HTTP and HTTPS servers.
// They are used to handle incoming requests and to manage the
// communication between the server and the client.
// The HttpConnectionFactory is used for HTTP/1.1 connections,
// while the HTTP2CServerConnectionFactory is used for HTTP/2 connections over
// cleartext.
// The ALPNServerConnectionFactory is used to negotiate the protocol between
// HTTP/1.1 and HTTP/2.
// The SslConnectionFactory is used to handle SSL/TLS connections,
// and it is configured with the SslContextFactory that contains the SSL
// settings
// such as the keystore path and password.
// The server is then configured with two connectors: one for HTTP and one for
// HTTPS.


// Http configuration
// This is used to configure the HTTP and HTTPS connectors
// and to set up the SSL context for secure connections.
// It includes settings for server version, secure scheme, secure port,
// output buffer size, and whether to send the X-Powered-By header.
// The SecureRequestCustomizer is used to customize the secure request handling.
// The HttpConfiguration is then used to create connection factories for
// HTTP/1.1 and
// HTTP/2 over cleartext (HTTP/2C) protocols, as well as
// an ALPN (Application-Layer Protocol Negotiation) connection factory for
// handling
// protocol negotiation between HTTP/1.1 and HTTP/2.

https://zetcode.com/maven/exec-plugin/

https://medium.com/@jimmysthoughts/happily-coexisting-building-with-exec-maven-plugin-on-both-os-x-and-cygwin-d9fb0c4a02cc

---------------------------

      final SslContextFactory sslcontext = SSLConfig.createContextFactory(config.sslConfig);

      HttpConfiguration configuration = new HttpConfiguration();
      ServerConnector connector;
      if (sslcontext != null) {
        Security.addProvider(new OpenSSLProvider());
        sslcontext.setProvider("Conscrypt");
        sslcontext.setCipherComparator(HTTP2Cipher.COMPARATOR);
        sslcontext.setUseCipherSuitesOrder(true);

        configuration.setSecureScheme("https");
        configuration.addCustomizer(new SecureRequestCustomizer());
        connector = new ServerConnector(server);
        SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(sslcontext, "alpn");
        connector.addConnectionFactory(sslConnectionFactory);
        connector.setDefaultProtocol(sslConnectionFactory.getProtocol());

        HttpConnectionFactory http1ConnectionFactory = new HttpConnectionFactory(configuration);
        HTTP2ServerConnectionFactory http2ConnectionFactory = new HTTP2ServerConnectionFactory(configuration);

        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory(
            http2ConnectionFactory.getProtocol(),
            http1ConnectionFactory.getProtocol());
        alpn.setDefaultProtocol(http1ConnectionFactory.getProtocol());
        connector.addConnectionFactory(alpn);
        connector.addConnectionFactory(http1ConnectionFactory);
        connector.addConnectionFactory(http2ConnectionFactory);
      }

             ((ServerConnector) server.getConnectors()[1]).getLocalPort(),
                        ((ServerConnector) server.getConnectors()[0]).getLocalPort());

---------------------------

https://logback.qos.ch/manual/layouts.html#coloring

https://mkyong.com/webservices/jax-rs/jersey-and-jetty-http-server-examples/

https://smallrye.io/jandex/jandex/3.1.3/index.html

https://news.ycombinator.com/item?id=43648931

https://p2.dexels.com/eclipse/9.4.12/jetty-distribution-9.4.12.v20180830/demo-base/webapps/doc/9.4.12.v20180830/embedding-jetty.html

https://jreleaser.org/

https://github.com/wiverson/maven-jpackage-template

https://www.jdeploy.com/

https://www.keycloak.org/

https://shiro.apache.org/

apache fortress

https://stackoverflow.com/questions/42973786/connection-pooling-in-spark-java-framework

https://medium.com/@rinorahmeti2000/connection-pooling-hikari-cp-for-enterprise-java-applications-e34442cbbce5

---------------------------

<dependencyManagement>

. Should contain dependencies only for version management (not for direct use).
. Child modules can then declare the dependency without a version, inheriting the version from here.
. Do not put plugins or plugin versions here.

<dependencies>

. Should contain dependencies that are directly used by your code (imported in Java files, etc.).
. These are the libraries your modules actually need to compile and run.

<build> / <pluginManagement> / <plugins>

. Should contain plugins (like maven-compiler-plugin, maven-surefire-plugin, etc.), not regular dependencies.
. Use <pluginManagement> to set plugin versions and configuration for child modules.
. Use <plugins> for plugins that are actually used in this module.


<plugin>
    <artifactId>exec-maven-plugin</artifactId>
    <groupId>org.codehaus.mojo</groupId>
    <executions>
        <execution>
            <id>runScript</id>
            <phase>package</phase>
            <goals>
                <goal>exec</goal>
            </goals>
            <configuration>
                <workingDirectory>${basedir}/src/main/scripts/</workingDirectory>
                <executable>./script.sh</executable>
            </configuration>
        </execution>
    </executions>
</plugin>

---------------------------

com.rapidapi.core/
├── infrastructure/           # Technical concerns
│   ├── cdi/                 # CDI/DI configuration
│   ├── config/              # Configuration infrastructure
│   ├── server/              # HTTP server setup
│   ├── persistence/         # Database infrastructure
│   ├── loader/              # Dynamic loading utilities
│   ├── validation/          # System validation
│   └── web/                 # Web infrastructure
├── domain/                  # Business domain
│   ├── config/              # Domain-specific configs
│   └── exception/           # Domain exceptions
├── application/             # Application services
│   └── service/             # Application layer services
└── config/                  # Shared configuration


---------------------------

com.rapidapi.core/
├── infrastructure/           # Technical concerns
│   ├── cdi/                 # ✅ CdiInitializer
│   ├── config/              # ✅ ConfigProvider, DatabaseConfig
│   ├── server/              # ✅ JettyServer
│   ├── persistence/         # ✅ DataSource factories
│   ├── loader/              # ✅ HandlerMappingLoader
│   ├── validation/          # ✅ CheckRequirements, StartUpCheck
│   └── web/                 # ✅ ValidatedHandler
├── domain/                  # Business domain
│   ├── config/              # ✅ Ready for domain configs
│   └── exception/           # ✅ ErrorHandler, ErrorResponse
├── application/             # Application services
│   └── service/             # ✅ BaseService
└── config/                  # ✅ All config classes (Security, Server, etc.)


domains/
├── product/                 # Product Bounded Context
│   ├── domain/             # Core business logic
│   │   ├── model/          # Entities, Value Objects
│   │   ├── repository/     # Repository interfaces
│   │   └── service/        # Domain services
│   ├── application/        # Application layer
│   │   ├── service/        # Application services
│   │   ├── dto/            # Data Transfer Objects
│   │   └── command/        # Commands/Queries
│   ├── infrastructure/     # Technical implementation
│   │   ├── persistence/    # Repository implementations
│   │   └── config/         # Domain-specific config
│   └── presentation/       # API layer
│       └── rest/           # REST controllers
├── user/                   # User Bounded Context
├── customer/               # Customer Bounded Context
└── email/                  # Email Bounded Context

Your POS Architecture:
├── shared-kernel/          # Infrastructure (renamed from core)
├── domains/               # Business domains (DDD structure)
│   └── product/
│       ├── domain/model/
│       ├── application/service/
│       └── presentation/
│           ├── rest/      # JSON APIs (Vue.js)
│           └── web/       # Thymeleaf (Dashboard)
└── pos-app/              # POS Application with dashboard



    // Example for embedded Jetty
    Server server = new Server(8080); // Port
    WebAppContext webAppContext = new WebAppContext();
    webAppContext.setContextPath("/your-app"); // Context path
    webAppContext.setWar("path/to/your/app.war"); // Path to your WAR file
    server.setHandler(webAppContext);
    server.start();
    server.join();


✅ New Flash Message Architecture:
1. MessageType Enum (Type Safety):

public enum MessageType {
    SUCCESS("success"),
    ERROR("error"), 
    WARNING("warning"),
    INFO("info");
}


2. FlashMessage Record (Modern Java):

public record FlashMessage(
    String message, 
    MessageType type,
    LocalDateTime timestamp
) implements Serializable {
    
    public FlashMessage(String message, MessageType type) {
        this(message, type, LocalDateTime.now());
    }
}


3. Usage in Controllers:

// Type-safe flash messages
flash.addSuccess("Product created successfully");
flash.addError("Product not found");
flash.addWarning("Low inventory");
flash.addInfo("Shift ends in 30 minutes");


4. FreeMarker Template Usage:

<#if flash.hasMessages()>
    <#list flash.messages as message>
        <div class="alert alert-${message.type}">
            ${message.message}
        </div>
    </#list>
</#if>
