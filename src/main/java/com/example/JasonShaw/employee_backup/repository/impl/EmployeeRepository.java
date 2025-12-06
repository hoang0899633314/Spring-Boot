package com.example.JasonShaw.employee_backup.repository.impl;

import com.example.JasonShaw.util.ConnectionUtil;
import com.example.JasonShaw.employee_backup.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee_backup.model.Department;
import com.example.JasonShaw.employee_backup.model.Employee;
import com.example.JasonShaw.employee_backup.repository.IEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Employee Repository Implementation - Hibernate Version
 * All methods use Native SQL or session methods (NO HQL) to avoid errors
 */
@Repository
@Slf4j
public class EmployeeRepository implements IEmployeeRepository {

    @Override
    public List<Employee> search(EmployeeSearchRequest request) {
        List<Employee> results = new ArrayList<>();

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM employee WHERE 1=1");

            if (request.getName() != null && !request.getName().isEmpty()) {
                sql.append(" AND LOWER(name) LIKE LOWER(:name)");
            }
            if (request.getDobFrom() != null && !request.getDobFrom().isEmpty()) {
                sql.append(" AND dob >= :dobFrom");
            }
            if (request.getDobTo() != null && !request.getDobTo().isEmpty()) {
                sql.append(" AND dob <= :dobTo");
            }
            if (request.getGender() != null) {
                sql.append(" AND gender = :gender");
            }
            if (request.getPhone() != null && !request.getPhone().isEmpty()) {
                sql.append(" AND phone LIKE :phone");
            }
            if (request.getDepartmentId() != null) {
                sql.append(" AND department_id = :departmentId");
            }

            if (request.getSalaryRange() != null && !request.getSalaryRange().isEmpty()) {
                switch (request.getSalaryRange()) {
                    case "lt5":
                        sql.append(" AND salary < 5000000");
                        break;
                    case "5-10":
                        sql.append(" AND salary >= 5000000 AND salary < 10000000");
                        break;
                    case "10-20":
                        sql.append(" AND salary >= 10000000 AND salary < 20000000");
                        break;
                    case "gt20":
                        sql.append(" AND salary >= 20000000");
                        break;
                }
            }

            Query<Employee> query = session.createNativeQuery(sql.toString(), Employee.class);

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
                query.setParameter("gender", request.getGender().toString());
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

    @Override
    public Optional<Employee> findById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM employee WHERE id = :id";
            Employee employee = (Employee) session.createNativeQuery(sql, Employee.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            log.error("Error finding employee by id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Employee save(Employee employee) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (employee.getDepartmentId() != null) {
                Department department = session.get(Department.class, employee.getDepartmentId());
                if (department != null) {
                    employee.setDepartment(department);
                } else {
                    log.warn("Department with id {} not found", employee.getDepartmentId());
                }
            }

            if (employee.getId() == null) {
                session.persist(employee);
                log.info("Employee created: {}", employee.getName());
            } else {
                employee = (Employee) session.merge(employee);
                log.info("Employee updated: {}", employee.getName());
            }

            transaction.commit();
            return employee;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error saving employee", e);
            throw new RuntimeException("Failed to save employee", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;

        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);

            if (employee != null) {
                session.delete(employee);
                transaction.commit();
                log.info("Employee deleted with id: {}", id);
                return true;
            } else {
                log.warn("Employee not found with id: {}", id);
                if (transaction != null) transaction.rollback();
                return false;
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting employee with id: {}", id, e);
            return false;
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM employee ORDER BY id";
            return session.createNativeQuery(sql, Employee.class).getResultList();
        } catch (Exception e) {
            log.error("Error finding all employees", e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);
            return employee != null;
        } catch (Exception e) {
            log.error("Error checking employee existence", e);
            return false;
        }
    }

    @Override
    public long count() {
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            String sql = "SELECT COUNT(*) FROM employee";
            Number result = (Number) session.createNativeQuery(sql).uniqueResult();
            return result != null ? result.longValue() : 0;
        } catch (Exception e) {
            log.error("Error counting employees", e);
            return 0;
        }
    }
}