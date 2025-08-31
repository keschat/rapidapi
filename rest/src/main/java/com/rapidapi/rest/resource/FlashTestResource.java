package com.rapidapi.rest.resource;

import com.rapidapi.rest.flash.FlashMessage;
import com.rapidapi.rest.flash.FlashMessageManager;
import com.rapidapi.rest.flash.MessageType;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/flash")
public class FlashTestResource {

    @Inject
    private FlashMessageManager flashMessageManager;

    @POST
    @Path("/set")
    public Response setFlashMessage() {
        flashMessageManager.addMessage(new FlashMessage("This is a test flash message!", MessageType.INFO));
        return Response.seeOther(URI.create("/flash/display")).build();
    }

    @GET
    @Path("/display")
    public String displayFlashMessages() {
        List<FlashMessage> messages = flashMessageManager.getMessages();
        if (messages.isEmpty()) {
            return "No flash messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (FlashMessage message : messages) {
            sb.append(message.type()).append(": ").append(message.message()).append("\n");
        }
        return sb.toString();
    }
}
