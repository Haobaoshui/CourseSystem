package com.haobaoshui.course.model.attendance;


public class AttendanceStateModeViewData {


    private Attendance attendance;
    private AttendanceStudent attendancestudent;
    private AttendanceState state;
    private AttendanceMode mode;

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
