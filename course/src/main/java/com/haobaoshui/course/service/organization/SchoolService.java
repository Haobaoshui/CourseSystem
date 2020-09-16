package com.haobaoshui.course.service.organization;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.School;

import java.util.List;


public interface SchoolService {


    /**
     * 添加
     *
     * @param school
     * @return
     */
    String add(School school);


    /**
     * 添加
     *
     * @param name
     * @param note
     * @return
     */
    String add(String name, String note);

    /**
     * 根据id删除
     *
     * @param id
     */
    int deleteById(String id);

    int delete(School school);

    int deleteAll();

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param note
     */
    int update(String id, String name, String note);

    int update(School school);


    /*
     * 得到所有数据总数
     * */
    int getCount();

    /**
     * 得到包含name的所有数据
     *
     * @param name
     * @return
     */
    List<School> getAllByLikeName(String name);


    /*根据name得到数据
     * */
    School getByName(String name);

    /*根据用户ID得到用户
     * */
    School getByID(String id);

    School getByDepartmentId(String t_department_id);


    /*
     * 返回所有结果
     * */
    List<School> getAll();


    /**
     * 获取特定页面数据
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page<School> getPage(int pageNo, int pageSize);

    /**
     * 所有数据位于同一页
     *
     * @return
     */
    Page<School> getAllPage();

    /**
     * 得到包含name的分页查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<School> getPageByLikeName(String name, int pageNo, int pageSize);

    /**
     * 如果没有指定学院的话，得到缺省学院ID
     *
     * @return
     */
    String getDefaultSchoolId();


}
