package com.service.inventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.inventory.model.dto.InventoryDto;
import com.service.inventory.model.dto.ProductResponseDto;
import com.service.inventory.util.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryConsumer {

    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(InventoryConsumer.class);

    @Autowired
    public InventoryConsumer(InventoryService inventoryService, ObjectMapper objectMapper) {
        this.inventoryService = inventoryService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = Constants.KafkaTopics.PRODUCT_CREATED_EVENT, groupId = "inventory-service")
    public void consumeProductCreatedEvent(ConsumerRecord<String, Object> record) {
        try {
            Map<String, Object> values = objectMapper.convertValue(record.value(), new TypeReference<Map<String, Object>>() {
            }); //Anonymous Subclass
            String id = (String) values.get("id");

            if (id.isEmpty()) {
                logger.warn("Received Kafka message with missing or empty 'id': {}", record.value());
                return;
            }

            inventoryService.createInventoryForProduct(new InventoryDto(id, 0L));
            logger.info("Successfully processed product with ID: {}", id);

        } catch (ClassCastException ex) {
            logger.error("Failed to cast message value: {}", record.value(), ex);
        } catch (Exception ex) {
            logger.error("Unexpected error occurred while processing Kafka message: {}", record.value(), ex);
        }
    }

    @KafkaListener(topics = Constants.KafkaTopics.ORDER_CREATED_EVENT, groupId = "inventory-service")
    public void consumeOrderCreatedEvent(ConsumerRecord<String, Object> record) {
        Map<String, Object> values = objectMapper.convertValue(record.value(), new TypeReference<Map<String, Object>>() {
        });

        List<Map<String, Object>> orderItems = (List<Map<String, Object>>) values.get("orderItems");

        List<InventoryDto> inventoryDtoList = new ArrayList<>();

        for (Map<String, Object> orderItem : orderItems) {
            String productId = (String) orderItem.get("productId");
            Long quantity = Constants.parseLong(orderItem.get("quantity").toString());

            inventoryDtoList.add(new InventoryDto(productId, -quantity));
        }

        inventoryService.updateInventory(inventoryDtoList);
    }

    @KafkaListener(topics = Constants.KafkaTopics.ORDER_CANCELED_EVENT, groupId = "inventory-service")
    public void consumeOrderCanceledEvent(ConsumerRecord<String, Object> record) {
        Map<String, Object> values = objectMapper.convertValue(record.value(), new TypeReference<Map<String, Object>>() {
        });

        List<Map<String, Object>> orderItems = (List<Map<String, Object>>) values.get("orderItems");

        List<InventoryDto> inventoryDtoList = new ArrayList<>();

        for (Map<String, Object> orderItem : orderItems) {
            String productId = (String) orderItem.get("productId");
            Long quantity = Constants.parseLong(orderItem.get("quantity").toString());

            inventoryDtoList.add(new InventoryDto(productId, quantity));
        }

        inventoryService.updateInventory(inventoryDtoList);
    }

}
