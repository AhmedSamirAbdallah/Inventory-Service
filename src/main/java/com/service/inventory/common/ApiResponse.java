package com.service.inventory.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {
    private T payload;
    private String message;
    private HttpStatus httpStatus;

    public static <T> ApiResponse<T> success(T payload, String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }

    public static <T> ApiResponse<T> success(T payload, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .message(null)
                .httpStatus(httpStatus)
                .build();
    }

    public static <T> ApiResponse<T> error(T payload, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .message(null)
                .httpStatus(httpStatus)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .payload(null)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
