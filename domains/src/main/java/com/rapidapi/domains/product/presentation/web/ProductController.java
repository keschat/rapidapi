package com.rapidapi.domains.product.presentation.web;

import java.util.List;

import com.rapidapi.domains.product.application.service.ProductService;
import com.rapidapi.domains.product.domain.model.Product;
import com.rapidapi.core.infrastructure.web.SessionFlashMessages;

import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Controller
@Path("/product")
public class ProductController {
    
    // Using your existing Thymeleaf infrastructure from shared-kernel

    @Inject
    private ProductService productService;
    
    @Inject
    private SessionFlashMessages flash;
    
    @Inject
    private Models models;

    @GET
    @View("product/list.ftl")
    public void listProducts() {
        List<Product> products = productService.findAll();
        models.put("products", products);
        models.put("title", "Product Management");
    }

    @GET
    @Path("/new")
    @View("product/form.ftl")
    public void newProductForm() {
        models.put("product", new Product(0L, "", 0.0));
        models.put("title", "Add New Product");
    }

    @GET
    @Path("/{id}")
    @View("product/view.ftl")
    public Response viewProduct(@PathParam("id") Long id) {
        return productService.find(id)
            .map(product -> {
                models.put("product", product);
                models.put("title", "Product Details");
                return Response.ok().build();
            })
            .orElseGet(() -> {
                flash.addError("Product not found");
                return Response.status(Response.Status.SEE_OTHER)
                    .location(java.net.URI.create("/domains/product"))
                    .build();
            });
    }

    @GET
    @Path("/{id}/edit")
    @View("product/form.ftl")
    public Response editProductForm(@PathParam("id") Long id) {
        return productService.find(id)
            .map(product -> {
                models.put("product", product);
                models.put("title", "Edit Product");
                return Response.ok().build();
            })
            .orElseGet(() -> {
                flash.addError("Product not found");
                return Response.status(Response.Status.SEE_OTHER)
                    .location(java.net.URI.create("/domains/product"))
                    .build();
            });
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response saveProduct(@FormParam("id") Long id,
                               @FormParam("name") String name,
                               @FormParam("price") Double price) {
        try {
            Product product = new Product(id != null ? id : 0L, name, price);
            
            if (product.id() == 0) {
                productService.save(product);
                flash.addSuccess("Product created successfully");
            } else {
                productService.update(product.id(), product);
                flash.addSuccess("Product updated successfully");
            }
            
            return Response.status(Response.Status.SEE_OTHER)
                .location(java.net.URI.create("/domains/product"))
                .build();
        } catch (Exception e) {
            flash.addError("Failed to save product: " + e.getMessage());
            return Response.status(Response.Status.SEE_OTHER)
                .location(java.net.URI.create("/domains/product/new"))
                .build();
        }
    }

    @POST
    @Path("/{id}/delete")
    public Response deleteProduct(@PathParam("id") Long id) {
        try {
            if (productService.delete(id)) {
                flash.addSuccess("Product deleted successfully");
            } else {
                flash.addError("Product not found");
            }
        } catch (Exception e) {
            flash.addError("Failed to delete product: " + e.getMessage());
        }
        
        return Response.status(Response.Status.SEE_OTHER)
            .location(java.net.URI.create("/domains/product"))
            .build();
    }
}