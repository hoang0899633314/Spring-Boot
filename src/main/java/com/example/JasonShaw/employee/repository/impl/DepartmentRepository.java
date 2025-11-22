package com.example.JasonShaw.employee.repository.impl;

import com.example.JasonShaw.employee.config.ConnectionUtil;
import com.example.JasonShaw.employee.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DepartmentRepository {

    /**
     * Lấy tất cả Department bằng HQL
     */
    public List<Department> findAll() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String hql = "FROM Department ORDER BY id";
            return session.createQuery(hql, Department.class).getResultList();
        } catch (Exception e) {
            log.error("Error finding all departments", e);
            return new ArrayList<>();
        }
    }

    /**
     * Tìm Department theo ID bằng Native SQL
     */
    public Optional<Department> findById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM departments WHERE id = :id";

            Department department = session.createNativeQuery(sql, Department.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(department);
        } catch (Exception e) {
            log.error("Error finding department by id: {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Tìm Department theo tên bằng HQL
     */
    public Optional<Department> findByName(String name) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String hql = "FROM Department d WHERE LOWER(d.name) = LOWER(:name)";

            Department department = session.createQuery(hql, Department.class)
                    .setParameter("name", name)
                    .uniqueResult();

            return Optional.ofNullable(department);
        } catch (Exception e) {
            log.error("Error finding department by name: {}", name, e);
            return Optional.empty();
        }
    }

    /**
     * Lưu Department bằng Hibernate
     */
    public Department save(Department department) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (department.getId() == null) {
                session.persist(department);
            } else {
                department = session.merge(department);
            }

            transaction.commit();
            log.info("Department saved successfully: {}", department.getName());

            return department;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error saving department", e);
            throw new RuntimeException("Failed to save department", e);
        }
    }

    /**
     * Cập nhật Department
     */
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
            log.info("Department updated successfully: {}", existingDepartment.getName());

            return existingDepartment;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error updating department", e);
            throw new RuntimeException("Failed to update department", e);
        }
    }

    /**
     * Xóa Department bằng HQL
     */
    public boolean deleteById(Long id) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Xóa quan hệ với Employee trước
            String updateEmployeesHql = "UPDATE Employee e SET e.department = null WHERE e.department.id = :deptId";
            session.createMutationQuery(updateEmployeesHql)
                    .setParameter("deptId", id)
                    .executeUpdate();

            // Xóa Department
            String hql = "DELETE FROM Department d WHERE d.id = :id";
            int result = session.createMutationQuery(hql)
                    .setParameter("id", id)
                    .executeUpdate();

            transaction.commit();

            if (result > 0) {
                log.info("Department deleted successfully with id: {}", id);
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error deleting department with id: {}", id, e);
            return false;
        }
    }

    /**
     * Kiểm tra Department tồn tại theo ID
     */
    public boolean existsById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(d) FROM Department d WHERE d.id = :id";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking department existence", e);
            return false;
        }
    }

    /**
     * Kiểm tra tên Department đã tồn tại (trừ ID hiện tại)
     */
    public boolean existsByNameAndIdNot(String name, Long excludeId) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(d) FROM Department d WHERE LOWER(d.name) = LOWER(:name) AND d.id != :excludeId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("name", name)
                    .setParameter("excludeId", excludeId)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking department name existence", e);
            return false;
        }
    }

    /**
     * Đếm số Department
     */
    public long count() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(d) FROM Department d";
            return session.createQuery(hql, Long.class).uniqueResult();
        } catch (Exception e) {
            log.error("Error counting departments", e);
            return 0;
        }
    }
}