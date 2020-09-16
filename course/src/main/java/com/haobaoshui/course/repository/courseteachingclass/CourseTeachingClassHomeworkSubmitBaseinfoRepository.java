package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfo;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassHomeworkSubmitBaseinfoRepository {


	/* 增加 */
	String add(CourseTeachingClassHomeworkSubmitBaseinfo submitinfo) ;

	String add(String t_course_teaching_class_homework_baseinfo_id, String t_student_id, String title,
			   String content, Date submitdate, Date modifieddate);

	int deleteById(String id) ;

	int deleteByCourseTeachingClassHomeworkSumbitBaseInfoId(
			String t_course_teaching_class_homework_baseinfo_id);
	int deleteByStudentId(String t_student_id) ;

	 int update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_student_id,
			String title, String content, Date submitdate, Date modifieddate);

	int update(String id, String title, String content, Date modifieddate) ;

	/*
	 * 根据用户ID得到用户
	 */
	 CourseTeachingClassHomeworkSubmitBaseinfo getByID(String id) ;

	/**
	 * 根据
	 */
	 List<CourseTeachingClassHomeworkSubmitBaseinfo> getByCourseTeachingClassHomeworkBaseinfoID(
			String t_course_teaching_class_homework_baseinfo_id);

	/**
	 * 根据
	 */
	 List<CourseTeachingClassHomeworkSubmitBaseinfo> getByStudentID(String t_student_id) ;
	/**
	 * 根据
	 */
	 List<CourseTeachingClassHomeworkSubmitBaseinfo> getByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(
			String t_course_teaching_class_homework_baseinfo_id, String t_student_id) ;



	 int getCount(String t_course_teaching_class_homework_baseinfo_id) ;

	 int getRealCount(String t_course_teaching_class_homework_baseinfo_id);

	 long getCount(String t_course_teaching_class_homework_baseinfo_id, String t_student_id);

	 List<String> PageQueryIds(String t_course_teaching_class_homework_baseinfo_id, int PageBegin, int PageSize) ;

	 List<String> PageQueryIds(String t_course_teaching_class_homework_baseinfo_id, String t_student_id,
			int PageBegin, int PageSize) ;

	 List<String> getIdsByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(
			String t_course_teaching_class_homework_baseinfo_id, String t_student_id) ;

	 List<String> getIdsByStudentID(String t_student_id) ;
}
