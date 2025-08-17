package com.rapidapi.core;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.rapidapi.core.infrastructure.config.DatabaseConfig;
import com.rapidapi.core.infrastructure.server.JettyServer;
import com.rapidapi.core.infrastructure.validation.CheckRequirements;

import jakarta.inject.Singleton;

@Singleton
public class Bootstrap {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    Connection dbConn;
    JettyServer httpServer;

    public Bootstrap() throws ClassNotFoundException, SQLException {
        dbConn = DatabaseConfig.getConnection();
        httpServer = new JettyServer();
    }

    public void initialize() throws SQLException {
        logger.info("Initializing all services...");

        try {
            CheckRequirements.ensureDatabaseSetup();
        } catch (Exception e) {
            logger.error("Failed to setup database requirements", e);
            throw new RuntimeException("Database setup failed", e);
        }

        String portEnv = System.getenv("HTTP_SERVER_PORT");
        int port = (portEnv != null && !portEnv.isEmpty()) ? Integer.parseInt(portEnv) : 9091;

        try {
            httpServer.start();
        } catch (Exception e) {
            logger.error("Failed to start HTTP server at port " + port, e);
            throw new RuntimeException("Server startup failed", e);
        }
    }

    public void destroy() {
        logger.info("Destroying all services...");

        try {
            if (httpServer != null) {
                httpServer.stop();
                logger.info("HTTP server stopped successfully");
            }
        } catch (Exception e) {
            logger.error("Failed to stop HTTP server", e);
        }

        try {
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
                logger.info("Database connection closed successfully");
            }
        } catch (SQLException e) {
            logger.error("Failed to close database connection", e);
        }

        DatabaseConfig.close();
    }

}
