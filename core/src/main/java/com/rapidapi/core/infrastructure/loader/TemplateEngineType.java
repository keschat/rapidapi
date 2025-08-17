package com.rapidapi.core.infrastructure.loader;

public enum TemplateEngineType {
    FREEMARKER("freemarker", "org.eclipse.krazo.freemarker.FreemarkerViewEngine", ".ftl"),
    THYMELEAF("thymeleaf", "org.eclipse.krazo.thymeleaf.ThymeleafViewEngine", ".html");
    
    private final String name;
    private final String viewEngineClass;
    private final String defaultExtension;
    
    TemplateEngineType(String name, String viewEngineClass, String defaultExtension) {
        this.name = name;
        this.viewEngineClass = viewEngineClass;
        this.defaultExtension = defaultExtension;
    }
    
    public String getName() {
        return name;
    }
    
    public String getViewEngineClass() {
        return viewEngineClass;
    }
    
    public String getDefaultExtension() {
        return defaultExtension;
    }
}