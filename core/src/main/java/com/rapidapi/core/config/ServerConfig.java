package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

@Singleton
public class ServerConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public ServerConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public int getHttpPort() {
        return ServerConfigKey.HTTP_PORT.getInt(config);
    }
    
    public int getHttpsPort() {
        return ServerConfigKey.HTTPS_PORT.getInt(config);
    }
    
    public String getContextPath() {
        return ServerConfigKey.CONTEXT_PATH.getString(config);
    }
    
    public String getApiPath() {
        return ServerConfigKey.API_PATH.getString(config);
    }
    
    public String getStaticResourcePath() {
        return ServerConfigKey.STATIC_RESOURCE_PATH.getString(config);
    }
    
    public String[] getWelcomeFiles() {
        String files = ServerConfigKey.WELCOME_FILES.getString(config);
        return files != null ? files.split(",") : new String[0];
    }
    
    public int getIdleTimeout() {
        return ServerConfigKey.IDLE_TIMEOUT.getInt(config);
    }
    
    public int getStopTimeout() {
        return ServerConfigKey.STOP_TIMEOUT.getInt(config);
    }
}