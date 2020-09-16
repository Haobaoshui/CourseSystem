package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkBaseinfo;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassHomeworkBaseinfoRepository {

    String add(CourseTeachingClassHomeworkBaseinfo homeworkbaseinfo);

    String add(String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_homework_type_id,
               Integer flag, String file_requirement, String title, String content, Date pubdate, Date enddate);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    int deleteByTeacherId(String t_teacher_id);


    int update(String id, String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_homework_type_id,
               Integer flag, String file_requirement, String title, String content, Date pubdate, Date enddate);

    int update(String id, String t_teacher_id, Integer flag, String file_requirement, String title,
               String content, Date enddate);

    int update(String id, String t_teacher_id, String title, String content, Date enddate);


    int getCount(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id);


    CourseTeachingClassHomeworkBaseinfo getByID(String id);


    List<CourseTeachingClassHomeworkBaseinfo> getByHomeWorkTypeID(String t_course_teaching_class_homework_type_id);


    List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassID(String t_course_teaching_class_id);


    List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassIDAndHomeworkTypeId(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id);


    List<CourseTeachingClassHomeworkBaseinfo> getByTeacherID(String t_teacher_id);

    Page<CourseTeachingClassHomeworkBaseinfo> getPage(String t_course_teaching_class_id,
                                                      String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize);


}
