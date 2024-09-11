package com.service.inventory.controller;

import com.service.inventory.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/inventory")
public interface InventoryInterface {

    @GetMapping(path = "/check")
    ApiResponse checkProductAvailability(@PathVariable String productId, @PathVariable Long quantity);
}
