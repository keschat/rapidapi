package com.rapidapi.core.infrastructure.server;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidapi.core.config.ApplicationConfig;
import com.rapidapi.core.config.SecurityConfig;

@WebListener
public class ServerConfigurationListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfigurationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // CDI is now available, load configurations
            var securityConfig = CDI.current().select(SecurityConfig.class).get();
            var appConfig = CDI.current().select(ApplicationConfig.class).get();
            
            logger.info("CDI initialized - {} {} ({})", 
                appConfig.getName(), appConfig.getVersion(), appConfig.getEnvironment());
            logger.info("Security provider: {}", securityConfig.getCrypto().getCryptoProvider());
            
        } catch (Exception e) {
            logger.error("Failed to initialize server configuration", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Server context destroyed");
    }
}