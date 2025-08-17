package com.rapidapi.core.infrastructure.validation;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidapi.core.infrastructure.config.DatabaseConfig;

public class CheckRequirements {
    
    private static final Logger logger = LoggerFactory.getLogger(CheckRequirements.class);
    
    public static void ensureDatabaseSetup() throws Exception {
        boolean isFirstRun = StartUpCheck.isFirstRun();
        
        if (isFirstRun) {
            logger.info("First run detected - setting up database...");
            setupDatabase();
            StartUpCheck.markFirstRunComplete();
        } else {
            logger.info("Subsequent run - validating database...");
            validateDatabase();
        }
    }
    
    private static void setupDatabase() throws Exception {
        try (Connection conn = DatabaseConfig.getConnection()) {
            createDatabaseIfNotExists(conn);
            createTablesIfNotExist(conn);
            logger.info("Database setup completed successfully");
        }
    }
    
    private static void validateDatabase() throws Exception {
        try (Connection conn = DatabaseConfig.getConnection()) {
            if (!tableExists(conn, "products")) {
                throw new RuntimeException("Required table 'products' not found. Database may be corrupted.");
            }
            logger.info("Database validation passed");
        }
    }
    
    private static void createDatabaseIfNotExists(Connection conn) throws Exception {
        // Database creation is handled by the connection URL
        logger.info("Database connection verified, {}", conn);
    }
    
    private static void createTablesIfNotExist(Connection conn) throws Exception {
        logger.info("Checking if products table exists...");
        if (!tableExists(conn, "products")) {
            logger.info("Products table does not exist, creating...");
            createProductsTable(conn);
        } else {
            logger.info("Products table already exists");
        }
    }
    
    private static boolean tableExists(Connection conn, String tableName) throws Exception {
        DatabaseMetaData meta = conn.getMetaData();
        
        // Try both lowercase and uppercase for MySQL compatibility
        try (ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            if (rs.next()) {
                logger.info("Table '{}' found", tableName);
                return true;
            }
        }
        
        try (ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"})) {
            if (rs.next()) {
                logger.info("Table '{}' found (uppercase)", tableName);
                return true;
            }
        }
        
        logger.info("Table '{}' not found", tableName);
        return false;
    }
    
    private static void createProductsTable(Connection conn) throws Exception {
        String sql = """
            CREATE TABLE products (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        
        try (Statement stmt = conn.createStatement()) {
            logger.info("Executing SQL: {}", sql);
            stmt.execute(sql);
            logger.info("Successfully created products table");
            
            // Verify table was created
            if (tableExists(conn, "products")) {
                logger.info("Table creation verified");
            } else {
                logger.error("Table creation failed - table not found after creation");
            }
        } catch (Exception e) {
            logger.error("Failed to create products table: {}", e.getMessage(), e);
            throw e;
        }
    }
}