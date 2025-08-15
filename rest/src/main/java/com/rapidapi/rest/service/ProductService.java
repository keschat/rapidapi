package com.rapidapi.rest.service;

import jakarta.enterprise.context.ApplicationScoped;
import com.rapidapi.rest.model.Product;
import java.util.List;

@ApplicationScoped
public class ProductService {

    public List<Product> getAllProducts() {
        // Dummy data for now
        return List.of(
            new Product(1L, "Laptop", 999.99),
            new Product(2L, "Mouse", 29.99),
            new Product(3L, "Keyboard", 79.99)
        );
    }

    public Product getProductById(Long id) {
        return getAllProducts().stream()
            .filter(p -> p.id().equals(id))
            .findFirst()
            .orElse(null);
    }
}