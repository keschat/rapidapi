package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ApplicationConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public ApplicationConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public String getName() {
        return ApplicationConfigKey.NAME.getString(config);
    }
    
    public String getVersion() {
        return ApplicationConfigKey.VERSION.getString(config);
    }
    
    public String getDescription() {
        return ApplicationConfigKey.DESCRIPTION.getString(config);
    }
    
    public String getEnvironment() {
        return ApplicationConfigKey.ENVIRONMENT.getString(config);
    }
}