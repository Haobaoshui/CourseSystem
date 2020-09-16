package com.haobaoshui.course.repository.organization;

import com.haobaoshui.course.model.organization.DepartmentTeacher;

import java.util.List;


public interface DepartmentTeacherRepository {

    /*增加*/
    String add(String t_department_id, String t_teacher_id);

    int deleteById(String id);


    int deleteByTeacherId(String t_teacher_id);


    int deleteByDepartmentId(String t_department_id);

    /**
     *
     */
    String getDepartmentIdByTeacherId(String t_teacher_id);


    /*根据用户ID得到用户
     * */
    DepartmentTeacher getById(String id);

    /**
     * 得到指定部门的全部教师id
     */
    List<DepartmentTeacher> getByDepartmentId(String t_department_id);


}
