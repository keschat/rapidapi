package com.rapidapi.rest;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        packages("com.rapidapi.rest");
        
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                // bindFactory(HttpSessionFactory.class).to(HttpSession.class);
            }
        });
    }
}
