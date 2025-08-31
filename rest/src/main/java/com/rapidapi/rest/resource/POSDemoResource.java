package com.rapidapi.rest.resource;

import com.rapidapi.rest.model.FlashMessage;
import com.rapidapi.rest.service.FlashMessageManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

import java.net.URI;
import java.util.Map;

@Path("/pos-demo")
public class POSDemoResource {

    @Inject
    private FlashMessageManager flashMessageManager;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable showDemo() {
        FlashMessage message = flashMessageManager.getMessage();
        Map<String, Object> model = message != null ? Map.of("flash", message) : Map.of();
        return new Viewable("/pos-demo.html", model);
    }

    @POST
    public Response addMessage() {
        flashMessageManager.addMessage(new FlashMessage("success", "A new message has been added!"));
        return Response.seeOther(URI.create("/pos-demo")).build();
    }
}
