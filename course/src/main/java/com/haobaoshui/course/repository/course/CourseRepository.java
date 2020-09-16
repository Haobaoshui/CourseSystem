package com.haobaoshui.course.repository.course;

import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface CourseRepository {



	/**
	 * 删除
	 * */
	 int deleteById(String id);
	
	/**
	 * 修改
	 * */
	 int update(String id,String name, String englishname, String num,
			int class_hours, int experiment_hours, String t_course_type_id,
			String t_course_style_id) ;
	
	/**
	 * 增加
	 * */
	 String add(String name, String englishname, String num,
			int class_hours, int experiment_hours, String t_course_type_id,
			String t_course_style_id);
	/**
	 * 根据课程id查找课程
	 * */
	 Course getCourseById(String id) ;
	/**
	 * 根据t_course_type_id查找课程
	 * */
	 List<Course> getByCourseTypeId(String t_course_type_id) ;
	/**
	 * 根据t_course_style_id查找课程
	 * */
	 List<Course> getByCourseStyleId(String t_course_style_id);
	
	/**
	 * 根据t_course_type_id,t_course_style_id查找课程
	 * */
	 List<Course> getByCourseTypeIdAndStyleId(String t_course_type_id, String t_course_style_id);


	/**
	 * 根据课程编号查找课程
	 */
	Course getCourseByCourseNum(String num);

	/**
	 * 得到所有课程数目
	 */
	long getCount();


	long getCountByCourseStyle(String t_course_style_id);


	long getCountByCourseNum(String num);

	long getCountByCourseType(String t_course_type_id);


	long getCountByCourseDepartmentId(String t_department_id);

	Page<Course> getPage(int pageNo, int pageSize);


}
