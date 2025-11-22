package com.example.JasonShaw.employee.repository.impl;

import com.example.JasonShaw.employee.config.ConnectionUtil;
import com.example.JasonShaw.employee.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee.model.Department;
import com.example.JasonShaw.employee.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class EmployeeRepository {

    /**
     * Tìm kiếm linh hoạt bằng HQL
     */
    public List<Employee> search(EmployeeSearchRequest request) {
        List<Employee> results = new ArrayList<>();

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Employee e WHERE 1=1");

            // Build dynamic HQL
            if (request.getName() != null && !request.getName().isEmpty()) {
                hql.append(" AND LOWER(e.name) LIKE LOWER(:name)");
            }

            if (request.getDobFrom() != null && !request.getDobFrom().isEmpty()) {
                hql.append(" AND e.dob >= :dobFrom");
            }

            if (request.getDobTo() != null && !request.getDobTo().isEmpty()) {
                hql.append(" AND e.dob <= :dobTo");
            }

            if (request.getGender() != null) {
                hql.append(" AND e.gender = :gender");
            }

            if (request.getPhone() != null && !request.getPhone().isEmpty()) {
                hql.append(" AND e.phone LIKE :phone");
            }

            if (request.getDepartmentId() != null) {
                hql.append(" AND e.department.id = :departmentId");
            }

            // Salary Range conditions
            if (request.getSalaryRange() != null && !request.getSalaryRange().isEmpty()) {
                switch (request.getSalaryRange()) {
                    case "lt5":
                        hql.append(" AND e.salary < 5000000");
                        break;
                    case "5-10":
                        hql.append(" AND e.salary >= 5000000 AND e.salary < 10000000");
                        break;
                    case "10-20":
                        hql.append(" AND e.salary >= 10000000 AND e.salary < 20000000");
                        break;
                    case "gt20":
                        hql.append(" AND e.salary >= 20000000");
                        break;
                }
            }

            Query<Employee> query = session.createQuery(hql.toString(), Employee.class);

            // Set parameters
            if (request.getName() != null && !request.getName().isEmpty()) {
                query.setParameter("name", "%" + request.getName() + "%");
            }

            if (request.getDobFrom() != null && !request.getDobFrom().isEmpty()) {
                query.setParameter("dobFrom", request.getDobFrom());
            }

            if (request.getDobTo() != null && !request.getDobTo().isEmpty()) {
                query.setParameter("dobTo", request.getDobTo());
            }

            if (request.getGender() != null) {
                query.setParameter("gender", request.getGender());
            }

            if (request.getPhone() != null && !request.getPhone().isEmpty()) {
                query.setParameter("phone", "%" + request.getPhone() + "%");
            }

            if (request.getDepartmentId() != null) {
                query.setParameter("departmentId", request.getDepartmentId());
            }

            results = query.getResultList();
            log.info("Search found {} employees", results.size());

        } catch (Exception e) {
            log.error("Error searching employees", e);
        }

        return results;
    }

    /**
     * Tìm kiếm theo ID bằng Native SQL
     */
    public Optional<Employee> findById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM employees WHERE id = :id";

            Employee employee = session.createNativeQuery(sql, Employee.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(employee);

        } catch (Exception e) {
            log.error("Error finding employee by id: {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Lưu hoặc cập nhật Employee bằng Hibernate
     */
    public Employee save(Employee employee) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Xử lý quan hệ Department trước khi lưu
            if (employee.getDepartmentId() != null) {
                Department department = session.get(Department.class, employee.getDepartmentId());
                if (department != null) {
                    employee.setDepartment(department);
                }
            }

            // saveOrUpdate: nếu có ID thì update, không có thì insert
            if (employee.getId() == null) {
                session.persist(employee);
            } else {
                employee = session.merge(employee);
            }

            transaction.commit();
            log.info("Employee saved successfully: {}", employee);

            return employee;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error saving employee", e);
            throw new RuntimeException("Failed to save employee", e);
        }
    }

    /**
     * Cập nhật Employee
     */
    public Employee update(Long id, Employee updatedEmployee) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Employee existingEmployee = session.get(Employee.class, id);
            if (existingEmployee == null) {
                throw new RuntimeException("Employee not found with id: " + id);
            }

            // Cập nhật các trường
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setDob(updatedEmployee.getDob());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            existingEmployee.setPhone(updatedEmployee.getPhone());

            // Xử lý Department
            if (updatedEmployee.getDepartmentId() != null) {
                Department department = session.get(Department.class, updatedEmployee.getDepartmentId());
                existingEmployee.setDepartment(department);
            }

            session.merge(existingEmployee);
            transaction.commit();

            log.info("Employee updated successfully: {}", existingEmployee);
            return existingEmployee;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error updating employee", e);
            throw new RuntimeException("Failed to update employee", e);
        }
    }

    /**
     * Xóa Employee bằng HQL
     */
    public boolean deleteById(Long id) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "DELETE FROM Employee e WHERE e.id = :id";
            int result = session.createMutationQuery(hql)
                    .setParameter("id", id)
                    .executeUpdate();

            transaction.commit();

            if (result > 0) {
                log.info("Employee deleted successfully with id: {}", id);
                return true;
            }
            return false;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error deleting employee with id: {}", id, e);
            return false;
        }
    }

    /**
     * Đếm tổng số nhân viên
     */
    public long count() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(e) FROM Employee e";
            return session.createQuery(hql, Long.class).uniqueResult();
        } catch (Exception e) {
            log.error("Error counting employees", e);
            return 0;
        }
    }

    /**
     * Lấy tất cả nhân viên
     */
    public List<Employee> findAll() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).getResultList();
        } catch (Exception e) {
            log.error("Error finding all employees", e);
            return new ArrayList<>();
        }
    }

    /**
     * Kiểm tra nhân viên tồn tại
     */
    public boolean existsById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(e) FROM Employee e WHERE e.id = :id";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking employee existence", e);
            return false;
        }
    }
}