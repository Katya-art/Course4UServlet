package com.example.finalProjectServlet.model.entity;

import com.example.finalProjectServlet.model.dao.UserDao;

public class Course {

    private Long id;

    private String name;

    private String theme;

    private int duration;

    private int teacherId;

    private int conditionId;

    private int numberOfStudents;

    public Long getId() {
        return id;
    }

    public Course() {
    }

    public Course(Long id, String name, String theme, int duration, int teacherId, int conditionId, int numberOfStudents) {
        this.id = id;
        this.name = name;
        this.theme = theme;
        this.duration = duration;
        this.teacherId = teacherId;
        this.conditionId = conditionId;
        this.numberOfStudents = numberOfStudents;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public String getTeacherName() {
        return (new UserDao()).findById((long) teacherId).getFullName();
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
