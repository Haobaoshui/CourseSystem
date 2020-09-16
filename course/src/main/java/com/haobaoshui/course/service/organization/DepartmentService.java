package com.haobaoshui.course.service.organization;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.School;

import java.util.List;


public interface DepartmentService {

    /**
     * 添加
     *
     * @param department
     * @return
     */
    String add(Department department);

    /**
     * 添加
     *
     * @param name
     * @param note
     * @return
     */
    String addDepartment(String name, String note);

    String add(String t_school_id, String departmentName, String departmentNote);

    String add(String t_school_id, String departmentName);

    void delete(String t_school_id, String t_department_id);

    /**
     * 根据id删除
     *
     * @param id
     */
    int deleteById(String id);

    int deleteByName(String name);

    int deleteByLikeName(String name);

    int deleteAll();

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param note
     */
    int update(String id, String name, String note);

    int updateName(String id, String name);

    int updateNote(String id, String note);

    int update(Department department);


    String getSchoolIdByDepartmentId(String t_department_id);

    /**
     * 得到默认学院下的默认系部
     */
    String getDefaultDepartmentIdInDefaultSchoolId();

    /**
     * 得到指定学院下的默认系部
     */
    String getDefaultDepartmentIdInSchoolId(String t_school_id);


    Page<Department> getPage(String t_school_id);


    List<Department> getAll(String t_school_id);

    /**
     *
     */
    long getDepartmentCount(String t_school_id, String t_department_id);


    /*
     * 得到所有数据总数
     * */
    int getCount();

    /*
     * 返回所有结果
     * */
    List<Department> getAll();

    /**
     * 得到包含name的所有数据
     *
     * @param name
     * @return
     */
    List<Department> getAllByLikeName(String name);


    /*根据name得到数据
     * */
    Department getByName(String name);

    /*根据用户ID得到用户
     * */
    Department getByID(String id);

    School getSchoolByDepartmentId(String t_department_id);


    /**
     * 获取特定页面数据
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page<Department> getPage(int pageNo, int pageSize);

    /**
     * 所有数据位于同一页
     *
     * @return
     */
    Page<Department> getAllPage();

    /**
     * 得到包含name的分页查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Department> getPageByLikeName(String name, int pageNo, int pageSize);

}
