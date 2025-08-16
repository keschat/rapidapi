package com.rapidapi.core.infrastructure.loader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.PathMappingsHandler;
import org.jboss.weld.environment.se.WeldContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HandlerMappingLoader {

    @SuppressWarnings("unchecked")
    public static void loadMappings(PathMappingsHandler pathMappingsHandler,
            WeldContainer weldContainer) throws Exception {
        System.out.println("Loading handler mappings...");
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = HandlerMappingLoader.class.getClassLoader().getResourceAsStream("config.json")) {
            if (is == null) {
                System.err.println("config.json not found in classpath");
                return;
            }

            Map<String, Object> config = mapper.readValue(is, Map.class);
            List<Map<String, String>> handlers = (List<Map<String, String>>) config.get("handlers");

            for (Map<String, String> handlerConfig : handlers) {
                String path = handlerConfig.get("path");
                String handlerClass = handlerConfig.get("handler");

                try {
                    System.out.println("Loading handler: " + handlerClass + " for path: " + path);
                    Class<?> clazz = Class.forName(handlerClass);
                    
                    Handler handler;
                    if (weldContainer != null) {
                        // Let Weld create and manage the handler instance
                        handler = (Handler) weldContainer.select(clazz).get();
                        System.out.println("Handler created and injected by Weld: " + handlerClass);
                    } else {
                        // Fallback to manual instantiation
                        handler = (Handler) clazz.getDeclaredConstructor().newInstance();
                        System.out.println("WeldContainer is null, using manual instantiation");
                    }

                    pathMappingsHandler.addMapping(PathSpec.from(path), handler);
                    System.out.println("Successfully loaded handler: " + handlerClass);
                } catch (Exception e) {
                    System.err.println("Failed to load handler: " + handlerClass + " - " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }


}