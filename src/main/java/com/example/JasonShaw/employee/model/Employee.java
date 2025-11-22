package com.example.JasonShaw.employee.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Column(name = "dob")
    String dob; // Định dạng: yyyy-MM-dd

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "salary")
    Double salary;

    @Column(name = "phone", length = 20)
    String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    Department department;

    // Transient field để nhận departmentId từ request
    @Transient
    Long departmentId;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", gender=" + gender +
                ", salary=" + salary +
                ", phone='" + phone + '\'' +
                ", departmentId=" + (department != null ? department.getId() : null) +
                '}';
    }
}