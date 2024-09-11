package com.service.inventory.controller.impl;

import com.service.inventory.common.ApiResponse;
import com.service.inventory.controller.InventoryInterface;
import com.service.inventory.service.InventoryService;
import com.service.inventory.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class InventoryImpl implements InventoryInterface {
    private InventoryService inventoryService;

    @Autowired
    public InventoryImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public ApiResponse checkProductAvailability(String productId, Long quantity) {
        Boolean isAvailable = inventoryService.checkProductAvailability(productId, quantity);

        String message = isAvailable ?
                String.format(Constants.SuccessMessage.PRODUCT_AVAILABLE, productId, quantity)
                : String.format(Constants.SuccessMessage.PRODUCT_NOT_AVAILABLE, productId, quantity);

        return ApiResponse.success(isAvailable, message, HttpStatus.OK);
    }
}
