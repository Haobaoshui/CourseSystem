package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClass;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface CourseTeachingClassRepository {


    /**
     * 增加
     */
    String add(String t_course_id, String name, String t_course_teaching_term_id);

    /**
     * 删除
     */
    int deleteById(String id);

    /**
     * 删除
     */
    int deleteByCourseId(String t_course_id);


    /**
     * 更新
     */
    int update(String id, String t_course_id, String name, String t_course_teaching_term_id);

    /**
     * 更新
     */
    int update(String id, String name);


    // 根据id
    CourseTeachingClass getCourseTeachingClassById(String id);

    /**
     * 根据课程id
     */
    List<CourseTeachingClass> getCourseTeachingClassByCourseId(String t_course_id);


    /**
     * 根据课程id和教学班id
     */
    List<CourseTeachingClass> getCourseTeachingClassByCourseIdTeachingClassId(String t_course_id,
                                                                              String id);

    /**
     * 根据学年学期
     */
    List<CourseTeachingClass> getCourseTeachingClassByCourseTeachingTermId(String t_course_teaching_term_id);

    /**
     * */
    long getCount();


    /**
     *
     * */
    Page<CourseTeachingClass> getPage(int pageNo, int pageSize);

}
