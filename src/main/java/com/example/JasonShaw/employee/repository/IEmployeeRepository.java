package com.example.JasonShaw.employee.repository;

import com.example.JasonShaw.employee.dto.EmployeeSearchRequest;
import com.example.JasonShaw.employee.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Employee Repository Interface
 * Định nghĩa các phương thức truy cập dữ liệu nhân viên
 */
public interface IEmployeeRepository {

    /**
     * Tìm kiếm nhân viên với nhiều điều kiện
     * @param request DTO chứa các tiêu chí tìm kiếm
     * @return Danh sách nhân viên phù hợp
     */
    List<Employee> search(EmployeeSearchRequest request);

    /**
     * Lấy tất cả nhân viên
     * @return Danh sách tất cả nhân viên
     */
    List<Employee> findAll();

    /**
     * Tìm nhân viên theo ID
     * @param id ID nhân viên
     * @return Optional chứa nhân viên nếu tìm thấy
     */
    Optional<Employee> findById(Long id);

    /**
     * Lưu nhân viên (tạo mới hoặc cập nhật)
     * @param employee Nhân viên cần lưu
     * @return Nhân viên đã được lưu
     */
    Employee save(Employee employee);

    /**
     * Xóa nhân viên theo ID
     * @param id ID nhân viên cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy
     */
    boolean deleteById(Long id);

    /**
     * Kiểm tra nhân viên có tồn tại không
     * @param id ID nhân viên
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsById(Long id);

    /**
     * Đếm tổng số nhân viên
     * @return Số lượng nhân viên
     */
    long count();
}