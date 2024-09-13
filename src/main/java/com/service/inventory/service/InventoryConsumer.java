package com.service.inventory.service;

import com.service.inventory.model.dto.InventoryDto;
import com.service.inventory.model.dto.ProductResponseDto;
import com.service.inventory.util.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class InventoryConsumer {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = Constants.KafkaTopics.PRODUCT_CREATED_EVENT, groupId = "inventory-service")
    public void consumeProductCreatedEvent(ConsumerRecord<String, Object> record) {
        LinkedHashMap values = (LinkedHashMap) record.value();
        String id = (String) values.get("id");
        inventoryService.createInventoryForProduct(new InventoryDto(id, 0L));
    }

}
