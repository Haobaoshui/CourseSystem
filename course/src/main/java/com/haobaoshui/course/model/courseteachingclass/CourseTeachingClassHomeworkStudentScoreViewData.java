package com.haobaoshui.course.model.courseteachingclass;

import com.haobaoshui.course.model.user.StudentViewData;

public class CourseTeachingClassHomeworkStudentScoreViewData {


    private CourseTeachingClassHomeworkStudentScore score;
    private CourseTeachingClassHomeworkScoreInfoViewData scoreInfo;
    private StudentViewData studentView;

    public CourseTeachingClassHomeworkStudentScore getScore() {
        return score;
    }

    public void setScore(CourseTeachingClassHomeworkStudentScore score) {
        this.score = score;
    }

    public StudentViewData getStudentView() {
        return studentView;
    }

    public void setStudentView(StudentViewData studentView) {
        this.studentView = studentView;
    }

    public CourseTeachingClassHomeworkScoreInfoViewData getScoreInfo() {
        return scoreInfo;
    }

    public void setScoreInfo(CourseTeachingClassHomeworkScoreInfoViewData scoreInfo) {
        this.scoreInfo = scoreInfo;
    }


}
