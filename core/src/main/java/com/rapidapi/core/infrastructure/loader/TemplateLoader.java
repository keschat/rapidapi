package com.rapidapi.core.infrastructure.loader;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.rapidapi.core.config.TemplateConfig;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TemplateLoader {
    
    @Inject
    private TemplateConfig templateConfig;
    
    private TemplateEngine templateEngine;
    
    @PostConstruct
    public void init() {
        var resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(templateConfig.getPrefix());
        resolver.setSuffix(templateConfig.getSuffix());
        resolver.setTemplateMode(templateConfig.getMode());
        resolver.setCacheable(templateConfig.isCacheable());
        resolver.setCharacterEncoding(templateConfig.getEncoding());
        
        if (templateConfig.isCacheable()) {
            resolver.setCacheTTLMs((long) templateConfig.getCacheTtlMs());
        }
        
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
    }
    
    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
    
    public String processTemplate(String templateName, org.thymeleaf.context.Context context) {
        return templateEngine.process(templateName, context);
    }
}