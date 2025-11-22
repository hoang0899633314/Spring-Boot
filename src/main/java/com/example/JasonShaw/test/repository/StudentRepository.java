package com.example.JasonShaw.test.repository;

import com.example.JasonShaw.test.ApiResponse;
import com.example.JasonShaw.test.model.Student;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.util.ClassUtil.name;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentRepository implements IStudentRepository {
    List<Student> students = new ArrayList<>(
            Arrays.asList(
                    new Student(1, "Linh", 2.0),
                    new Student(2, "Loi", 3.0)
            )
    );

    public List<Student> findALl() {
        List<Student> students = new ArrayList<>();
        try (PreparedStatement preparedStatement = BaseRepository
                .getConnection()
                .prepareStatement("select id, name, score from student");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Student student = Student.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .score(resultSet.getDouble("score"))
                        .build();
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return students;
    }

    public Student findByID(int id) {
        try (PreparedStatement preparedStatement = BaseRepository
                .getConnection()
                .prepareStatement("select id, name, score from student where id = ?;");
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Student.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .score(resultSet.getDouble("score"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    public Student save(Student student) {
        try (PreparedStatement preparedStatement = BaseRepository
                .getConnection()
                .prepareStatement("insert into student(name, score) VALUES (?, ?);");
        ) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setDouble(2, student.getScore());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return student;

    }
}


