package com.rapidapi.core.domain.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

@Singleton
public class BusinessConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public BusinessConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public int getMaxProductsPerPage() {
        return config.getInt("business.products.max-per-page", 50);
    }
    
    public boolean isProductValidationEnabled() {
        return config.getBoolean("business.products.validation-enabled", true);
    }
    
    public double getMaxProductPrice() {
        return Double.parseDouble(config.getString("business.products.max-price", "10000.00"));
    }
    
    public String getDefaultCurrency() {
        return config.getString("business.products.default-currency", "USD");
    }
}