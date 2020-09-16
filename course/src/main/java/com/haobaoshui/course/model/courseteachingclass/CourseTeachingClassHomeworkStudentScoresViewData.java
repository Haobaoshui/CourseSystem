package com.haobaoshui.course.model.courseteachingclass;

import com.haobaoshui.course.model.user.StudentViewData;

import java.util.List;

public class CourseTeachingClassHomeworkStudentScoresViewData {


    private List<CourseTeachingClassHomeworkStudentScoreSimpleView> lstScore;

    private StudentViewData studentView;


    public StudentViewData getStudentView() {
        return studentView;
    }

    public void setStudentView(StudentViewData studentView) {
        this.studentView = studentView;
    }

    public List<CourseTeachingClassHomeworkStudentScoreSimpleView> getLstScore() {
        return lstScore;
    }

    public void setLstScore(List<CourseTeachingClassHomeworkStudentScoreSimpleView> lstScore) {
        this.lstScore = lstScore;
    }

}
