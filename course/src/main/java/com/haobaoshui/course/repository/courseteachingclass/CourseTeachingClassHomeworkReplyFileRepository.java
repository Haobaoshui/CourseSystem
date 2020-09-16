package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReplyFile;

import java.util.List;


public interface CourseTeachingClassHomeworkReplyFileRepository {

    String add(CourseTeachingClassHomeworkReplyFile expriment);

    String add(String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_homework_baseinfo_id);


    int update(String id, String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath);


    CourseTeachingClassHomeworkReplyFile getByID(String id);


    List<CourseTeachingClassHomeworkReplyFile> getByCourseTeachingClassHomeworkReplyID(String t_course_teaching_class_homework_reply_id);


    long getCount(String t_group_id);



}
