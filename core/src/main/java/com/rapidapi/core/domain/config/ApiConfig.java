package com.rapidapi.core.domain.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

@Singleton
public class ApiConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public ApiConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public int getRateLimitPerMinute() {
        return config.getInt("api.rate-limit.per-minute", 100);
    }
    
    public int getMaxRequestSize() {
        return config.getInt("api.request.max-size-mb", 10);
    }
    
    public boolean isCorsEnabled() {
        return config.getBoolean("api.cors.enabled", true);
    }
    
    public String[] getAllowedOrigins() {
        String origins = config.getString("api.cors.allowed-origins", "*");
        return origins.split(",");
    }
    
    public String getApiVersion() {
        return config.getString("api.version", "v1");
    }
}