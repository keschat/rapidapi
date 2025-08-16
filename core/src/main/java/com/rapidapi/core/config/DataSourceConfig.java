package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

@Singleton
public class DataSourceConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public DataSourceConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public String getUrl() {
        return DataSourceConfigKey.URL.getString(config);
    }
    
    public String getUsername() {
        return DataSourceConfigKey.USERNAME.getString(config);
    }
    
    public String getPassword() {
        return DataSourceConfigKey.PASSWORD.getString(config);
    }
    
    public String getPoolType() {
        return DataSourceConfigKey.POOL_TYPE.getString(config);
    }
    
    public int getMaxPoolSize() {
        return DataSourceConfigKey.MAX_POOL_SIZE.getInt(config);
    }
    
    public int getMinPoolSize() {
        return DataSourceConfigKey.MIN_POOL_SIZE.getInt(config);
    }
    
    public int getConnectionTimeout() {
        return DataSourceConfigKey.CONNECTION_TIMEOUT.getInt(config);
    }
    
    public int getIdleTimeout() {
        return DataSourceConfigKey.IDLE_TIMEOUT.getInt(config);
    }
}