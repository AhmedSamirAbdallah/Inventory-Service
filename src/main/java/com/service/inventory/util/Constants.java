package com.service.inventory.util;

public class Constants {

    public static class SuccessMessage {
        public static final String INVENTORY_INITIALIZED = "Inventory successfully initialized for the product.";
        public static final String STOCK_UPDATED = "Stock levels successfully updated.";
        //        public static final String PRODUCT_AVAILABLE = "Product is available in the required quantity.";
        public static final String PRODUCT_AVAILABLE = "Product with ID '%s' has sufficient stock. Required quantity: %d";
        public static final String PRODUCT_NOT_AVAILABLE = "Product with ID '%s' does not have sufficient stock. Required quantity: %d";

    }

    public static class ErrorMessage {
        public static final String PRODUCT_NOT_FOUND_MSG = "The requested product was not found.";
        public static final String INSUFFICIENT_STOCK_MSG = "Not enough stock available.";
        public static final String INVENTORY_UPDATE_FAILED_MSG = "Failed to update inventory.";
        public static final String INVALID_REQUEST_MSG = "The request is invalid.";
        public static final String INVENTORY_ALREADY_EXISTS_MSG = "An inventory record already exists for this product.";
        public static final String INVENTORY_NOT_FOUND_MSG = "Inventory record for the requested product was not found.";
        public static final String NEGATIVE_QUANTITY = "Quantity cannot be negative.";

    }

    public static class KafkaTopics {
        public static final String PRODUCT_CREATED_EVENT = "product-created-event";
        public static final String PRODUCT_UPDATED_EVENT = "product-updated-event";
        public static final String PRODUCT_DELETED_EVENT = "product-deleted-event";

        public static final String ORDER_CREATED_EVENT = "order-created-event";
        public static final String ORDER_UPDATED_EVENT = "order-updated-event";
        public static final String ORDER_DELETED_EVENT = "order-deleted-event";
        public static final String ORDER_CANCELED_EVENT = "order-canceled-event";


    }


}
