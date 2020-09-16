package com.haobaoshui.course.model.course;

import com.haobaoshui.course.model.organization.Department;

public class CourseDepartmentViewData {


    private CourseDepartment courseDepartment;
    private Course course;
    private Department department;


    public CourseDepartment getCourseDepartment() {
        return courseDepartment;
    }

    public void setCourseDepartment(CourseDepartment courseDepartment) {
        this.courseDepartment = courseDepartment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
