package com.haobaoshui.course.model.courseteachingclass;

import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.user.TeacherViewData;

public class CourseTeachingClassViewData {


    private CourseTeachingClass courseTeachingClass;


    //课程
    private Course course;

    //学期
    private CourseTeachingTerm term;

    //授课教师
    private TeacherViewData[] teacher;


    //教师授课类型
    private TeachingType[] teachingtype;


    public Course getCourse() {
        return course;
    }


    public void setCourse(Course course) {
        this.course = course;
    }


    public TeacherViewData[] getTeacher() {
        return teacher;
    }


    public void setTeacher(TeacherViewData[] teacher) {
        this.teacher = teacher;
    }


    public TeachingType[] getTeachingtype() {
        return teachingtype;
    }


    public void setTeachingtype(TeachingType[] teachingtype) {
        this.teachingtype = teachingtype;
    }


    public CourseTeachingTerm getTerm() {
        return term;
    }


    public void setTerm(CourseTeachingTerm term) {
        this.term = term;
    }


    public CourseTeachingClass getCourseTeachingClass() {
        return courseTeachingClass;
    }


    public void setCourseTeachingClass(CourseTeachingClass courseTeachingClass) {
        this.courseTeachingClass = courseTeachingClass;
    }
}
