package com.example.JasonShaw.employee.service;

import com.example.JasonShaw.employee.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee.model.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> search(EmployeeSearchRequest request);
    Employee getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    long countEmployees();
}