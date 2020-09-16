package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReply;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassForumReplyRepository {

	String add(String t_course_teaching_class_forum_topic_id, String t_user_id, String title, String content, String created_date,
			   String last_modified_date);
	String add(String t_course_teaching_class_forum_topic_id, String t_user_id, String title, String content, Date created_date, Date last_modified_date);

	int deleteById(String id) ;

	int deleteByForumTopicId(String t_course_teaching_class_forum_topic_id) ;

	int deleteByt_user_id(String t_user_id) ;


	/**
	 * 更新
	 * */
	int update(String id, String t_user_id, String title, String content, Date last_modified_date) ;
	/*
	 * 根据用户ID得到用户
	 */
	CourseTeachingClassForumReply getByID(String id);
	/**
	 * 根据教学班得到论坛id
	 * */
	List<CourseTeachingClassForumReply> getByForumTopicID(String t_course_teaching_class_forum_topic_id);
	
	
	/**
	 * 根据t_user_id得到论坛id
	 * */
	List<CourseTeachingClassForumReply> getByUserID(String t_user_id) ;
	/**
	 * 根据t_user_id得到论坛id
	 * */
	List<CourseTeachingClassForumReply> getByCourseTeachingClassIdAndUserID(String t_course_teaching_class_id, String t_user_id);


	long getCount(String t_course_teaching_class_forum_topic_id);


	Page<CourseTeachingClassForumReply> getPage(String t_course_teaching_class_forum_topic_id, int pageNo, int pageSize);
}
