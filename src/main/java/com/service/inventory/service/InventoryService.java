package com.service.inventory.service;

import com.service.inventory.exception.BusinessException;
import com.service.inventory.model.dto.InventoryRequestDto;
import com.service.inventory.model.entity.Inventory;
import com.service.inventory.repository.InventoryRepository;
import com.service.inventory.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void createInventoryForProduct(InventoryRequestDto requestDto) {

        if (inventoryRepository.existsByProductId(requestDto.productId())) {
            throw new BusinessException(Constants.ErrorMessage.INVENTORY_ALREADY_EXISTS_MSG, HttpStatus.CONFLICT);
        }

        inventoryRepository.save(Inventory.builder()
                .productId(requestDto.productId())
                .quantity(requestDto.quantity())
                .build());
    }
}
