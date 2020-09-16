package com.haobaoshui.course.repository.organization;

import com.haobaoshui.course.model.organization.SchoolDepartment;

import java.util.List;


public interface SchoolDepartmentRepository {


    String add(String t_school_id, String t_department_id);

    int deleteByDepartmentId(String t_department_id);

    int delete(String t_school_id, String t_department_id);

    String getSchoolIdByDepartmentId(String t_department_id);


    String getIdBySchoolIdDepartmentId(String t_school_id, String t_department_id);

    List<SchoolDepartment> getIdBySchoolIdDepartmentName(String t_school_id, String departmentName);


    List<SchoolDepartment> getDepartmentIdBySchoolId(String t_school_id);


    long getDepartmentCount(String t_school_id, String departmentName);

}
