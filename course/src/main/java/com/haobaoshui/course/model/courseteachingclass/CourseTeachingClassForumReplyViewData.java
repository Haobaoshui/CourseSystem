package com.haobaoshui.course.model.courseteachingclass;

import com.haobaoshui.course.model.user.UserInfoViewData;

import java.util.List;


public class CourseTeachingClassForumReplyViewData {


    private CourseTeachingClassForumReply forumreply;
    private UserInfoViewData userInfoViewData;
    private List<CourseTeachingClassForumReplyFile> replyFileList;

    public CourseTeachingClassForumReply getForumreply() {
        return forumreply;
    }

    public void setForumreply(CourseTeachingClassForumReply forumreply) {
        this.forumreply = forumreply;
    }

    public UserInfoViewData getUserInfoViewData() {
        return userInfoViewData;
    }

    public void setUserInfoViewData(UserInfoViewData userInfoViewData) {
        this.userInfoViewData = userInfoViewData;
    }

    public List<CourseTeachingClassForumReplyFile> getReplyFileList() {
        return replyFileList;
    }

    public void setReplyFileList(List<CourseTeachingClassForumReplyFile> replyFileList) {
        this.replyFileList = replyFileList;
    }
}
