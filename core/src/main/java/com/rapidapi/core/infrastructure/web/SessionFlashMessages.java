package com.rapidapi.core.infrastructure.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestScoped
@Named("flash")
public class SessionFlashMessages {
    
    private static final String FLASH_MESSAGES_KEY = "FLASH_MESSAGES";
    
    @Inject
    private HttpServletRequest request;
    
    public void addSuccess(String message) {
        addMessage("success", message);
    }
    
    public void addError(String message) {
        addMessage("error", message);
    }
    
    public void addInfo(String message) {
        addMessage("info", message);
    }
    
    public void addWarning(String message) {
        addMessage("warning", message);
    }
    
    private void addMessage(String type, String message) {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        List<FlashMessage> messages = (List<FlashMessage>) session.getAttribute(FLASH_MESSAGES_KEY);
        
        if (messages == null) {
            messages = new ArrayList<>();
            session.setAttribute(FLASH_MESSAGES_KEY, messages);
        }
        
        messages.add(new FlashMessage(type, message));
    }
    
    public List<FlashMessage> getMessages() {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ArrayList<>();
        }
        
        @SuppressWarnings("unchecked")
        List<FlashMessage> messages = (List<FlashMessage>) session.getAttribute(FLASH_MESSAGES_KEY);
        
        if (messages != null) {
            // Clear messages after reading (flash behavior)
            session.removeAttribute(FLASH_MESSAGES_KEY);
            return new ArrayList<>(messages);
        }
        
        return new ArrayList<>();
    }
    
    public boolean hasMessages() {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        List<FlashMessage> messages = (List<FlashMessage>) session.getAttribute(FLASH_MESSAGES_KEY);
        return messages != null && !messages.isEmpty();
    }
    
    public static class FlashMessage implements Serializable {
        private final String type;
        private final String message;
        private final long timestamp;
        
        public FlashMessage(String type, String message) {
            this.type = type;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getType() { return type; }
        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
    }
}