package com.service.inventory.controller;

import com.service.inventory.common.ApiResponse;
import com.service.inventory.model.dto.InventoryDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/inventory")
public interface InventoryControllerInterface {


    @PostMapping
    ApiResponse createInventoryForProduct(@RequestBody InventoryDto requestDto);

    @GetMapping(path = "/check")
    ApiResponse checkProductAvailability(@RequestParam("productId") String productId, @RequestParam("quantity") Long quantity);


    @PostMapping(path = "/update")
    ApiResponse updateInventory(@RequestBody List<InventoryDto> requestDto);

}
