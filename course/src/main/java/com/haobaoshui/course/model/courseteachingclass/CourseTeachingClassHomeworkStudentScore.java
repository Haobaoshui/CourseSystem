package com.haobaoshui.course.model.courseteachingclass;

import java.util.Date;

public class CourseTeachingClassHomeworkStudentScore {


    private String id;
    private String courseTeachingClassHomeworkScoreInfoId;
    private String studentId;
    private String teacherId;
    private String score;
    private Date recordDate;
    private String description;
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getCourseTeachingClassHomeworkScoreInfoId() {
        return courseTeachingClassHomeworkScoreInfoId;
    }

    public void setCourseTeachingClassHomeworkScoreInfoId(String courseTeachingClassHomeworkScoreInfoId) {
        this.courseTeachingClassHomeworkScoreInfoId = courseTeachingClassHomeworkScoreInfoId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

}
