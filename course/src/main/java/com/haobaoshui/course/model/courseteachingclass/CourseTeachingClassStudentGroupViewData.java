package com.haobaoshui.course.model.courseteachingclass;


import com.haobaoshui.course.model.authority.Group;
import com.haobaoshui.course.model.user.StudentViewData;

/**
 * 教学班每个分组的详细信息，包括该小组信息、小组学生信息等
 */
public class CourseTeachingClassStudentGroupViewData {


    private CourseTeachingClassStudentGroup courseTeachingClassStudentGroup;
    private Group group;
    private StudentViewData[] studentViewDatas;

    public CourseTeachingClassStudentGroup getCourseTeachingClassStudentGroup() {
        return courseTeachingClassStudentGroup;
    }

    public void setCourseTeachingClassStudentGroup(CourseTeachingClassStudentGroup courseTeachingClassStudentGroup) {
        this.courseTeachingClassStudentGroup = courseTeachingClassStudentGroup;
    }

    public StudentViewData[] getStudentViewDatas() {
        return studentViewDatas;
    }

    public void setStudentViewDatas(StudentViewData[] studentViewDatas) {
        this.studentViewDatas = studentViewDatas;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
