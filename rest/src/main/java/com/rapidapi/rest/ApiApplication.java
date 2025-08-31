package com.rapidapi.rest;

import com.rapidapi.rest.service.FlashMessageManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.thymeleaf.ThymeleafMvcFeature;

public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        packages("com.rapidapi.rest.resource");
        register(ThymeleafMvcFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(FlashMessageManager.class).to(FlashMessageManager.class).in(RequestScoped.class);
            }
        });
    }
}
