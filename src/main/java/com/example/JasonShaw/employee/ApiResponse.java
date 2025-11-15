package com.example.JasonShaw.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) // Loại bỏ field null khỏi JSON response
public class ApiResponse<T> {

    @Builder.Default
    Integer code = 1000; // Mã code mặc định cho success

    String message;

    T data; // Dữ liệu trả về (có thể là Employee, List<Employee>, hoặc null)
}