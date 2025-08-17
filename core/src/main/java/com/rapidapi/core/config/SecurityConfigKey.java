package com.rapidapi.core.config;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

public enum SecurityConfigKey {

    JWT_SECRET("security.jwt.secret"),
    JWT_ALGORITHM("security.jwt.algorithm"),
    JWT_EXPIRATION_MINUTES("security.jwt.expiration-minutes"),
    JWT_VERIFICATION_URL("security.jwt.verification-url"),
    
    CRYPTO_SECRET("security.crypto.secret"),
    CRYPTO_ALGORITHM("security.crypto.algorithm"),
    
    CRYPTO_PROVIDER("security.crypto-provider");

    private final String key;

    SecurityConfigKey(String key) {
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