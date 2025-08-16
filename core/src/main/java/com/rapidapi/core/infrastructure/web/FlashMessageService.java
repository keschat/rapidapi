package com.rapidapi.core.infrastructure.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@SessionScoped
@Named("flash")
public class FlashMessageService implements Serializable {
    
    private List<FlashMessage> messages = new ArrayList<>();
    
    public void addSuccess(String message) {
        messages.add(new FlashMessage("success", message));
    }
    
    public void addError(String message) {
        messages.add(new FlashMessage("error", message));
    }
    
    public void addInfo(String message) {
        messages.add(new FlashMessage("info", message));
    }
    
    public void addWarning(String message) {
        messages.add(new FlashMessage("warning", message));
    }
    
    public List<FlashMessage> getMessages() {
        List<FlashMessage> result = new ArrayList<>(messages);
        messages.clear(); // Clear after reading (flash behavior)
        return result;
    }
    
    public boolean hasMessages() {
        return !messages.isEmpty();
    }
    
    public static class FlashMessage implements Serializable {
        private final String type;
        private final String message;
        
        public FlashMessage(String type, String message) {
            this.type = type;
            this.message = message;
        }
        
        public String getType() { return type; }
        public String getMessage() { return message; }
    }
}