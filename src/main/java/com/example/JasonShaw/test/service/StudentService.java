package com.example.JasonShaw.test.service;

import com.example.JasonShaw.test.model.Student;
import com.example.JasonShaw.test.repository.IStudentRepository;
import com.example.JasonShaw.test.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor // Tiêm thông qua Consturctor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentService implements IStudentService {
    // Cách 1: tiêm vào thuộc tính
//    @Autowired
//    private IStudentRepository studentRepository;


    // Cách 2: TIêm vào constructor
    IStudentRepository studentRepository;


    // Cách 3: Tiêm vào setter
//    private IStudentRepository studentRepository;
//
//    @Autowired
//    public void setStudentRepository(IStudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findALl();
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findByID(id);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
