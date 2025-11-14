package com.example.JasonShaw;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final List<Student> students = new ArrayList<>(
            Arrays.asList(
                    new Student(1, "Linh", 2.0),
                    new Student(2, "Loi", 3.0)
            )
    );

    //    @RequestMapping(value = "/students", method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(ApiResponse.builder().data(students).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getById(@PathVariable("id") Integer id) {
        for (Student student : students) {
            if (student.getId() == id) {
//                return ResponseEntity.status(HttpStatus.OK).body(student);
                return ResponseEntity.ok(ApiResponse.<Student>builder()
//                        .code(20001)
//                        .message("Get data successfully")
                        .data(student)
                        .build());
            }
        }
        throw new ApiException(ErrorCode.STUDENT_NOT_FOUND);

//        return ResponseEntity.status(ErrorCode.STUDENT_NOT_FOUND.getStatus()).body(
//                ApiResponse.<Student>builder()
//                        .code(ErrorCode.STUDENT_NOT_FOUND.getCode())
//                        .message(ErrorCode.STUDENT_NOT_FOUND.getMessage())
//                        .build()
//        );
    }

    //    @RequestMapping(value = "/students", method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> save(@RequestBody Student student) {
        student.setId((int) (Math.random() * 100000000) + 1);
        students.add(student);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Student>builder()
                        .data(student).build());
    }
}
