package com.haobaoshui.course.model.attendance;


import com.haobaoshui.course.model.user.StudentViewData;

public class AttendanceStudentViewData {


    private Attendance attendance;
    private StudentViewData studentviewdata;
    private AttendanceStudent attendancestudent;
    private AttendanceState state;

    private AttendanceMode mode;

    public StudentViewData getStudentviewdata() {
        return studentviewdata;
    }

    public void setStudentviewdata(StudentViewData studentviewdata) {
        this.studentviewdata = studentviewdata;
    }

    public AttendanceStudent getAttendancestudent() {
        return attendancestudent;
    }

    public void setAttendancestudent(AttendanceStudent attendancestudent) {
        this.attendancestudent = attendancestudent;
    }

    public AttendanceState getState() {
        return state;
    }

    public void setState(AttendanceState state) {
        this.state = state;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public AttendanceMode getMode() {
        return mode;
    }

    public void setMode(AttendanceMode mode) {
        this.mode = mode;
    }

}
