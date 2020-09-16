package com.haobaoshui.course.model.courseteachingclass;

import com.haobaoshui.course.model.user.UserInfoViewData;

import java.util.List;


public class CourseTeachingClassForumTopicViewData {


    private CourseTeachingClassForumTopic forumtopic;
    private UserInfoViewData userInfoViewData;
    private List<CourseTeachingClassForumTopicFile> topicFileList;


    public CourseTeachingClassForumTopic getForumtopic() {
        return forumtopic;
    }

    public void setForumtopic(CourseTeachingClassForumTopic forumtopic) {
        this.forumtopic = forumtopic;
    }

    public UserInfoViewData getUserInfoViewData() {
        return userInfoViewData;
    }

    public void setUserInfoViewData(UserInfoViewData userInfoViewData) {
        this.userInfoViewData = userInfoViewData;
    }

    public List<CourseTeachingClassForumTopicFile> getTopicFileList() {
        return topicFileList;
    }

    public void setTopicFileList(List<CourseTeachingClassForumTopicFile> topicFileList) {
        this.topicFileList = topicFileList;
    }
}
