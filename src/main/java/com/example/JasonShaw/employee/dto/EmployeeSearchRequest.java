package com.example.JasonShaw.employee.dto;

import com.example.JasonShaw.employee.model.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeSearchRequest {
    String name;
    String dobFrom;      // yyyy-MM-dd
    String dobTo;        // yyyy-MM-dd
    Gender gender;
    String salaryRange;  // lt5, 5-10, 10-20, gt20
    String phone;
    Long departmentId;
}