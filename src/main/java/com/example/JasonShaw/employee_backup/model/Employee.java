package com.example.JasonShaw.employee_backup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Employee Entity
 */
@Entity
@Table(name = "employee")
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
    String dob; // Format: yyyy-MM-dd

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "salary")
    Double salary;

    @Column(name = "phone", length = 20)
    String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties({"employees"})
    Department department;

    // Transient field to receive departmentId from request
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