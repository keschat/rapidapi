package com.rapidapi.core;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidapi.core.http.JettyServer;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = null;

        try {
            container = weld.initialize();
            logger.info("Weld container initialized: {}", container.getBeanManager());

            JettyServer server = new JettyServer(container);
            server.run();

        } catch (Exception e) {
            logger.error("Application failed to start", e);
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (container != null && container.isRunning()) {
                container.shutdown();
                logger.info("Weld container shut down cleanly");
            }
        }
    }
}
