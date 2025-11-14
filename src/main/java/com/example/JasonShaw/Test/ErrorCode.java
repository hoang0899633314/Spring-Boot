package com.example.JasonShaw.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public enum ErrorCode {
    STUDENT_NOT_FOUND(40401, "Student is not exist!", HttpStatus.NOT_FOUND);
    int code;
    String message;
    HttpStatus status;

}
