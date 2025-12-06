package com.example.JasonShaw.employee_backup.repository.impl;

import com.example.JasonShaw.util.ConnectionUtil;
import com.example.JasonShaw.employee_backup.model.Department;
import com.example.JasonShaw.employee_backup.repository.IDepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Department Repository Implementation - Hibernate Version
 * All methods use Native SQL or session methods (NO HQL) to avoid errors
 */
@Repository
@Slf4j
public class DepartmentRepository extends IDepartmentRepository {

    @Override
    public List<Department> findAll() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM department ORDER BY id";
            return session.createNativeQuery(sql, Department.class).getResultList();
        } catch (Exception e) {
            log.error("Error finding all departments", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM department WHERE id = :id";
            Department department = (Department) session.createNativeQuery(sql, Department.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return Optional.ofNullable(department);
        } catch (Exception e) {
            log.error("Error finding department by id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Department> findByName(String name) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM department WHERE LOWER(name) = LOWER(:name)";
            Department department = (Department) session.createNativeQuery(sql, Department.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return Optional.ofNullable(department);
        } catch (Exception e) {
            log.error("Error finding department by name: {}", name, e);
            return Optional.empty();
        }
    }

    @Override
    public Department save(Department department) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (department.getId() == null) {
                session.persist(department);
                log.info("Department created: {}", department.getName());
            } else {
                department = (Department) session.merge(department);
                log.info("Department updated: {}", department.getName());
            }

            transaction.commit();
            return department;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error saving department", e);
            throw new RuntimeException("Failed to save department", e);
        }
    }

    public Department update(Long id, Department updatedDepartment) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Department existingDepartment = session.get(Department.class, id);
            if (existingDepartment == null) {
                throw new RuntimeException("Department not found with id: " + id);
            }

            existingDepartment.setName(updatedDepartment.getName());
            session.merge(existingDepartment);

            transaction.commit();
            log.info("Department updated: {}", existingDepartment.getName());

            return existingDepartment;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error updating department", e);
            throw new RuntimeException("Failed to update department", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Set department_id = NULL for employees
            String updateSql = "UPDATE employee SET department_id = NULL WHERE department_id = :deptId";
            session.createNativeQuery(updateSql)
                    .setParameter("deptId", id)
                    .executeUpdate();

            // Delete department
            Department department = session.get(Department.class, id);

            if (department != null) {
                session.delete(department);
                transaction.commit();
                log.info("Department deleted with id: {}", id);
                return true;
            } else {
                log.warn("Department not found with id: {}", id);
                if (transaction != null) transaction.rollback();
                return false;
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting department with id: {}", id, e);
            return false;
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            Department department = session.get(Department.class, id);
            return department != null;
        } catch (Exception e) {
            log.error("Error checking department existence", e);
            return false;
        }
    }

    @Override
    public boolean existsByNameExcludingId(String name, Long excludeId) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT COUNT(*) FROM department WHERE LOWER(name) = LOWER(:name) AND id != :excludeId";
            Number count = (Number) session.createNativeQuery(sql)
                    .setParameter("name", name)
                    .setParameter("excludeId", excludeId)
                    .uniqueResult();
            return count != null && count.longValue() > 0;
        } catch (Exception e) {
            log.error("Error checking department name existence", e);
            return false;
        }
    }

    public boolean existsByNameAndIdNot(String name, Long excludeId) {
        return existsByNameExcludingId(name, excludeId);
    }

    @Override
    public long count() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT COUNT(*) FROM department";
            Number result = (Number) session.createNativeQuery(sql).uniqueResult();
            return result != null ? result.longValue() : 0;
        } catch (Exception e) {
            log.error("Error counting departments", e);
            return 0;
        }
    }
}