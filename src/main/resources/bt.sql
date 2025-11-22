-- ==================== BƯỚC 1: TẠO DATABASE ====================
CREATE DATABASE IF NOT EXISTS employee_management
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE employee_management;

-- ==================== BƯỚC 2: TẠO BẢNG DEPARTMENT ====================
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;

CREATE TABLE department (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==================== BƯỚC 3: TẠO BẢNG EMPLOYEE ====================
CREATE TABLE employee (
                          id VARCHAR(36) PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          dob DATE NOT NULL,
                          gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
                          salary DECIMAL(15, 2) NOT NULL,
                          phone VARCHAR(15) NOT NULL,
                          department_id BIGINT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                          FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL,
                          INDEX idx_name (name),
                          INDEX idx_dob (dob),
                          INDEX idx_gender (gender),
                          INDEX idx_department (department_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==================== BƯỚC 4: TẠO TRIGGER TỰ ĐỘNG GENERATE UUID ====================
DELIMITER //

CREATE TRIGGER before_employee_insert
    BEFORE INSERT ON employee
    FOR EACH ROW
BEGIN
    IF NEW.id IS NULL OR NEW.id = '' THEN
        SET NEW.id = UUID();
END IF;
END//

DELIMITER ;

-- ==================== BƯỚC 5: INSERT DỮ LIỆU MẪU ====================

-- Insert Departments
INSERT INTO department (name) VALUES
                                  ('IT Department'),
                                  ('HR Department'),
                                  ('Sales Department'),
                                  ('Marketing Department'),
                                  ('Finance Department');

-- Insert Employees
INSERT INTO employee (id, name, dob, gender, salary, phone, department_id) VALUES
                                                                               (UUID(), 'Nguyen Van An', '1990-05-15', 'MALE', 8000.00, '0901234567', 1),
                                                                               (UUID(), 'Tran Thi Binh', '1992-08-20', 'FEMALE', 12000.00, '0912345678', 2),
                                                                               (UUID(), 'Le Van Cuong', '1988-03-10', 'MALE', 15000.00, '0923456789', 1),
                                                                               (UUID(), 'Pham Thi Dung', '1995-11-25', 'FEMALE', 4500.00, '0934567890', 3),
                                                                               (UUID(), 'Hoang Van Anh', '1993-07-08', 'MALE', 25000.00, '0945678901', 4),
                                                                               (UUID(), 'Vu Thi Mai', '1994-06-12', 'FEMALE', 9500.00, '0956789012', 2),
                                                                               (UUID(), 'Do Van Binh', '1991-09-30', 'MALE', 11000.00, '0967890123', 1),
                                                                               (UUID(), 'Nguyen Thi Cam', '1996-12-05', 'FEMALE', 7500.00, '0978901234', 3),
                                                                               (UUID(), 'Tran Van Dung', '1989-04-18', 'MALE', 18000.00, '0989012345', 4),
                                                                               (UUID(), 'Le Thi Hoa', '1997-01-22', 'FEMALE', 6000.00, '0990123456', 5);

-- ==================== BƯỚC 6: VERIFY DATA ====================
SELECT 'Departments:' AS Info;
SELECT * FROM department;

SELECT 'Employees:' AS Info;
SELECT * FROM employee;

-- ==================== BƯỚC 7: KIỂM TRA TRIGGER UUID ====================
-- Test insert without ID
INSERT INTO employee (name, dob, gender, salary, phone, department_id)
VALUES ('Test Employee', '1995-01-01', 'OTHER', 10000.00, '0901111111', 1);

SELECT * FROM employee WHERE name = 'Test Employee';

-- Cleanup test data
DELETE FROM employee WHERE name = 'Test Employee';

-- ==================== THỐNG KÊ ====================
SELECT
    (SELECT COUNT(*) FROM department) AS total_departments,
    (SELECT COUNT(*) FROM employee) AS total_employees;