package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopic;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassForumTopicRepository {

    String add(String t_course_teaching_class_id, String t_user_id, String title, String content, String created_date,
               String last_modified_date);

    String add(String t_course_teaching_class_id, String t_user_id, String title, String content, Date created_date,
               Date last_modified_date);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    int deleteByUserId(String t_user_id);
    int update(String id, String t_user_id, String title, String content, String last_modified_date);

    int update(String id, String t_user_id, String title, String content, Date last_modified_date);


    /**
     * 将浏览次数增1
     */
    int incViewCount(String id);

    /**
     * 得到浏览次数
     */
    long getViewCount(String id);

    /**
     * 得到标记
     */
    long getFlag(String id);

    /**
     * 设计标记
     */
    int setFlag(String id, int flag);





    long getCount(String t_group_id);

    /*
     * 根据用户ID得到用户
     */
    CourseTeachingClassForumTopic getByID(String id);

    
    /**
     * 根据教学班得到论坛id
     */
    List<CourseTeachingClassForumTopic> getByCourseTeachingClassID(String t_course_teaching_class_id);

    /**
     * 根据t_user_id得到论坛id
     */
    List<CourseTeachingClassForumTopic> getByUserID(String t_user_id);

    /**
     * 根据t_course_teaching_class_id和t_user_id得到论坛id
     */
    List<CourseTeachingClassForumTopic> getByCourseTeachingClassIDAndUserID(String t_course_teaching_class_id, String t_user_id);


    Page<CourseTeachingClassForumTopic> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);
}
