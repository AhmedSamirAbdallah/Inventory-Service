package com.service.inventory.controller;

import com.service.inventory.common.ApiResponse;
import com.service.inventory.model.dto.InventoryDto;
import jakarta.ws.rs.QueryParam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/inventory")
public interface InventoryControllerInterface {


    @PostMapping
    ApiResponse createInventoryForProduct(@RequestBody InventoryDto requestDto);

    @GetMapping(path = "/check")
    ApiResponse checkProductAvailability(@QueryParam("productId") String productId, @QueryParam("quantity") Long quantity);


    @PostMapping(path = "/update")
    ApiResponse updateInventory(@RequestBody List<InventoryDto> requestDto);

}
