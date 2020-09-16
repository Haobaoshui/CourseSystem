package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReplyFile;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReplyViewData;
import com.haobaoshui.course.model.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public interface CourseTeachingClassForumReplyService {


     void deleteByForumTopicId(HttpServletRequest request, String t_forum_topic_id);

     void deleteById(HttpServletRequest request, String t_forum_reply_id);

    /**
     * 根据t_user_id删除
     */
     void deleteByUserId(HttpServletRequest request, String t_user_id);

    /**
     * 根据t_user_id和t_course_teaching_class_id删除
     */
     void deleteByCourseTeachingClassIDAndUserID(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id);

     String add(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                      String created_date, MultipartFile[] files);

     String add(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                      Date created_date, MultipartFile[] files);

     CourseTeachingClassForumReplyFile getFileByID(String id);

     Page<CourseTeachingClassForumReplyViewData> getPage(String t_forum_topic_id, int pageNo, int pageSize);

    /**
     * 增加访问次数
     */
     void IncViewCount(String t_forum_topic_id);

     void update(HttpServletRequest request, String t_forum_reply_id, String t_user_id, String title, String content,
                       Date last_modified_date, MultipartFile[] files);

     CourseTeachingClassForumReplyViewData getForumReplyViewDataByID(String id);

}
