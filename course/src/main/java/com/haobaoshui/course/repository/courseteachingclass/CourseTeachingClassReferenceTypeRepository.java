package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceType;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface CourseTeachingClassReferenceTypeRepository {

    String add(CourseTeachingClassReferenceType expriment);

    String add(String t_course_teaching_class_id, String name, String note);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    int update(String id, String name, String note);


    CourseTeachingClassReferenceType getByID(String id);


    List<CourseTeachingClassReferenceType> getByCourseTeachingClassID(String t_course_teaching_class_id);


    long getCount(String t_course_teaching_class_id);


    Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);

    Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id);
}
