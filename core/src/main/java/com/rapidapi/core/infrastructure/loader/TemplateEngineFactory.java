package com.rapidapi.core.infrastructure.loader;

import com.rapidapi.core.config.TemplateConfig;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class TemplateEngineFactory {
    
    @Inject
    private TemplateConfig templateConfig;
    
    // Constructor for manual instantiation
    public TemplateEngineFactory(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }
    
    // Default constructor for CDI
    public TemplateEngineFactory() {
    }
    
    public String getViewEngineClass() {
        return templateConfig.getEngine().getViewEngineClass();
    }
    
    public String[] getRequiredProviders() {
        TemplateEngineType engine = templateConfig.getEngine();
        
        return switch (engine) {
            case FREEMARKER -> new String[]{
                "org.glassfish.jersey.media.json.JsonJacksonFeature",
                "org.eclipse.krazo.jersey.KrazoFeature",
                "org.eclipse.krazo.freemarker.FreemarkerViewEngine"
            };
            case THYMELEAF -> new String[]{
                "org.glassfish.jersey.media.json.JsonJacksonFeature",
                "org.eclipse.krazo.jersey.KrazoFeature", 
                "org.eclipse.krazo.thymeleaf.ThymeleafViewEngine"
            };
        };
    }
    
    public static TemplateEngineFactory create(TemplateEngineType type) {
        // Factory method for creating specific engine configurations
        TemplateEngineFactory factory = new TemplateEngineFactory();
        // Could inject specific config here if needed
        return factory;
    }
}