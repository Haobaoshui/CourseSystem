package com.haobaoshui.course.service.course;

import com.haobaoshui.course.model.course.CourseDepartment;
import com.haobaoshui.course.model.course.CourseDepartmentViewData;

import java.util.List;

public interface CourseDepartmentService {

    String add(CourseDepartment courseDepartment);

    String add(String t_course_id, String t_department_id);


    int deleteById(String id);

    int deleteByCourseId(String t_course_id);

    int deleteByDepartmentId(String t_department_id);

    int update(String id, String t_course_id_pre, String t_department_id);

    long getCount(String t_course_id);

    CourseDepartment getById(String id);


    List<CourseDepartment> getByCourseId(String t_course_id);

    List<CourseDepartment> getByDepartmentId(String t_department_id);

    CourseDepartmentViewData getCourseDepartmentViewDataById(String id);

    List<CourseDepartmentViewData> getCourseDepartmentViewDataByCourseId(String t_course_id);
}
