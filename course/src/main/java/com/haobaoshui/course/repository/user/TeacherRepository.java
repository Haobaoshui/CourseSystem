package com.haobaoshui.course.repository.user;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.user.Teacher;


public interface TeacherRepository {

    /* 增加教师 */
    String add(Department department, Teacher teacher);


    int UpdateTeacherNumById(String t_teacher_id, String teacher_num);

    /*
     * 删除
     */
    int deleteById(String t_teacher_id);

    int getCount();

    /**
     * 根据教师id得到教师所在部门id
     */
    String getDepartmentIdByTeacherId(String t_teacher_id);

    /*
     * 根据用户ID得到用户
     */
    Teacher getTeacherByID(String t_teacher_id);

    /*
     * 根据教师工号ID得到用户
     */
    Teacher getTeacherByTeacherNum(String teacher_num);

    /*
     * 根据t_user_id得到用户
     */
    Teacher getTeacherByUserId(String t_user_id);


    Page<Teacher> getPage(String t_school_id, int pageNo, int pageSize);

}
