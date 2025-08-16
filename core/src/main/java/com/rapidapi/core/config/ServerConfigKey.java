package com.rapidapi.core.config;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

public enum ServerConfigKey {

    HTTP_PORT("server.http-port"),
    HTTPS_PORT("server.https-port"),
    CONTEXT_PATH("server.context-path"),
    API_PATH("server.api-path"),
    STATIC_RESOURCE_PATH("server.static-resource-path"),
    WELCOME_FILES("server.welcome-files"),
    IDLE_TIMEOUT("server.idle-timeout"),
    STOP_TIMEOUT("server.stop-timeout");

    private final String key;

    ServerConfigKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public int getInt(ConfigProvider config) {
        return config.getInt(key);
    }

    public String getString(ConfigProvider config) {
        return config.getString(key);
    }
}