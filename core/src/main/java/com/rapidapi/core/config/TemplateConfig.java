package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

@Singleton
public class TemplateConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public TemplateConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public String getPrefix() {
        return TemplateConfigKey.PREFIX.getString(config);
    }
    
    public String getSuffix() {
        return TemplateConfigKey.SUFFIX.getString(config);
    }
    
    public String getMode() {
        return TemplateConfigKey.MODE.getString(config);
    }
    
    public boolean isCacheable() {
        return TemplateConfigKey.CACHEABLE.getBoolean(config);
    }
    
    public int getCacheTtlMs() {
        return TemplateConfigKey.CACHE_TTL_MS.getInt(config);
    }
    
    public String getEncoding() {
        return TemplateConfigKey.ENCODING.getString(config);
    }
}