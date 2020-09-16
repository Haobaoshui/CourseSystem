package com.haobaoshui.course.repository.organization;

import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.NaturalClass;
import com.haobaoshui.course.model.organization.NaturalClassStudent;
import com.haobaoshui.course.model.organization.School;

import java.util.List;


public interface NaturalClassStudentRepository {


    /**
     * 增加记录
     */
    String add(String t_natural_class_id, String t_student_id);

    /**
     * 删除指定id
     */
    int deleteById(String id);

    /**
     * 删除指定自然班学生
     */
    int deleteByNaturalClassId(String t_natural_class_id);

    /**
     * 删除指定自然班学生
     */
    int deleteByStudentId(String t_student_id);

    NaturalClassStudent getNaturalClassStudentById(String id);

    /**
     * 根据学生id得到记录，注意，一个学生只能存在一个自然班中
     */
    NaturalClassStudent getNaturalClassStudentByStudentId(String t_student_id);

    /**
     * 根据自然班id得到该班的学生id
     */
    List<String> getStudentIdByNaturalClassId(String t_natural_class_id);

    /**
     * 根据学生id得到学生所在学院
     */
    School getSchoolByStudentId(String t_student_id);

    /**
     * 根据学生id得到学生所在系部
     */
    Department getDepartmentByStudentId(String t_student_id);

    /**
     * 根据学生id得到学生所在自然班
     */
    NaturalClass getNaturalClassByStudentId(String t_student_id);

    /**
     * 指定学号是否存在
     */
    boolean isStudentExist(String t_natural_class_id, String t_student_num);

    /**
     * 获得指定学号的id
     */
    String getStudentId(String t_natural_class_id, String t_student_num);
}
