package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitFile;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface CourseTeachingClassHomeworkSubmitFileRepository {

    String add(CourseTeachingClassHomeworkSubmitFile submitfile);

    String add(String t_course_teaching_class_homework_baseinfo_id, int fileNodeId, String filename, String filepath);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_homework_baseinfo_id);

    int update(String id, String t_course_teaching_class_homework_submit_baseinfo_id, int fileNodeId, String filename, String filepath);


    CourseTeachingClassHomeworkSubmitFile getByID(String id);


    List<CourseTeachingClassHomeworkSubmitFile> getByCourseTeachingClassHomeworkBaseInfoID(String t_course_teaching_class_homework_submit_baseinfo_id);


    long getCount(String t_group_id);


    Page<CourseTeachingClassHomeworkSubmitFile> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);
}
