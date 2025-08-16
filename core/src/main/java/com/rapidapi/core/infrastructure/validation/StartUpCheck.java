package com.rapidapi.core.infrastructure.validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartUpCheck {
    
    private static final Logger logger = LoggerFactory.getLogger(StartUpCheck.class);
    private static final String CONFIG_DIR = System.getProperty("user.home") + "/.apitutorial";
    private static final String FIRST_RUN_FLAG = CONFIG_DIR + "/first-run-complete";
    
    public static boolean isFirstRun() {
        Path flagPath = Paths.get(FIRST_RUN_FLAG);
        boolean firstRun = !Files.exists(flagPath);
        logger.info("First run check: {}", firstRun ? "YES" : "NO");
        return firstRun;
    }
    
    public static void markFirstRunComplete() {
        try {
            Path configDir = Paths.get(CONFIG_DIR);
            Files.createDirectories(configDir);
            
            Path flagPath = Paths.get(FIRST_RUN_FLAG);
            Files.createFile(flagPath);
            
            logger.info("Marked first run as complete");
        } catch (IOException e) {
            logger.error("Failed to create first run flag", e);
        }
    }
}