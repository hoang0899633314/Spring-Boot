package com.example.JasonShaw;

public class Student {
    private int id;
    private String name;
    private double scroe;

    public Student() {
    }

    public Student(int id, String name, double score) {
        this.id = id;
        this.name = name;
        this.scroe = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return scroe;
    }

    public void setScore(double scroe) {
        this.scroe = scroe;
    }
}
