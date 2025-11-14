package com.example.JasonShaw.employee;

public class Employee {
    private Long id;
    private String name;
    private String dob; // Định dạng: yyyy-MM-dd
    private Gender gender;
    private Double salary;
    private String phone;

    // Constructor mặc định
    public Employee() {
    }

    // Constructor đầy đủ
    public Employee(Long id, String name, String dob, Gender gender, Double salary, String phone) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.salary = salary;
        this.phone = phone;
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}