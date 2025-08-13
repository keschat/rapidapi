package com.rapidapi.core.config;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

@ApplicationPath("/api")
public class DIConfig extends ResourceConfig {

    public DIConfig() {

        packages("com.rapidapi.core.config");
        
        // register(new AbstractBinder() {
        //     @Override
        //     protected void configure() {
        //         bind(ConfigProvider.class).to(ConfigProvider.class);
        //         // Additional bindings can be added here
        //     }
        // });
    }

}
