package com.rapidapi.core.config;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

public enum SSLConfigKey {

    SSL_PORT("server.ssl.port"),
    KEYSTORE_PATH("server.ssl.keystore.path"),
    KEYSTORE_PASSWORD("server.ssl.keystore.password");

    private final String key;

    SSLConfigKey(String key) {
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