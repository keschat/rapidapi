package com.rapidapi.core.infrastructure.web;

public enum MessageType {
    SUCCESS("success"),
    ERROR("error"),
    WARNING("warning"),
    INFO("info");
    
    private final String cssClass;
    
    MessageType(String cssClass) {
        this.cssClass = cssClass;
    }
    
    public String getCssClass() {
        return cssClass;
    }
    
    @Override
    public String toString() {
        return cssClass;
    }
}