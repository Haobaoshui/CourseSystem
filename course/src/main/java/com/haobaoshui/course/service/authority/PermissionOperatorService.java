package com.haobaoshui.course.service.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.PermissionOperator;

import java.util.List;


public interface PermissionOperatorService {
    /**
     * 添加
     *
     * @param permissionOperator
     * @return
     */
    String add(PermissionOperator permissionOperator);


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

    int delete(PermissionOperator permissionOperator);

    int deleteAll();

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param note
     */
    int update(String id, String name, String note);

    int update(PermissionOperator permissionOperator);


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
    List<PermissionOperator> getAllByLikeName(String name);


    /*根据name得到数据
     * */
    PermissionOperator getByName(String userName);

    /*根据用户ID得到用户
     * */
    PermissionOperator getByID(String id);

    /*
     * 返回所有结果
     * */
    List<PermissionOperator> getAll();


    /**
     * 获取特定页面数据
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page<PermissionOperator> getPage(int pageNo, int pageSize);

    /**
     * 所有数据位于同一页
     *
     * @return
     */
    Page<PermissionOperator> getAllPage();

    /**
     * 得到包含name的分页查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<PermissionOperator> getPageByLikeName(String name, int pageNo, int pageSize);


}
