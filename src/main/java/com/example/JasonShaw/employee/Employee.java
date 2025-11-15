package com.example.JasonShaw.employee;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    Long id;
    String name;
    String dob; // Định dạng: yyyy-MM-dd
    Gender gender;
    Double salary;
    String phone;
}