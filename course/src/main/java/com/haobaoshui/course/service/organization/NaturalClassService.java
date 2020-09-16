package com.haobaoshui.course.service.organization;

import com.haobaoshui.course.model.organization.NaturalClass;

public interface NaturalClassService {


    /**
     * 在指定学院增加班级
     */
    String Add(String t_department_id, String strNaturalClassName);

    /**
     * 在指定学院增加班级
     */
    String Add(String t_department_id, String strNaturalClassName, String strNaturalClassNote);

    /**
     * 在默认学院默认系部中增加班级
     */

    String Add2DefaultSchoolAndDefaultDepartment(String strNaturalClassName, String strNaturalClassNote);

    /**
     * 在默认学院默认系部中增加班级
     */

    String Add2DefaultSchoolAndDefaultDepartment(String strNaturalClassName);

    /**
     * 在学院默认系部中增加班级
     */

    String Add2SchoolDefaultDepartment(String t_school_id, String strNaturalClassName);

    /**
     * 得到默认学院默认系部下的班级,如果没有则创建该班级
     */
    String getDefaultNaturalClassId(String strNaturalClassName);

    /**
     * 得到指定系部下的班级,如果没有则创建该班级
     */
    String getDefaultNaturalClassIdByDepartmentId(String t_department_id, String strNaturalClassName);

    /**
     * 得到指定系学院的班级,如果没有则创建该班级
     */
    String getDefaultNaturalClassIdBySchoolId(String t_school_id, String strNaturalClassName);

    /**
     * 得到指定系部下的默认班级
     */
    String getDefaultNaturalClassIdByDepartmentId(String t_department_id);

    /**
     * 得到默认学院下的默认系部下的默认班级
     */
    String getDefaultNaturalClassId();

    /**
     * 得到指定学院下的默认系部下的默认班级
     */
    String getDefaultNaturalClassIdBySchoolId(String t_school_id);

    /**
     * 得到指定学院下的指定系部下的默认班级
     */
    String getDefaultNaturalClassIdBySchoolIdAndDepartmentId(String t_school_id, String t_department_id);

    /**
     * 得到指定学院下的指定系部下的默认班级
     */
    String getDefaultNaturalClassIdBySchoolNameAndDepartmentName(String school_name, String department_name);

    /**
     * 得到指定学院下的指定系部下的指定班级
     */
    String getDefaultNaturalClassIdBySchoolNameAndDepartmentNameAndNaturallClassName(String school_name,
                                                                                     String department_name, String natural_class_name);

    /**
     * 添加学生
     */
    String getNaturalClassId(String schoolName, String departmentName, String naturalclassname);

    NaturalClass getById(String id);

    NaturalClass getByName(String name);


}
