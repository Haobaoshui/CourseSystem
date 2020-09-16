package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReply;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassHomeworkReplyRepository {

	 void update(String id, String t_course_teaching_class_homework_submit_baseinfo_id, String t_teacher_id, String title,
			String content, Date submitdate, Date modifieddate) ;

	 void update(String id, String title, String content, Date modifieddate);

	/*
	 * 根据用户ID得到用户
	 */
	 CourseTeachingClassHomeworkReply getByID(String id) ;

	/**
	 * 根据
	 * */
	 List<CourseTeachingClassHomeworkReply> getByCourseTeachingClassHomeworkSubmitBaseInfoID(
			String t_course_teaching_class_homework_submit_baseinfo_id);
	
	/**
	 * 根据t_teacher_id得到回复
	 * */
	 List<CourseTeachingClassHomeworkReply> getByTeacherID(
			String t_teacher_id) ;

	/* 增加用户 */
	 String add(CourseTeachingClassHomeworkReply expriment) ;

	 String add(String t_course_teaching_class_homework_submit_baseinfo_id, String t_teacher_id, String title, String content,
			Date submitdate, Date modifieddate);

	void deleteById(String id);

	void deleteByCourseTeachingClassHomeworkSubmitId(String t_course_teaching_class_homework_submit_baseinfo_id);

	void deleteByTeacherId(String t_teacher_id);

	int getCount(String t_course_teaching_class_homework_submit_baseinfo_id, String t_student_id);


	Page<CourseTeachingClassHomeworkReply> getPage(String t_course_teaching_class_homework_submit_baseinfo_id,
												   String t_student_id, int pageNo, int pageSize);
}
