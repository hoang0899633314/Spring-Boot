package com.example.JasonShaw.test.service;

import com.example.JasonShaw.test.model.Student;

import java.util.List;

public interface IStudentService {

    List<Student> findAll();

    Student findById(Integer id);

    Student save(Student student);
}
