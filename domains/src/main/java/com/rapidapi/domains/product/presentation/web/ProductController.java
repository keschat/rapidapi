package com.rapidapi.domains.product.presentation.web;

import java.util.List;

import java.util.Map;
import java.util.HashMap;

import com.rapidapi.domains.product.application.service.ProductService;
import com.rapidapi.domains.product.domain.model.Product;
import com.rapidapi.core.infrastructure.web.FlashMessageService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ProductController {
    
    // Using your existing Thymeleaf infrastructure from shared-kernel

    @Inject
    private ProductService productService;
    
    @Inject
    private FlashMessageService flash;

    @GetMapping
    public String listProducts() {
        List<Product> products = productService.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("products", products);
        model.put("title", "Product Management");
        return renderTemplate("product/list", model);
    }

    @GetMapping("/new")
    public String newProductForm() {
        Map<String, Object> model = new HashMap<>();
        model.put("product", new Product(0L, "", 0.0));
        model.put("title", "Add New Product");
        return renderTemplate("product/form", model);
    }

    @GetMapping("/{id}")
    public String viewProduct(Long id) {
        return productService.find(id)
            .map(product -> {
                Map<String, Object> model = new HashMap<>();
                model.put("product", product);
                model.put("title", "Product Details");
                return renderTemplate("product/view", model);
            })
            .orElse("redirect:/domains/product?error=notfound");
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(Long id) {
        return productService.find(id)
            .map(product -> {
                Map<String, Object> model = new HashMap<>();
                model.put("product", product);
                model.put("title", "Edit Product");
                return renderTemplate("product/form", model);
            })
            .orElse("redirect:/domains/product?error=notfound");
    }

    @PostMapping
    public String saveProduct(Product product) {
        try {
            if (product.id() == 0) {
                productService.save(product);
                flash.addSuccess("Product created successfully");
            } else {
                productService.update(product.id(), product);
                flash.addSuccess("Product updated successfully");
            }
            return "redirect:/domains/product";
        } catch (Exception e) {
            flash.addError("Failed to save product: " + e.getMessage());
            return "redirect:/domains/product/new";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(Long id) {
        try {
            if (productService.delete(id)) {
                flash.addSuccess("Product deleted successfully");
            } else {
                flash.addError("Product not found");
            }
        } catch (Exception e) {
            flash.addError("Failed to delete product: " + e.getMessage());
        }
        return "redirect:/domains/product";
    }
    
    private String renderTemplate(String templateName, Map<String, Object> model) {
        // Use your existing TemplateLoader from shared-kernel
        // This will be integrated with your Jetty server's template handling
        return templateName; // Placeholder - integrate with your template system
    }
}