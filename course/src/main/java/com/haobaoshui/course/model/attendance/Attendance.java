package com.haobaoshui.course.model.attendance;

import java.util.Date;

public class Attendance {


    private String id;
    private String courseTeacingClassId;
    private String attendanceTypeId;
    private String teacherId;
    private Date beginDatetime;
    private Date endDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseTeacingClassId() {
        return courseTeacingClassId;
    }

    public void setCourseTeacingClassId(String courseTeacingClassId) {
        this.courseTeacingClassId = courseTeacingClassId;
    }

    public String getAttendanceTypeId() {
        return attendanceTypeId;
    }

    public void setAttendanceTypeId(String attendanceTypeId) {
        this.attendanceTypeId = attendanceTypeId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Date getBeginDatetime() {
        return beginDatetime;
    }

    public void setBeginDatetime(Date beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }


}
