package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopicFile;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopicViewData;
import com.haobaoshui.course.model.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public interface CourseTeachingClassForumTopicService {


    void deleteById(HttpServletRequest request, String t_forum_topic_id);

    /**
     * 根据t_user_id删除
     */
    void deleteByUserId(HttpServletRequest request, String t_user_id);

    /**
     * 根据t_course_teaching_class_id、t_user_id删除
     */
    void deleteByCourseTeachingClassIDAndUserID(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id);

    /**
     * 根据t_course_teaching_class_id删除
     */
    void deleteByCourseTeachingClassID(HttpServletRequest request, String t_course_teaching_class_id);

    String add(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id, String title, String content,
                      String created_date, String last_modified_date, MultipartFile[] files);

    String add(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id, String title, String content,
                      Date created_date, Date last_modified_date, MultipartFile[] files);


    CourseTeachingClassForumTopicFile getFileByID(String id);

    Page<CourseTeachingClassForumTopicViewData> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);

    CourseTeachingClassForumTopicViewData getForumTopicViewDataById(String id);

    void update(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                       String last_modified_date, MultipartFile[] files);

    void update(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                       Date last_modified_date, MultipartFile[] files);

}
