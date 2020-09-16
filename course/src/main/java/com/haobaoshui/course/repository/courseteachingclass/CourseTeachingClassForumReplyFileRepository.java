package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReplyFile;

import java.util.List;


public interface CourseTeachingClassForumReplyFileRepository {


     int update(String id, String t_forum_reply_id, String filename, String filepath);

    /*
     * 根据用户ID得到用户
     */
     CourseTeachingClassForumReplyFile getByID(String id);

    /**
     * 根据教学班得到通知
     */
     List<CourseTeachingClassForumReplyFile> getByForumReplyID(String t_forum_reply_id);

    /* 增加用户 */
     String add(CourseTeachingClassForumReplyFile submitfile);

     String add(String t_forum_reply_id, String filename, String filepath);

     int deleteById(String id);

     int deleteByForumReplyId(String t_forum_reply_id);

     long getCount(String t_forum_reply_id);



}
