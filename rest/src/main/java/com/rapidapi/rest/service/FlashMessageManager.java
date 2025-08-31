package com.rapidapi.rest.service;

import com.rapidapi.rest.model.FlashMessage;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class FlashMessageManager {

    private static final String FLASH_SESSION_KEY = "flash_message";

    @Inject
    private HttpServletRequest request;

    public void addMessage(FlashMessage message) {
        HttpSession session = request.getSession(true);
        session.setAttribute(FLASH_SESSION_KEY, message);
    }

    public FlashMessage getMessage() {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        FlashMessage message = (FlashMessage) session.getAttribute(FLASH_SESSION_KEY);
        if (message != null) {
            session.removeAttribute(FLASH_SESSION_KEY);
        }
        return message;
    }
}
