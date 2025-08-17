package com.rapidapi.core.infrastructure.web;

import java.io.Serializable;
import java.time.LocalDateTime;

public record FlashMessage(
    String message, 
    MessageType type,
    LocalDateTime timestamp
) implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public FlashMessage(String message, MessageType type) {
        this(message, type, LocalDateTime.now());
    }
    
    public String getType() {
        return type.getCssClass();
    }
    
    public String getMessage() {
        return message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}