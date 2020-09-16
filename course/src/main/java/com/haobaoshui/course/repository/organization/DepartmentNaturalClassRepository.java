package com.haobaoshui.course.repository.organization;

import com.haobaoshui.course.model.organization.DepartmentNaturalClass;

import java.util.List;


public interface DepartmentNaturalClassRepository {


    /**
     * 增加记录
     */
    String add(String t_department_id, String t_natural_class_id);

    /**
     * 删除指定id
     */
    int deleteById(String id);

    /**
     * 删除
     */

    int deleteByDepartmentId(String t_department_id);

    /**
     * 删除
     */
    int deleteByNaturalclassId(String t_natural_class_id);

    DepartmentNaturalClass getById(String id);

    /**
     * 根据t_natural_class_id得到记录
     */
    DepartmentNaturalClass getByNaturalclassId(String t_natural_class_id);

    /*
     * 得到id
     */
    String getIdByDepartmentNaturalclassId(String t_department_id, String t_natural_class_id);

    /*
     * 得到id
     */
    String getIdByDepartmentIdNaturalclassName(String t_department_id, String t_natural_class_name);

    /*
     * 得到
     */
    DepartmentNaturalClass getByDepartmentNaturalclassId(String t_department_id, String t_natural_class_id);

    /**
     * 根据
     */
    List<DepartmentNaturalClass> getByDepartmentId(String t_department_id);


    /*
     * 得到总数
     */
    long getCount(String t_department_id);


}
