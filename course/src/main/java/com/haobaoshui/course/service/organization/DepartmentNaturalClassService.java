package com.haobaoshui.course.service.organization;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.DepartmentNaturalClass;
import com.haobaoshui.course.model.organization.DepartmentNaturalClassViewData;
import com.haobaoshui.course.model.organization.NaturalClass;

import java.util.List;


public interface DepartmentNaturalClassService {


    String addIds(String t_department_id, String t_natural_class_id);

    void delete(String t_natural_class_id);

    DepartmentNaturalClass getByNaturalClassId(String t_natural_class_id);

    Department getDepartmentByNaturalClassId(String t_natural_class_id);


    String getNaturalclassIdByNaturalClassName(String naturalclassname);


    /**
     * 根据
     */
    Department getDepartmentNaturalClassId(String t_natural_class_id);

    /**
     * 根据
     */
    NaturalClass getNaturalClassById(String id);


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
     * 根据系部id得到该学院下所有的自然班
     */
    List<NaturalClass> getNaturalClassByDepartmentId(String t_department_id);

    /**
     * 根据学院id得到该学院下所有的自然班
     */
    List<NaturalClass> getNaturalClassBySchoolId(String t_school_id);

    /**
     * 根据系部id得到该学院下所有的自然班
     */
    List<DepartmentNaturalClass> getDepartmentNaturalClassByDepartmentId(String t_department_id);

    /*
     * 根据ID得到
     */
    DepartmentNaturalClassViewData getNaturalClassSchoolViewDataByID(String id);

    Page<DepartmentNaturalClassViewData> getPage(String t_department_id, int pageNo, int pageSize);


    Page<NaturalClass> getNaturalClassPage(String t_department_id, int pageNo, int pageSize);

}
