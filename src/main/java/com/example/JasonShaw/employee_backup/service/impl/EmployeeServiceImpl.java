package com.example.JasonShaw.employee_backup.service.impl;

import com.example.JasonShaw.employee_backup.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee_backup.exception.ApiException;
import com.example.JasonShaw.employee_backup.exception.ErrorCode;
import com.example.JasonShaw.employee_backup.model.Employee;
import com.example.JasonShaw.employee_backup.repository.IEmployeeRepository;
import com.example.JasonShaw.employee_backup.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    @Override
    public List<Employee> search(EmployeeSearchRequest request) {
        log.info("Searching employees with criteria: {}", request);
        return employeeRepository.search(request);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        log.info("Getting employee by id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        log.info("Creating new employee: {}", employee);
        validateEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        log.info("Updating employee id: {} with data: {}", id, employee);

        if (!employeeRepository.existsById(id)) {
            throw new ApiException(ErrorCode.EMPLOYEE_NOT_EXISTED);
        }

        validateEmployee(employee);
        employee.setId(id);

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee id: {}", id);

        if (!employeeRepository.existsById(id)) {
            throw new ApiException(ErrorCode.EMPLOYEE_NOT_EXISTED);
        }

        boolean deleted = employeeRepository.deleteById(id);
        if (!deleted) {
            throw new ApiException(ErrorCode.UNCATEGORIZED_EXCEPTION, "Failed to delete employee");
        }
    }

    @Override
    public long countEmployees() {
        log.info("Counting total employees");
        return employeeRepository.count();
    }

    private void validateEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "Employee name is required");
        }

        if (employee.getSalary() != null && employee.getSalary() < 0) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "Salary cannot be negative");
        }
    }
}