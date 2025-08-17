package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class CryptoConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public CryptoConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public String getSecret() {
        return SecurityConfigKey.CRYPTO_SECRET.getString(config);
    }
    
    public String getAlgorithm() {
        return SecurityConfigKey.CRYPTO_ALGORITHM.getString(config);
    }
    
    public String getCryptoProvider() {
        return SecurityConfigKey.CRYPTO_PROVIDER.getString(config);
    }
}