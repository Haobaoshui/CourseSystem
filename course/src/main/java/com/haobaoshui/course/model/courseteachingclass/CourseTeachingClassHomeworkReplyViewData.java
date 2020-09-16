package com.haobaoshui.course.model.courseteachingclass;

import java.util.List;


public class CourseTeachingClassHomeworkReplyViewData {


    private CourseTeachingClassHomeworkReply reply;
    private CourseTeachingClassHomeworkSubmitBaseinfo homeworksubmitbaseinfo;
    private List<CourseTeachingClassHomeworkReplyFile> repplyFileList;

    public CourseTeachingClassHomeworkReply getReply() {
        return reply;
    }

    public void setReply(CourseTeachingClassHomeworkReply reply) {
        this.reply = reply;
    }

    public List<CourseTeachingClassHomeworkReplyFile> getRepplyFileList() {
        return repplyFileList;
    }

    public void setRepplyFileList(List<CourseTeachingClassHomeworkReplyFile> repplyFileList) {
        this.repplyFileList = repplyFileList;
    }

    public CourseTeachingClassHomeworkSubmitBaseinfo getHomeworksubmitbaseinfo() {
        return homeworksubmitbaseinfo;
    }

    public void setHomeworksubmitbaseinfo(CourseTeachingClassHomeworkSubmitBaseinfo homeworksubmitbaseinfo) {
        this.homeworksubmitbaseinfo = homeworksubmitbaseinfo;
    }


}
