package com.example.JasonShaw.employee_backup.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Error Code Enum
 */
@Getter
public enum ErrorCode {
    // Success
    SUCCESS(1000, "Success", HttpStatus.OK),

    // Employee Errors
    EMPLOYEE_NOT_EXISTED(2001, "Employee not found", HttpStatus.NOT_FOUND),
    EMPLOYEE_ALREADY_EXISTS(2002, "Employee already exists", HttpStatus.BAD_REQUEST),

    // Department Errors
    DEPARTMENT_NOT_EXISTED(3001, "Department not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_ALREADY_EXISTS(3002, "Department already exists", HttpStatus.BAD_REQUEST),

    // Validation Errors
    INVALID_INPUT(4001, "Invalid input data", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD(4002, "Missing required field", HttpStatus.BAD_REQUEST),

    // System Errors
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR(9001, "Database error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}