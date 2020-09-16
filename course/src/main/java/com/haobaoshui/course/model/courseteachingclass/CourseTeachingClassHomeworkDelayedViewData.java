package com.haobaoshui.course.model.courseteachingclass;


import com.haobaoshui.course.model.user.TeacherViewData;

public class CourseTeachingClassHomeworkDelayedViewData {


    private CourseTeachingClassHomeworkDelayed homeworkdelayed;
    private TeacherViewData teacher;
    private CourseTeachingClassHomeworkBaseinfo homeworkbaseinfo;

    public CourseTeachingClassHomeworkDelayed getHomeworkdelayed() {
        return homeworkdelayed;
    }

    public void setHomeworkdelayed(CourseTeachingClassHomeworkDelayed homeworkdelayed) {
        this.homeworkdelayed = homeworkdelayed;
    }

    public TeacherViewData getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherViewData teacher) {
        this.teacher = teacher;
    }

    public CourseTeachingClassHomeworkBaseinfo getHomeworkbaseinfo() {
        return homeworkbaseinfo;
    }

    public void setHomeworkbaseinfo(CourseTeachingClassHomeworkBaseinfo homeworkbaseinfo) {
        this.homeworkbaseinfo = homeworkbaseinfo;
    }

}
