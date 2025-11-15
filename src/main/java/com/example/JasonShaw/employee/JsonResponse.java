package com.example.JasonShaw.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResponse {

    /**
     * Trả về response thành công với HTTP 200 OK
     * @param data Dữ liệu cần trả về
     * @param message Thông báo
     */
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(1000)
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Trả về response thành công với HTTP 200 OK (message mặc định)
     */
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ok(data, "Success");
    }

    /**
     * Trả về response đã tạo mới với HTTP 201 CREATED
     * @param data Dữ liệu đã được tạo
     * @param message Thông báo
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(1001)
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Trả về response đã tạo mới với HTTP 201 CREATED (message mặc định)
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return created(data, "Created successfully");
    }

    /**
     * Trả về response không có nội dung với HTTP 200 OK (dùng cho DELETE)
     * @param message Thông báo
     */
    public static ResponseEntity<ApiResponse<Void>> noContent(String message) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(1000)
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Trả về response không có nội dung (message mặc định)
     */
    public static ResponseEntity<ApiResponse<Void>> noContent() {
        return noContent("Deleted successfully");
    }
}