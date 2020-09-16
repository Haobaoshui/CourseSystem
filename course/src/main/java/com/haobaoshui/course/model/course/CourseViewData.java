package com.haobaoshui.course.model.course;

import com.haobaoshui.course.model.course.*;

import java.util.List;

public class CourseViewData {
	

	
	private Course course;
	private CourseStyle courseStyle;
	private CourseType courseType;
	private List<CourseDepartmentViewData> listCourseDepartment;
	private List<CoursePrecourseViewData> listCoursePrecourse;
	

	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public CourseStyle getCourseStyle() {
		return courseStyle;
	}
	public void setCourseStyle(CourseStyle courseStyle) {
		this.courseStyle = courseStyle;
	}
	public CourseType getCourseType() {
		return courseType;
	}
	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}
	public List<CourseDepartmentViewData> getListCourseDepartment() {
		return listCourseDepartment;
	}
	public void setListCourseDepartment(List<CourseDepartmentViewData> listCourseDepartment) {
		this.listCourseDepartment = listCourseDepartment;
	}
	public List<CoursePrecourseViewData> getListCoursePrecourse() {
		return listCoursePrecourse;
	}
	public void setListCoursePrecourse(List<CoursePrecourseViewData> listCoursePrecourse) {
		this.listCoursePrecourse = listCoursePrecourse;
	}
	

}
