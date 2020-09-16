package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceFile;

import java.util.List;


public interface CourseTeachingClassReferenceFileRepository {


    int update(String id, String t_course_teaching_class_reference_id, String filename, String filepath);

    /*
     * 根据用户ID得到用户
     */
    CourseTeachingClassReferenceFile getByID(String id);

    /**
     * 根据课程-作业基本信息得到对应的文件列表
     */
    List<CourseTeachingClassReferenceFile> getByCourseTeachingClassHomeworkBaseInfoID(String t_course_teaching_class_reference_id);

    /* 增加用户 */
    String add(CourseTeachingClassReferenceFile file);

    String add(String t_course_teaching_class_reference_id, String filename, String filepath);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_reference_id);


    long getCount(String t_group_id);



}
