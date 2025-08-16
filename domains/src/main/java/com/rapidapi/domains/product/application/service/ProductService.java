package com.rapidapi.domains.product.application.service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rapidapi.core.application.service.BaseService;
import com.rapidapi.domains.product.domain.model.Product;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService extends BaseService {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (var conn = getConnection();
                var stmt = conn.prepareStatement("SELECT id, name, price FROM products");
                var rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"));
                products.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products", e);
        }
        return products;
    }

    public Optional<Product> find(long id) {
        try (var conn = getConnection();
                var stmt = conn.prepareStatement("SELECT id, name, price FROM products WHERE id = ?")) {

            stmt.setLong(1, id);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"));
                    return Optional.of(p);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product", e);
        }
        return Optional.empty();
    }

    public Product save(Product p) {
        try (var conn = getConnection();
                var stmt = conn.prepareStatement("INSERT INTO products (name, price) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.name());
            stmt.setDouble(2, p.price());
            stmt.executeUpdate();

            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Product(rs.getLong(1), p.name(), p.price());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save product", e);
        }
        return p;
    }

    public Optional<Product> update(long id, Product p) {
        try (var conn = getConnection();
                var stmt = conn.prepareStatement("UPDATE products SET name = ?, price = ? WHERE id = ?")) {

            stmt.setString(1, p.name());
            stmt.setDouble(2, p.price());
            stmt.setLong(3, id);
            int rowsUpdated = stmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                return Optional.of(new Product(id, p.name(), p.price()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product", e);
        }
        return Optional.empty();
    }

    public boolean delete(long id) {
        try (var conn = getConnection();
                var stmt = conn.prepareStatement("DELETE FROM products WHERE id = ?")) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product", e);
        }
    }
}
