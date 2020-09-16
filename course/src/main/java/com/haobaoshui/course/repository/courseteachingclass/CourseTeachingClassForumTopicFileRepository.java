package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopicFile;

import java.util.List;


public interface CourseTeachingClassForumTopicFileRepository {


    String add(CourseTeachingClassForumTopicFile submitfile);

    String add(String t_course_teaching_class_forum_topic_id, String filename, String filepath);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_forum_topic_id);

    int update(String id, String t_forum_topic_id, String filename, String filepath);


    CourseTeachingClassForumTopicFile getByID(String id);


    List<CourseTeachingClassForumTopicFile> getByForumTopicID(String t_course_teaching_class_forum_topic_id);


    long getCount(String t_course_teaching_class_forum_topic_id);



}
