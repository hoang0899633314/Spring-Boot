package com.example.JasonShaw.employee.service.impl;

import com.example.JasonShaw.employee.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee.exception.ApiException;
import com.example.JasonShaw.employee.exception.ErrorCode;
import com.example.JasonShaw.employee.model.Employee;
import com.example.JasonShaw.employee.repository.impl.DepartmentRepository;
import com.example.JasonShaw.employee.repository.impl.EmployeeRepository;
import com.example.JasonShaw.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Employee> search(EmployeeSearchRequest request) {
        return employeeRepository.search(request);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        // Validate department exists
        if (employee.getDepartmentId() != null) {
            if (!departmentRepository.existsById(employee.getDepartmentId())) {
                throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED);
            }
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        if (!employeeRepository.existsById(id)) {
            throw new ApiException(ErrorCode.EMPLOYEE_NOT_FOUND);
        }

        // Validate department exists
        if (employee.getDepartmentId() != null) {
            if (!departmentRepository.existsById(employee.getDepartmentId())) {
                throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXISTED);
            }
        }

        return employeeRepository.update(id, employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ApiException(ErrorCode.EMPLOYEE_NOT_FOUND);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public long countEmployees() {
        return employeeRepository.count();
    }
}