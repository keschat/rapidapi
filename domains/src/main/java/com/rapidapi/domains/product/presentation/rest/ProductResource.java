package com.rapidapi.domains.product.presentation.rest;

import java.util.List;
import java.util.Optional;

import com.rapidapi.core.domain.exception.ErrorResponse;
import com.rapidapi.domains.product.application.service.ProductService;
import com.rapidapi.domains.product.domain.model.Product;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public List<Product> list() {
        return productService.findAll();
    }
    
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        Optional<Product> product = productService.find(id);
        return product.map(p -> Response.ok(p).build())
                     .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Product not found")).build());
    }

    @POST
    public Response create(@Valid Product p) {
        Product saved = productService.save(p);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long id, @Valid Product p) {
        Optional<Product> updated = productService.update(id, p);
        return updated.map(product -> Response.ok(product).build())
                     .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Product not found")).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        boolean deleted = productService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse("Product not found")).build();
    }
}
