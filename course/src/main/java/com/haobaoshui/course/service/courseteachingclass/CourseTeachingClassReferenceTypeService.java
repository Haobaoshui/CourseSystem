package com.haobaoshui.course.service.courseteachingclass;

import javax.servlet.http.HttpServletRequest;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceType;
import com.haobaoshui.course.model.Page;


public interface CourseTeachingClassReferenceTypeService {



	 CourseTeachingClassReferenceType getByID(String id);

	 void deleteById(HttpServletRequest request,String t_teaching_type_id) ;

	 String add(String t_course_teaching_class_id, String name, String note);

	 Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) ;

	 Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id);

	 void update(String id, String name, String note);

}
