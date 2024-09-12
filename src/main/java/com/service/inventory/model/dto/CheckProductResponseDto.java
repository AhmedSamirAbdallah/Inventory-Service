package com.service.inventory.model.dto;

public record CheckProductResponseDto(
        Boolean isAvailable,
        Long quantity
) {
}
