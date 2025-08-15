# rapidapi
Rapid Api Demo App


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