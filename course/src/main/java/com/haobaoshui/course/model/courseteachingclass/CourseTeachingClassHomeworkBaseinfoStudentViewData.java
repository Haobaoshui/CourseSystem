package com.haobaoshui.course.model.courseteachingclass;

public class CourseTeachingClassHomeworkBaseinfoStudentViewData extends CourseTeachingClassHomeworkBaseinfoViewData {


    private boolean studentSubmitted;//是否提交作业


    public CourseTeachingClassHomeworkBaseinfoStudentViewData(CourseTeachingClassHomeworkBaseinfoViewData data) {
        this.setCourseteachingclass(data.getCourseteachingclass());
        this.setHomeworkbaseinfo(data.getHomeworkbaseinfo());
        this.setHomeworkFileList(data.getHomeworkFileList());
        this.setHomeworkType(data.getHomeworkType());
        this.setTeacher(data.getTeacher());
        this.setHomeworkDelayedList(data.getHomeworkDelayedList());
    }

    public boolean isStudentSubmitted() {
        return studentSubmitted;
    }

    public void setStudentSubmitted(boolean studentSubmitted) {
        this.studentSubmitted = studentSubmitted;
    }


}
