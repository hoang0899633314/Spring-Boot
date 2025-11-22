package com.example.JasonShaw.test.controller;

import com.example.JasonShaw.test.ApiException;
import com.example.JasonShaw.test.ApiResponse;
import com.example.JasonShaw.test.ErrorCode;
import com.example.JasonShaw.test.model.Student;
import com.example.JasonShaw.test.repository.StudentRepository;
import com.example.JasonShaw.test.service.IStudentService;
import com.example.JasonShaw.test.service.StudentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {    IStudentService studentService;

    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(ApiResponse.builder().data(studentService.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getById(@PathVariable("id") Integer id) {
        Student student = studentService.findById(id);
        if (student == null) {
            throw new ApiException(ErrorCode.STUDENT_NOT_FOUND);

        }
        return ResponseEntity.ok(ApiResponse.<Student>builder()
                .data(student)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> save(@RequestBody Student student) {
        student = studentService.save(student);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Student>builder()
                        .data(student).build());
    }
}
