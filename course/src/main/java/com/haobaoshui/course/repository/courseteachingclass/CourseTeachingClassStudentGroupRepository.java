package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudentGroup;

import java.util.List;


public interface CourseTeachingClassStudentGroupRepository {

	/**
	 * 增加
	 */
	String add(String t_course_teaching_class_id, String t_group_id, int show_index);

	/**
	 * 根据id删除
	 */
	int deleteById(String id);
	/**
	 * 根据课程删除所有的学生
	 */
	int deleteByCourseTeachingClassId(String t_course_teaching_class_id) ;


	/**
	 * 根据组删除
	 */
	int deleteByGroupId(String t_group_id) ;

	/**
	 * 根据课程和组id删除
	 */
	int deleteByCourseTeachingClassIdAndGroupId(String t_course_teaching_class_id,String t_group_id);


	/**
	 * 修改
	 */
	int update(String id, String t_course_teaching_class_id, String t_group_id, int show_index) ;

	/**
	 * 修改show_index
	 */
	int update(String id, int show_index) ;

	/**
	 * 修改show_index
	 */
	int update(String t_course_teaching_class_id, String t_group_id, int show_index) ;



	
	/**
	 * 学生是否在指定教学班级中存在
	 */
	 boolean IsGroupExist(String t_course_teaching_class_id, String t_student_id) ;
	
	/**
	 * 得到教学班人数
	 */
	 int getGroupCountByCourseTeachingClassId(String t_course_teaching_class_id) ;

	/**
	 * */
	 int getShowIndexMaxByCourseTeachingClassId(String t_course_teaching_class_id) ;

	/**
	 * 得到比指定的学生小一个次序
	 */
	 int getShowIndexMaxLessthanByCourseTeachingClassId(String t_course_teaching_class_id, String t_group_id) ;
	
	/**
	 * 得到比指定的学生大一个次序
	 */
	 int getShowIndexMinMorethanByCourseTeachingClassId(String t_course_teaching_class_id, String t_group_id) ;
	
	

	/**
	 * */
	 int getShowIndexMinByCourseTeachingClassId(String t_course_teaching_class_id) ;

	 CourseTeachingClassStudentGroup getById(
			String id);
	
	
	

	 CourseTeachingClassStudentGroup getByTeachingClassIdAndGroupId(
			String t_course_teaching_class_id, String t_group_id) ;
	
	 CourseTeachingClassStudentGroup getByTeachingClassIdAndUserId(
			String t_course_teaching_class_id, String t_user_id) ;
	
	 CourseTeachingClassStudentGroup getByTeachingClassIdAndShowIndex(
			String t_course_teaching_class_id, int show_index) ;
	
	 List<CourseTeachingClassStudentGroup> getByCourseTeachingClassId(
			String t_course_teaching_class_id) ;
	

}
