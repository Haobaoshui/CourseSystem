package com.haobaoshui.course.model.courseteachingclass;

public class CourseTeachingClassTeacher {


    private String id;
    private String courseTeachingClassId;
    private String teacherId;
    private String teachingTypeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseTeachingClassId() {
        return courseTeachingClassId;
    }

    public void setCourseTeachingClassId(String courseTeachingClassId) {
        this.courseTeachingClassId = courseTeachingClassId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeachingTypeId() {
        return teachingTypeId;
    }

    public void setTeachingTypeId(String teachingTypeId) {
        this.teachingTypeId = teachingTypeId;
    }


}
