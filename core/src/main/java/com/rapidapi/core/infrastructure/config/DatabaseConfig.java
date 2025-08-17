package com.rapidapi.core.infrastructure.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;

@Singleton
public class DatabaseConfig {

    private final static Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static BasicDataSource dataSource;

    // Initialize the connection pool
    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Configure connection pool
            dataSource = new BasicDataSource();
            
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");
            
            dataSource.setUrl(dbUrl != null ? dbUrl : "jdbc:mysql://localhost:3306/ospos?useSSL=false&serverTimezone=UTC");
            dataSource.setUsername(dbUser != null ? dbUser : "root");
            dataSource.setPassword(dbPassword != null ? dbPassword : "password");

            // Connection pool settings
            // dataSource.setInitialSize(5); // Initial number of connections
            // dataSource.setMaxTotal(20); // Maximum number of connections
            // dataSource.setMaxIdle(10); // Maximum idle connections
            // dataSource.setMinIdle(5); // Minimum idle connections
            // dataSource.setMaxWaitMillis(10000); // Max wait time for a connection (10
            // seconds)

            // Test connection validity
            dataSource.setValidationQuery("SELECT 1");
            dataSource.setTestOnBorrow(true);

            logger.info("MySQL connection pool initialized successfully");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found", e);
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }

    /**
     * Get a database connection from the pool
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Close the connection pool (call this when application shuts down)
     */
    public static void close() {
        if (dataSource != null) {
            try {
                dataSource.close();
                logger.info("MySQL connection pool closed successfully");
            } catch (SQLException e) {
                logger.error("Error closing connection pool", e);
            }
        }
    }

    /**
     * Test the database connection
     * 
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(2); // 2 second timeout
        } catch (SQLException e) {
            logger.error("Database connection test failed", e);
            return false;
        }
    }
}