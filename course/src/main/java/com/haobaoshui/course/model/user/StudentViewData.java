package com.haobaoshui.course.model.user;

import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.NaturalClass;
import com.haobaoshui.course.model.organization.School;

public class StudentViewData {


    private Student student;
    private School school;
    private Department department;
    private NaturalClass naturalclass;
    private UserInfoViewData userInfoViewData;


    public User getUser() {
        if (userInfoViewData != null) return userInfoViewData.getUser();
        return null;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public NaturalClass getNaturalclass() {
        return naturalclass;
    }

    public void setNaturalclass(NaturalClass naturalclass) {
        this.naturalclass = naturalclass;
    }


    public UserInfoViewData getUserInfoViewData() {
        return userInfoViewData;
    }

    public void setUserInfoViewData(UserInfoViewData userInfoViewData) {
        this.userInfoViewData = userInfoViewData;
    }
}
