package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class SecurityConfig {
    
    private final JwtConfig jwtConfig;
    private final CryptoConfig cryptoConfig;
    
    @Inject
    public SecurityConfig(JwtConfig jwtConfig, CryptoConfig cryptoConfig) {
        this.jwtConfig = jwtConfig;
        this.cryptoConfig = cryptoConfig;
    }
    
    public JwtConfig getJwt() {
        return jwtConfig;
    }
    
    public CryptoConfig getCrypto() {
        return cryptoConfig;
    }
}