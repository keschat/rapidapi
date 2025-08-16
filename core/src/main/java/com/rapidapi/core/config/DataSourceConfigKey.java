package com.rapidapi.core.config;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

public enum DataSourceConfigKey {

    URL("datasource.url"),
    USERNAME("datasource.username"),
    PASSWORD("datasource.password"),
    POOL_TYPE("datasource.pool.type"),
    MAX_POOL_SIZE("datasource.pool.max-size"),
    MIN_POOL_SIZE("datasource.pool.min-size"),
    CONNECTION_TIMEOUT("datasource.pool.connection-timeout"),
    IDLE_TIMEOUT("datasource.pool.idle-timeout");

    private final String key;

    DataSourceConfigKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getString(ConfigProvider config) {
        return config.getString(key);
    }

    public int getInt(ConfigProvider config) {
        return config.getInt(key);
    }
}