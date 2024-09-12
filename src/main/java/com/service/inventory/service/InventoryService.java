package com.service.inventory.service;

import com.service.inventory.exception.BusinessException;
import com.service.inventory.mapper.MapStructMapper;
import com.service.inventory.model.dto.CheckProductResponseDto;
import com.service.inventory.model.dto.InventoryDto;
import com.service.inventory.model.entity.Inventory;
import com.service.inventory.repository.InventoryRepository;
import com.service.inventory.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final MapStructMapper mapStructMapper;

    public InventoryService(InventoryRepository inventoryRepository, MapStructMapper mapStructMapper) {
        this.inventoryRepository = inventoryRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Transactional
    public InventoryDto createInventoryForProduct(InventoryDto requestDto) {

        if (inventoryRepository.existsByProductId(requestDto.productId())) {
            throw new BusinessException(Constants.ErrorMessage.INVENTORY_ALREADY_EXISTS_MSG, HttpStatus.CONFLICT);
        }

        Inventory inventory = inventoryRepository.save(Inventory.builder()
                .productId(requestDto.productId())
                .quantity(requestDto.quantity())
                .minimumStockLevel(0L)
                .build());

        return mapStructMapper.toInventoryDto(inventory);
    }

    private Inventory getInventory(String productId) {
        return inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() -> new BusinessException(Constants
                        .ErrorMessage
                        .INVENTORY_NOT_FOUND_MSG, HttpStatus.NOT_FOUND));
    }

    public CheckProductResponseDto checkProductAvailability(String productId, Long quantity) {
        Inventory inventory = getInventory(productId);
        return new CheckProductResponseDto(inventory.getQuantity() >= quantity, inventory.getQuantity());
    }

    @Transactional
    public List<InventoryDto> updateInventory(List<InventoryDto> requestDtoList) {
        List<InventoryDto> response = new ArrayList<>();
        for (InventoryDto request : requestDtoList) {

            Inventory inventory = getInventory(request.productId());
            Long newQuantity = inventory.getQuantity() + request.quantity();

            if (newQuantity < 0) {
                throw new BusinessException(Constants.ErrorMessage.NEGATIVE_QUANTITY, HttpStatus.BAD_REQUEST);
            }

            inventory.setQuantity(newQuantity);

            inventory = inventoryRepository.save(inventory);

            response.add(mapStructMapper.toInventoryDto(inventory));
        }
        return response;
    }
}
