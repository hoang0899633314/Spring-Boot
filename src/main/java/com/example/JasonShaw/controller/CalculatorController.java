package com.example.JasonShaw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    @GetMapping("/calculator")
    public ResponseEntity<?> calculate(
            @RequestParam(required = false) String firstNumber,
            @RequestParam(required = false) String secondNumber,
            @RequestParam(required = false) String operator) {

        // 1. Kiểm tra tham số có null không
        if (firstNumber == null || secondNumber == null || operator == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Missing parameters! Required: firstNumber, secondNumber, operator");
        }

        // 2. Kiểm tra firstNumber và secondNumber có phải là số không
        double num1, num2;
        try {
            num1 = Double.parseDouble(firstNumber);
            num2 = Double.parseDouble(secondNumber);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid number format!");
        }

        // 3. Kiểm tra operator hợp lệ
        if (!operator.matches("[+\\-*/]")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid operator! Allowed: +, -, *, /");
        }

        // 4. Kiểm tra chia cho 0
        if (operator.equals("/") && num2 == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Division by zero is not allowed.");
        }

        // 5. Xử lý phép tính
        double result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            default: // "/"
                result = num1 / num2;
        }

        return ResponseEntity.ok(result);
    }
}
