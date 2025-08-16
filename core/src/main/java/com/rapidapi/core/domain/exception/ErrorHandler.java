package com.rapidapi.core.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    private static final boolean IS_PRODUCTION = "production".equals(System.getProperty("app.environment"));
    
    public static String handleError(String operation, Exception e) {
        // Always log the full error for debugging
        logger.error("Error during {}: {}", operation, e.getMessage(), e);
        
        // Return generic message in production, detailed in development
        if (IS_PRODUCTION) {
            return "An error occurred. Please try again.";
        } else {
            return e.getMessage(); // Only for development
        }
    }
    
    public static String handleValidationError(String message) {
        // Validation errors are safe to show to users
        return message;
    }
}