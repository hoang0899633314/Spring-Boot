package com.example.JasonShaw.employee.repository;

import com.example.JasonShaw.employee.model.Department;

import java.util.List;
import java.util.Optional;

public abstract class IDepartmentRepository {
    public abstract List<Department> findAll();

    public abstract Optional<Department> findById(Long id);

    public abstract Optional<Department> findByName(String name);

    public abstract Department save(Department department);

    public abstract boolean deleteById(Long id);

    public abstract boolean existsById(Long id);

    public abstract boolean existsByNameExcludingId(String name, Long excludeId);

    public abstract long count();
}
