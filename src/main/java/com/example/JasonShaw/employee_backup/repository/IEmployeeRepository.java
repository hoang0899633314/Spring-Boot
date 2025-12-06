package com.example.JasonShaw.employee_backup.repository;

import com.example.JasonShaw.employee_backup.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee_backup.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepository {
    List<Employee> search(EmployeeSearchRequest request);
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    boolean deleteById(Long id);
    boolean existsById(Long id);
    long count();
}