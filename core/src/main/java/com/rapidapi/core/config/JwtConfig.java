package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

@Singleton
public class JwtConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public JwtConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public String getSecret() {
        return SecurityConfigKey.JWT_SECRET.getString(config);
    }
    
    public String getAlgorithm() {
        return SecurityConfigKey.JWT_ALGORITHM.getString(config);
    }
    
    public int getExpirationMinutes() {
        return SecurityConfigKey.JWT_EXPIRATION_MINUTES.getInt(config);
    }
    
    public String getVerificationUrl() {
        return SecurityConfigKey.JWT_VERIFICATION_URL.getString(config);
    }
}