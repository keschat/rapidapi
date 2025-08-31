package com.rapidapi.rest.resource;

import org.glassfish.jersey.server.mvc.Viewable;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/test")
public class TestResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable test() {
        return new Viewable("/test-view.html");
    }
}
