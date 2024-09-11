package com.service.inventory.model.dto;

public record InventoryRequestDto(
        String productId,
        Long quantity
) {
}
