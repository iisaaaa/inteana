package model;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseCode;
    private String name;
    private String description;
    private int credits;
    private String professorId;

    public Course(String courseCode, String name, String description, int credits, String professorId) {
        this.courseCode = courseCode;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "Course[" + "code='" + courseCode + "- name='" + name + "- credits=" + credits + ']';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    } 
}
