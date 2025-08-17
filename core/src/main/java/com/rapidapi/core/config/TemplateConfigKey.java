package com.rapidapi.core.config;

import com.rapidapi.core.infrastructure.config.ConfigProvider;

public enum TemplateConfigKey {

    ENGINE("template.engine"),
    PREFIX("template.prefix"),
    SUFFIX("template.suffix"),
    MODE("template.mode"),
    CACHEABLE("template.cacheable"),
    CACHE_TTL_MS("template.cache.ttl-ms"),
    ENCODING("template.encoding");

    private final String key;

    TemplateConfigKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getString(ConfigProvider config) {
        return config.getString(key);
    }

    public boolean getBoolean(ConfigProvider config) {
        return config.getBoolean(key);
    }

    public int getInt(ConfigProvider config) {
        return config.getInt(key);
    }
}