package com.rapidapi.core;

import org.slf4j.LoggerFactory;

public class Application {

    public static void main(String[] args) throws Exception {

        final var logger = LoggerFactory.getLogger(Application.class);
        logger.info("Starting application...");

        Bootstrap bootstrap = new Bootstrap();
        
        // Add shutdown hook for graceful termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutdown signal received");
            bootstrap.destroy();
        }));
        
        bootstrap.initialize();
    }














    // private static final Logger logger = LoggerFactory.getLogger(Application.class);

    // public static void run() {
    //     try {
    //         CdiInitializer cdiInitializer = new CdiInitializer();
    //         cdiInitializer.initialize();
            
    //         JettyServer server = new JettyServer(cdiInitializer.getContainer());
    //         server.run();

    //     } catch (Exception e) {
    //         logger.error("Application failed to start", e);
    //         e.printStackTrace();
    //         System.exit(1);
    //     }
    // }
    
    // public static void main(String[] args) {
    //     run();
    // }
}
