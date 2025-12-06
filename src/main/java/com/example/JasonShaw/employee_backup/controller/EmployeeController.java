package com.example.JasonShaw.employee_backup.controller;

import com.example.JasonShaw.employee_backup.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee_backup.exception.ApiResponse;
import com.example.JasonShaw.employee_backup.exception.JsonResponse;
import com.example.JasonShaw.employee_backup.model.Employee;
import com.example.JasonShaw.employee_backup.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> searchEmployees(
            @ModelAttribute EmployeeSearchRequest request) {
        log.info("Received search request: {}", request);
        List<Employee> result = employeeService.search(request);
        log.info("Found {} employees", result.size());
        return JsonResponse.ok(result, "Search employees successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id) {
        log.info("Getting employee by id: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return JsonResponse.ok(employee, "Get employee successfully");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        log.info("Creating new employee: {}", employee);
        Employee createdEmployee = employeeService.createEmployee(employee);
        return JsonResponse.created(createdEmployee, "Employee created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        log.info("Updating employee id: {} with data: {}", id, employee);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return JsonResponse.ok(updatedEmployee, "Employee updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee id: {}", id);
        employeeService.deleteEmployee(id);
        return JsonResponse.noContent("Employee deleted successfully");
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countEmployees() {
        log.info("Counting total employees");
        long count = employeeService.countEmployees();
        return JsonResponse.ok(count, "Count employees successfully");
    }
}