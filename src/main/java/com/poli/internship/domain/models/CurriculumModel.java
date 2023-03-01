package com.poli.internship.domain.models;

import java.time.LocalDate;

public class CurriculumModel {
    private String id;
    private String name;
    private String lastName;
    private String degreeCourse;
    private LocalDate graduationYear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDegreeCourse() {
        return degreeCourse;
    }

    public void setDegreeCourse(String degreeCourse) {
        this.degreeCourse = degreeCourse;
    }

    public LocalDate getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(LocalDate graduationYear) {
        this.graduationYear = graduationYear;
    }
}
