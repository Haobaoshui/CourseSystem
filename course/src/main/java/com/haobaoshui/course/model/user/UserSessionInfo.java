package com.haobaoshui.course.model.user;

import com.haobaoshui.course.model.authority.PermissionViewData;

import java.util.List;

public class UserSessionInfo {


    private int pageSize;
    private TeacherViewData teacherViewData;
    private StudentViewData studentViewData;

    private List<PermissionViewData> permissionviewdata;

    public User getUser() {

        if (teacherViewData != null) return teacherViewData.getUser();
        return studentViewData.getUser();
    }

    public String getUsername() {
        UserInfoViewData userInfoViewData = null;
        if (teacherViewData != null) userInfoViewData = teacherViewData.getUserInfoViewData();
        else if (studentViewData != null) userInfoViewData = studentViewData.getUserInfoViewData();
        if (userInfoViewData != null) return userInfoViewData.getUserbasicinfo().getUserBasicInfoName();
        return null;
    }


    public List<PermissionViewData> getPermissionviewdata() {
        return permissionviewdata;
    }

    public void setPermissionviewdata(List<PermissionViewData> permissionviewdata) {
        this.permissionviewdata = permissionviewdata;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public TeacherViewData getTeacherViewData() {
        return teacherViewData;
    }

    public void setTeacherViewData(TeacherViewData teacherViewData) {
        this.teacherViewData = teacherViewData;
    }

    public StudentViewData getStudentViewData() {
        return studentViewData;
    }

    public void setStudentViewData(StudentViewData studentViewData) {
        this.studentViewData = studentViewData;
    }
}
