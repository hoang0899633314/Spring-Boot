package com.example.JasonShaw.employee.controller;

import com.example.JasonShaw.employee.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee.exception.ApiResponse;
import com.example.JasonShaw.employee.exception.JsonResponse;
import com.example.JasonShaw.employee.model.Employee;
import com.example.JasonShaw.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Employee Controller
 * Xử lý các HTTP request liên quan đến nhân viên
 *
 * Base URL: /employees
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final IEmployeeService employeeService;

    /**
     * GET /employees - Tìm kiếm nhân viên với nhiều tiêu chí
     *
     * Query Parameters (tất cả optional):
     * - name: Tên nhân viên (contains, case-insensitive)
     * - dobFrom: Ngày sinh từ (yyyy-MM-dd)
     * - dobTo: Ngày sinh đến (yyyy-MM-dd)
     * - gender: Giới tính (MALE, FEMALE, OTHER)
     * - salaryRange: Khoảng lương (lt5, 5-10, 10-20, gt20)
     * - phone: Số điện thoại (contains)
     * - departmentId: ID bộ phận
     *
     * Example:
     * GET /employees?name=Nguyen&gender=MALE&salaryRange=5-10&departmentId=1
     *
     * @param request DTO chứa các tiêu chí tìm kiếm
     * @return Danh sách nhân viên phù hợp
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> searchEmployees(
            @ModelAttribute EmployeeSearchRequest request) {

        log.info("Received search request: {}", request);
        List<Employee> result = employeeService.search(request);
        log.info("Found {} employees", result.size());

        return JsonResponse.ok(result, "Search employees successfully");
    }

    /**
     * GET /employees/{id} - Lấy nhân viên theo ID
     *
     * @param id ID nhân viên
     * @return Thông tin chi tiết nhân viên
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id) {
        log.info("Getting employee by id: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return JsonResponse.ok(employee, "Get employee successfully");
    }

    /**
     * POST /employees - Tạo mới nhân viên
     *
     * Request Body:
     * {
     *   "name": "Nguyen Van A",
     *   "dob": "1990-01-01",
     *   "gender": "MALE",
     *   "salary": 10000.0,
     *   "phone": "0901234567",
     *   "departmentId": 1
     * }
     *
     * @param employee Thông tin nhân viên mới
     * @return Nhân viên đã được tạo (có ID)
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        log.info("Creating new employee: {}", employee);
        Employee createdEmployee = employeeService.createEmployee(employee);
        return JsonResponse.created(createdEmployee, "Employee created successfully");
    }

    /**
     * PUT /employees/{id} - Cập nhật thông tin nhân viên
     *
     * @param id ID nhân viên cần cập nhật
     * @param employee Thông tin mới
     * @return Nhân viên đã được cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {

        log.info("Updating employee id: {} with data: {}", id, employee);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return JsonResponse.ok(updatedEmployee, "Employee updated successfully");
    }

    /**
     * DELETE /employees/{id} - Xóa nhân viên
     *
     * @param id ID nhân viên cần xóa
     * @return Response không có data
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee id: {}", id);
        employeeService.deleteEmployee(id);
        return JsonResponse.noContent("Employee deleted successfully");
    }

    /**
     * GET /employees/count - Đếm tổng số nhân viên
     *
     * @return Số lượng nhân viên
     */
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countEmployees() {
        log.info("Counting total employees");
        long count = employeeService.countEmployees();
        return JsonResponse.ok(count, "Count employees successfully");
    }
}