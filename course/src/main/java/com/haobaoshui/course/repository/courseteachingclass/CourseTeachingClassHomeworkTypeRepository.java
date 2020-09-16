package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkType;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface CourseTeachingClassHomeworkTypeRepository {


    String add(CourseTeachingClassHomeworkType expriment);

    String add(String t_course_teaching_class_id, String name, String note);

    int deleteById(String id);

    int deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    int update(String id, String name, String note);


    CourseTeachingClassHomeworkType getByID(String id);

    /**
     * 根据教学班得到通知
     */
    List<CourseTeachingClassHomeworkType> getByCourseTeachingClassID(String t_course_teaching_class_id);




    long getCount(String t_course_teaching_class_id);


    Page<CourseTeachingClassHomeworkType> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);

    Page<CourseTeachingClassHomeworkType> getPage(String t_course_teaching_class_id);
}
