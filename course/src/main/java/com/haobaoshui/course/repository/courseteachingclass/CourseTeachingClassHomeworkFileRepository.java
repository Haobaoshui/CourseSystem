package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkFile;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface CourseTeachingClassHomeworkFileRepository {

    String add(CourseTeachingClassHomeworkFile expriment);

    String add(String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_homework_baseinfo_id);

    int update(String id, String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath);


    CourseTeachingClassHomeworkFile getByID(String id);


    long getCount(String t_course_teaching_class_homework_baseinfo_id);

    List<CourseTeachingClassHomeworkFile> getByCourseTeachingClassHomeworkBaseInfoID(String t_course_teaching_class_homework_baseinfo_id);


    Page<CourseTeachingClassHomeworkFile> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);
}
