package com.rapidapi.core.config;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

public enum ApplicationConfigKey {

    NAME("app.name"),
    VERSION("app.version"),
    DESCRIPTION("app.description"),
    ENVIRONMENT("app.environment");

    private final String key;

    ApplicationConfigKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getString(ConfigProvider config) {
        return config.getString(key);
    }
}