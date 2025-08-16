package com.rapidapi.core.infrastructure.cdi;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class CdiInitializer implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(CdiInitializer.class);
    
    private Weld weld;
    private WeldContainer container;
    private volatile boolean isShutdown = false;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (container == null) {
            weld = new Weld();
            container = weld.initialize();
            logger.info("Weld container initialized: {}", container.getBeanManager());
        }

        if (sce != null) {
            sce.getServletContext().setAttribute(WeldContainer.class.getName(), container);
            logger.info("CDI container registered with servlet context");
        }
    }

    public void initialize() {
        contextInitialized(null);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (container != null && container.isRunning() && !isShutdown) {
            isShutdown = true;
            logger.info("Shutting down Weld container from servlet context...");
            try {
                container.shutdown();
                logger.info("Weld container shut down cleanly");
            } catch (Exception e) {
                logger.warn("Error shutting down Weld container", e);
            }
        }
    }

    public WeldContainer getContainer() {
        return container;
    }
}