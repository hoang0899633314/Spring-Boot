package com.example.JasonShaw.employee_backup.controller;

import com.example.JasonShaw.employee_backup.exception.ErrorCode;
import com.example.JasonShaw.employee_backup.model.Department;
import com.example.JasonShaw.employee_backup.exception.ApiResponse;
import com.example.JasonShaw.employee_backup.exception.ApiException;
import com.example.JasonShaw.employee_backup.exception.JsonResponse;
import com.example.JasonShaw.employee_backup.repository.impl.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return JsonResponse.ok(departments, "Get all departments successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED));
        return JsonResponse.ok(department, "Get department successfully");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> createDepartment(@RequestBody Department department) {
        if (departmentRepository.findByName(department.getName()).isPresent()) {
            throw new ApiException(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
        }
        Department savedDepartment = departmentRepository.save(department);
        return JsonResponse.created(savedDepartment, "Department created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> updateDepartment(
            @PathVariable Long id,
            @RequestBody Department updatedDepartment) {

        if (!departmentRepository.existsById(id)) {
            throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }

        if (departmentRepository.existsByNameAndIdNot(updatedDepartment.getName(), id)) {
            throw new ApiException(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
        }

        Department department = departmentRepository.update(id, updatedDepartment);
        return JsonResponse.ok(department, "Department updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }

        departmentRepository.deleteById(id);
        return JsonResponse.noContent("Department deleted successfully");
    }
}