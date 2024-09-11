package com.service.inventory.service;

import com.service.inventory.exception.BusinessException;
import com.service.inventory.model.dto.InventoryRequestDto;
import com.service.inventory.model.entity.Inventory;
import com.service.inventory.repository.InventoryRepository;
import com.service.inventory.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void createInventoryForProduct(InventoryRequestDto requestDto) {

        if (inventoryRepository.existsByProductId(requestDto.productId())) {
            throw new BusinessException(Constants.ErrorMessage.INVENTORY_ALREADY_EXISTS_MSG, HttpStatus.CONFLICT);
        }

        inventoryRepository.save(Inventory.builder()
                .productId(requestDto.productId())
                .quantity(requestDto.quantity())
                .build());
    }

    private Inventory getInventory(String productId) {
        return inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() -> new BusinessException(Constants
                        .ErrorMessage
                        .INVENTORY_NOT_FOUND_MSG, HttpStatus.NOT_FOUND));
    }

    public Boolean checkProductAvailability(String productId, Long quantity) {
        Inventory inventory = getInventory(productId);
        return inventory.getQuantity() >= quantity;
    }
}
