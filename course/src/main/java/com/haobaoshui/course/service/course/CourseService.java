package com.haobaoshui.course.service.course;

import com.haobaoshui.course.exception.CourseExistException;
import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.course.CourseViewData;
import com.haobaoshui.course.model.Page;

import javax.servlet.http.HttpServletRequest;


public interface CourseService {


    String add(Course course, String[] t_department_ids, String[] precourseIds);

    String add(String name, String englishname, String num, int class_hours, int experiment_hours, String t_course_type_id,
               String t_course_style_id, String[] t_department_ids, String[] precourseIds) throws CourseExistException;


    String update(String t_course_id, String name, String englishname, String num, int class_hours, int experiment_hours,
                  String t_course_type_id, String t_course_style_id, String[] t_department_ids, String[] precourseIds);

    boolean isExist(String num);

    /**
     * 删除课程
     */
    int deleteById(HttpServletRequest request, String t_course_id);

    /**
     * 根据t_course_type_id删除课程
     */
    void deleteByCourseTypeId(HttpServletRequest request, String t_course_type_id);

    /**
     * 根据t_course_style_id删除课程
     */
    void deleteByCourseStyleId(HttpServletRequest request, String t_course_style_id);

    /**
     * 根据t_course_type_id、t_course_style_id删除课程
     */
    void deleteByCourseTypeIdAndStyleId(HttpServletRequest request, String t_course_type_id, String t_course_style_id);

    Course getById(String t_course_id);


    CourseViewData getCourseViewDataById(String t_course_id);

    /**
     * 得到
     */
    Page<CourseViewData> getPage(int pageNo, int pageSize);


}
