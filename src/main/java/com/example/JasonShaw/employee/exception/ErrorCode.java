package com.example.JasonShaw.employee.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    // Success codes (1000-1999)
    SUCCESS(1000, "Success", HttpStatus.OK),
    CREATED(1001, "Created successfully", HttpStatus.CREATED),

    // Client errors (4000-4999)
    EMPLOYEE_NOT_FOUND(4001, "Employee not found", HttpStatus.NOT_FOUND),
    INVALID_INPUT(4002, "Invalid input data", HttpStatus.BAD_REQUEST),
    EMPLOYEE_ALREADY_EXISTS(4003, "Employee already exists", HttpStatus.CONFLICT),
    DEPARTMENT_NOT_EXISTED(4004, "Department not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_ALREADY_EXISTS(4005, "Department already exists", HttpStatus.CONFLICT),

    // Server errors (5000-5999)
    INTERNAL_SERVER_ERROR(5000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    Integer code;
    String message;
    HttpStatus status;
}