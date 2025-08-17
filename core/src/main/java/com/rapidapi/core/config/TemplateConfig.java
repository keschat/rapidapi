package com.rapidapi.core.config;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.rapidapi.core.infrastructure.config.ConfigProvider;
import com.rapidapi.core.infrastructure.loader.TemplateEngineType;

@Singleton
public class TemplateConfig {
    
    private final ConfigProvider config;
    
    @Inject
    public TemplateConfig(ConfigProvider config) {
        this.config = config;
    }
    
    public TemplateEngineType getEngine() {
        String engineName = TemplateConfigKey.ENGINE.getString(config);
        try {
            return TemplateEngineType.valueOf(engineName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return TemplateEngineType.FREEMARKER; // Default fallback
        }
    }
    
    public String getPrefix() {
        return TemplateConfigKey.PREFIX.getString(config);
    }
    
    public String getSuffix() {
        String suffix = TemplateConfigKey.SUFFIX.getString(config);
        // Use engine default if not specified
        return suffix != null && !suffix.isEmpty() ? suffix : getEngine().getDefaultExtension();
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