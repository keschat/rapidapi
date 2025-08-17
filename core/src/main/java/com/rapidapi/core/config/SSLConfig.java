package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

@Singleton
public class SSLConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public SSLConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public int getPort() {
        return SSLConfigKey.SSL_PORT.getInt(config);
    }
    
    public String getKeystorePath() {
        return SSLConfigKey.KEYSTORE_PATH.getString(config);
    }
    
    public String getKeystorePassword() {
        return SSLConfigKey.KEYSTORE_PASSWORD.getString(config);
    }
    
    public String getKeyManagerPassword() {
        return SSLConfigKey.KEYSTORE_PASSWORD.getString(config);
    }
}