package com.rapidapi.rest.flash;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@ApplicationScoped
public class FlashMessageManager {

    private static final String FLASH_SESSION_KEY = "flash-messages";

    @Context
    private HttpServletRequest request;

    @SuppressWarnings("unchecked")
    public void addMessage(FlashMessage message) {
        HttpSession session = request.getSession();
        List<FlashMessage> messages = (List<FlashMessage>) session.getAttribute(FLASH_SESSION_KEY);
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        session.setAttribute(FLASH_SESSION_KEY, messages);
    }

    @SuppressWarnings("unchecked")
    public List<FlashMessage> getMessages() {
        HttpSession session = request.getSession();
        List<FlashMessage> messages = (List<FlashMessage>) session.getAttribute(FLASH_SESSION_KEY);
        if (messages == null) {
            messages = new ArrayList<>();
        }
        session.removeAttribute(FLASH_SESSION_KEY);
        return messages;
    }
}
