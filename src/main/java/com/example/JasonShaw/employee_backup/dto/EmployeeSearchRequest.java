package com.example.JasonShaw.employee_backup.dto;

import com.example.JasonShaw.employee_backup.model.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Employee Search Request DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class EmployeeSearchRequest {
    String name;
    String dobFrom;      // yyyy-MM-dd
    String dobTo;        // yyyy-MM-dd
    Gender gender;
    String salaryRange;  // lt5, 5-10, 10-20, gt20
    String phone;
    Long departmentId;
}