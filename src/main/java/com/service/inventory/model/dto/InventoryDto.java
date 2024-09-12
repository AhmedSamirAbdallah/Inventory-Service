package com.service.inventory.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record InventoryDto(
        @NotBlank(message = "Product ID must not be empty.")
        String productId,

        @Min(value = -Integer.MAX_VALUE, message = "Quantity must be an integer.")
        Long quantity
) {
}
