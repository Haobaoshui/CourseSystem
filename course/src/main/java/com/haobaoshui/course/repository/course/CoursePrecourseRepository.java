package com.haobaoshui.course.repository.course;

import com.haobaoshui.course.model.course.CoursePrecourse;

import java.util.List;


public interface CoursePrecourseRepository {

    String add(CoursePrecourse coursePrecourse);

    String add(String t_course_id_this, String t_course_id_pre);

    int deleteById(String id);

    int deleteByCourseId(String t_course_id);

    int update(CoursePrecourse coursePrecourse);

    int update(String id, String t_course_id_this, String t_course_id_pre);


    CoursePrecourse getById(String id);

    List<CoursePrecourse> getPreCourse(String t_course_id);


}
