package com.example.JasonShaw.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    // Danh sách nhân viên lưu trong bộ nhớ
    private List<Employee> employees = new ArrayList<>();

    // Biến đếm để tạo ID tự tăng
    private Long nextId = 1L;

    // Khởi tạo dữ liệu mẫu
    public EmployeeController() {
        employees.add(new Employee(
                nextId++,
                "Nguyen Van A",
                "1990-05-15",
                Gender.MALE,
                15000000.0,
                "0901234567"
        ));
        employees.add(new Employee(
                nextId++,
                "Tran Thi B",
                "1995-08-20",
                Gender.FEMALE,
                18000000.0,
                "0912345678"
        ));
        employees.add(new Employee(
                nextId++,
                "Le Van C",
                "1988-12-10",
                Gender.MALE,
                20000000.0,
                "0923456789"
        ));
    }

    // 1. GET /employees - Lấy tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employees);
    }

    // 2. GET /employees/{id} - Lấy nhân viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Employee employee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found with id: " + id);
        }

        return ResponseEntity.ok(employee);
    }

    // 3. POST /employees - Tạo mới nhân viên
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        // Tự động sinh ID tăng dần
        employee.setId(nextId++);
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    // 4. PUT /employees/{id} - Cập nhật thông tin nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found with id: " + id);
        }

        // Cập nhật thông tin
        employee.setName(updatedEmployee.getName());
        employee.setDob(updatedEmployee.getDob());
        employee.setGender(updatedEmployee.getGender());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setPhone(updatedEmployee.getPhone());

        return ResponseEntity.ok(employee);
    }

    // 5. DELETE /employees/{id} - Xóa nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        Employee employee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found with id: " + id);
        }

        employees.remove(employee);
        return ResponseEntity.ok("Employee deleted successfully with id: " + id);
    }
}