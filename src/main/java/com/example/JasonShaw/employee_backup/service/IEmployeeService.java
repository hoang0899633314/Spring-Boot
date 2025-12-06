package com.example.JasonShaw.employee_backup.service;

import com.example.JasonShaw.employee_backup.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee_backup.model.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> search(EmployeeSearchRequest request);
    Employee getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    long countEmployees();
}