package com.rapidapi.rest.flash;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
public class FlashMessageFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(FlashMessageManager.class);
            }
        });
        return true;
    }
}
