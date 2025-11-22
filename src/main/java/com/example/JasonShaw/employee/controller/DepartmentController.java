package com.example.JasonShaw.employee.controller;

import com.example.JasonShaw.employee.exception.ErrorCode;
import com.example.JasonShaw.employee.model.Department;
import com.example.JasonShaw.employee.exception.ApiResponse;
import com.example.JasonShaw.employee.exception.ApiException;
import com.example.JasonShaw.employee.exception.JsonResponse;
import com.example.JasonShaw.employee.repository.impl.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    // GET /departments - Lấy tất cả bộ phận
    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return JsonResponse.ok(departments, "Get all departments successfully");
    }

    // GET /departments/{id} - Lấy bộ phận theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED));
        return JsonResponse.ok(department, "Get department successfully");
    }

    // POST /departments - Tạo mới bộ phận
    @PostMapping
    public ResponseEntity<ApiResponse<Department>> createDepartment(@RequestBody Department department) {
        // Kiểm tra tên bộ phận đã tồn tại chưa
        if (departmentRepository.findByName(department.getName()).isPresent()) {
            throw new ApiException(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
        }

        Department savedDepartment = departmentRepository.save(department);
        return JsonResponse.created(savedDepartment, "Department created successfully");
    }

    // PUT /departments/{id} - Cập nhật bộ phận
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> updateDepartment(
            @PathVariable Long id,
            @RequestBody Department updatedDepartment) {

        if (!departmentRepository.existsById(id)) {
            throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }

        // Kiểm tra tên mới có trùng với bộ phận khác không
        if (departmentRepository.existsByNameAndIdNot(updatedDepartment.getName(), id)) {
            throw new ApiException(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
        }

        Department department = departmentRepository.update(id, updatedDepartment);
        return JsonResponse.ok(department, "Department updated successfully");
    }

    // DELETE /departments/{id} - Xóa bộ phận
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }

        departmentRepository.deleteById(id);
        return JsonResponse.noContent("Department deleted successfully");
    }
}