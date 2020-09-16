package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReference;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassReferenceRepository {


    String add(CourseTeachingClassReference reference);

    String add(String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_homework_type_id, String title,
               String content, Date pubdate, Date enddate);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    int deleteByTeacherId(String t_teacher_id);


    int update(String id, String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_reference_type_id,
               String title, String content, Date pubdate, Date modifieddate);

    int update(String id, String t_teacher_id, String title, String content, Date modifieddate);


    CourseTeachingClassReference getByID(String id);


    List<CourseTeachingClassReference> getByCourseTeachingClassID(String t_course_teaching_class_id);


    List<CourseTeachingClassReference> getByTeacherID(String t_teacher_id);


    List<CourseTeachingClassReference> getByReferenceTypeId(String t_course_teaching_class_reference_type_id);


    List<CourseTeachingClassReference> getByCourseTeachingClassIDAndTeacherId(String t_course_teaching_class_id,
                                                                              String t_teacher_id);


    List<CourseTeachingClassReference> getByCourseTeachingClassIDAndTeacherIdAndReferenceTypeId(String t_course_teaching_class_id,
                                                                                                String t_teacher_id, String t_course_teaching_class_reference_type_id);


    long getCount(String t_course_teaching_class_id, String t_course_teaching_class_reference_type_id);


    Page<CourseTeachingClassReference> getPage(String t_course_teaching_class_id,
                                               String t_course_teaching_class_reference_type_id, int pageNo, int pageSize);
}
