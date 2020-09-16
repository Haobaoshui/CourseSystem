package com.haobaoshui.course.model;

import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.model.user.TeacherViewData;
import com.haobaoshui.course.model.user.User;

public class UserGroupViewData {

    private StudentViewData studentViewData;
    private TeacherViewData teacherViewData;


    public User getUser() {
        if (teacherViewData != null) return teacherViewData.getUser();
        return studentViewData.getUser();
    }


    public StudentViewData getStudentViewData() {
        return studentViewData;
    }

    public void setStudentViewData(StudentViewData studentViewData) {
        this.studentViewData = studentViewData;
    }

    public TeacherViewData getTeacherViewData() {
        return teacherViewData;
    }

    public void setTeacherViewData(TeacherViewData teacherViewData) {
        this.teacherViewData = teacherViewData;
    }
}
