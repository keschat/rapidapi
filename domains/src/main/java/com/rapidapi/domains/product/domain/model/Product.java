package com.rapidapi.domains.product.domain.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Product(Long id, @NotBlank(message = "Product name is required") String name,
        @Min(value = 0, message = "Price cannot be negative") @Max(value = 1000, message = "Price cannot exceed 1000") double price) {
}
