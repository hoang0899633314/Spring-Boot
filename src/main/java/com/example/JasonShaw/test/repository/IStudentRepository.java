package com.example.JasonShaw.test.repository;

import com.example.JasonShaw.test.model.Student;

import java.util.List;

public interface IStudentRepository {
    List<Student> findALl();

    Student findByID(int id);

    Student save(Student student);
}

