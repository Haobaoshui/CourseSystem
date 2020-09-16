package com.haobaoshui.course.model.attendance;

public class AttendanceSpecificStatistics {


    private Integer nCount;
    private AttendanceState state;

    public Integer getnCount() {
        return nCount;
    }

    public void setnCount(Integer nCount) {
        this.nCount = nCount;
    }

    public AttendanceState getState() {
        return state;
    }

    public void setState(AttendanceState state) {
        this.state = state;
    }

}
