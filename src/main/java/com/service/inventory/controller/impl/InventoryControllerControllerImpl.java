package com.service.inventory.controller.impl;

import com.service.inventory.common.ApiResponse;
import com.service.inventory.controller.InventoryControllerInterface;
import com.service.inventory.model.dto.CheckProductResponseDto;
import com.service.inventory.model.dto.InventoryDto;
import com.service.inventory.service.InventoryService;
import com.service.inventory.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryControllerControllerImpl implements InventoryControllerInterface {
    private InventoryService inventoryService;

    @Autowired
    public InventoryControllerControllerImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public ApiResponse createInventoryForProduct(InventoryDto requestDto) {
        return ApiResponse.success(inventoryService.createInventoryForProduct(requestDto),
                Constants.SuccessMessage.INVENTORY_INITIALIZED, HttpStatus.CREATED);
    }


    @Override
    public ApiResponse checkProductAvailability(String productId, Long quantity) {
        CheckProductResponseDto responseDto = inventoryService.checkProductAvailability(productId, quantity);

        String message = responseDto.isAvailable() ?
                String.format(Constants.SuccessMessage.PRODUCT_AVAILABLE, productId, quantity)
                : String.format(Constants.SuccessMessage.PRODUCT_NOT_AVAILABLE, productId, quantity);

        return ApiResponse.success(responseDto, message, HttpStatus.OK);
    }

    public ApiResponse updateInventory(List<InventoryDto> requestDtoList) {

        return ApiResponse.success(inventoryService.updateInventory(requestDtoList), Constants.SuccessMessage.STOCK_UPDATED, HttpStatus.OK);
    }

}
