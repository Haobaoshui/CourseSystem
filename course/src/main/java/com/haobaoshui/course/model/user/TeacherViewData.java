package com.haobaoshui.course.model.user;

import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.School;

public class TeacherViewData {

    private Teacher teacher;
    private School school;
    private Department department;
    private UserInfoViewData userInfoViewData;

    public User getUser() {
        if (userInfoViewData != null) return userInfoViewData.getUser();
        return null;
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


    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    public UserInfoViewData getUserInfoViewData() {
        return userInfoViewData;
    }

    public void setUserInfoViewData(UserInfoViewData userInfoViewData) {
        this.userInfoViewData = userInfoViewData;
    }
}
