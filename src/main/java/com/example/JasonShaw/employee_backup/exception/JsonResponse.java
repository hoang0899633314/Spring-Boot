package com.example.JasonShaw.employee_backup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResponse {

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> noContent(String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }
}