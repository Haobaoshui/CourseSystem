package com.haobaoshui.course.model.courseteachingclass;

import com.haobaoshui.course.model.user.StudentViewData;

import java.util.Date;
import java.util.List;

public class CourseTeachingClassHomeworkSubmitBaseinfoViewData {


    private CourseTeachingClassHomeworkSubmitBaseinfo homeworksubmitbaseinfo;
    private StudentViewData student;
    private CourseTeachingClassHomeworkBaseinfoStudentViewData homeworkbaseinfoViewData;
    private List<CourseTeachingClassHomeworkSubmitFile> homeworksubmitFileList;
    private boolean hasReply;// 是否已经回复

    private CourseTeachingClassStudentGroupViewData studentGroupViewData;

    public CourseTeachingClassHomeworkSubmitBaseinfo getHomeworksubmitbaseinfo() {
        return homeworksubmitbaseinfo;
    }

    public void setHomeworksubmitbaseinfo(CourseTeachingClassHomeworkSubmitBaseinfo homeworksubmitbaseinfo) {
        this.homeworksubmitbaseinfo = homeworksubmitbaseinfo;
    }

    public StudentViewData getStudent() {
        return student;
    }

    public void setStudent(StudentViewData student) {
        this.student = student;
    }


    public List<CourseTeachingClassHomeworkSubmitFile> getHomeworksubmitFileList() {
        return homeworksubmitFileList;
    }

    public void setHomeworksubmitFileList(List<CourseTeachingClassHomeworkSubmitFile> homeworksubmitFileList) {
        this.homeworksubmitFileList = homeworksubmitFileList;
    }

    public boolean isHasReply() {
        return hasReply;
    }

    public void setHasReply(boolean hasReply) {
        this.hasReply = hasReply;
    }

    // 如果作业提交，是否是迟交作业
    public boolean isDelayedSubmit() {

        Date dateNormal = homeworkbaseinfoViewData.getHomeworkbaseinfo().getEnddate();

        if (dateNormal.compareTo(homeworksubmitbaseinfo.getSubmitdate()) < 0) return true;

        return false;
    }

    public CourseTeachingClassHomeworkBaseinfoStudentViewData getHomeworkbaseinfoViewData() {
        return homeworkbaseinfoViewData;
    }

    public void setHomeworkbaseinfoViewData(CourseTeachingClassHomeworkBaseinfoStudentViewData homeworkbaseinfoViewData) {
        this.homeworkbaseinfoViewData = homeworkbaseinfoViewData;
    }

    public CourseTeachingClassStudentGroupViewData getStudentGroupViewData() {
        return studentGroupViewData;
    }

    public void setStudentGroupViewData(CourseTeachingClassStudentGroupViewData studentGroupViewData) {
        this.studentGroupViewData = studentGroupViewData;
    }

}
