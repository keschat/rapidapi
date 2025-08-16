package com.rapidapi.core.infrastructure.web;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestScoped
@Named("flash")
public class PersistentFlashMessages {
    
    private static final String FLASH_COOKIE_NAME = "FLASH_SESSION_ID";
    private static final int COOKIE_MAX_AGE = 300; // 5 minutes
    
    @Inject
    private HttpServletRequest request;
    
    @Inject
    private HttpServletResponse response;
    
    // In production, inject a Redis/Database service here
    // @Inject private FlashMessageRepository repository;
    
    // For now, using in-memory storage (replace with Redis/DB)
    private static final java.util.Map<String, List<FlashMessage>> messageStore = 
        new java.util.concurrent.ConcurrentHashMap<>();
    
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
        String sessionId = getOrCreateFlashSessionId();
        
        messageStore.computeIfAbsent(sessionId, k -> new ArrayList<>())
                   .add(new FlashMessage(type, message));
        
        // In production: repository.saveMessage(sessionId, new FlashMessage(type, message));
    }
    
    public List<FlashMessage> getMessages() {
        String sessionId = getFlashSessionId();
        if (sessionId == null) {
            return new ArrayList<>();
        }
        
        List<FlashMessage> messages = messageStore.remove(sessionId);
        // In production: List<FlashMessage> messages = repository.getAndDeleteMessages(sessionId);
        
        return messages != null ? messages : new ArrayList<>();
    }
    
    public boolean hasMessages() {
        String sessionId = getFlashSessionId();
        if (sessionId == null) {
            return false;
        }
        
        List<FlashMessage> messages = messageStore.get(sessionId);
        // In production: return repository.hasMessages(sessionId);
        
        return messages != null && !messages.isEmpty();
    }
    
    private String getFlashSessionId() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (FLASH_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    private String getOrCreateFlashSessionId() {
        String sessionId = getFlashSessionId();
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(FLASH_COOKIE_NAME, sessionId);
            cookie.setMaxAge(COOKIE_MAX_AGE);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
        return sessionId;
    }
    
    public static class FlashMessage implements Serializable {
        private final String type;
        private final String message;
        private final LocalDateTime timestamp;
        
        public FlashMessage(String type, String message) {
            this.type = type;
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }
        
        public String getType() { return type; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}