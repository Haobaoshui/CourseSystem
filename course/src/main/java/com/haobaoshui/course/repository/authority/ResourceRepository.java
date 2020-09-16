package com.haobaoshui.course.repository.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Resource;

import java.util.List;


public interface ResourceRepository {


    /* 增加用户 */
    String add(Resource t);

    /*
     * 添加
     */
    String add(String uri, String uri_element);

    /*
     * 删除
     */
    int delete(String id);

    /*
     * 更新
     */
    int update(String id, String uri, String uri_element);

    /*
     * 查找
     */
    List<Resource> select(String uri);

    /*
     * 根据用户名得到用户
     */
    Resource getByURI(String uri);

    /*
     * 根据用户ID得到用户
     */
    Resource getByID(String id);

    /*
     * 返回所有结果
     */
    List<Resource> getAll();

    /*
     * 得到学院总数
     */
    long getCount();

    /*
     * 分页查询
     *
     * @PageBegin 开始页面，从0开始
     */
    List<Resource> PageQuery(int PageBegin, int PageSize);

    /**
     * 获取学院
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page<Resource> getPage(int pageNo, int pageSize);

    Page<Resource> getAllPage();

    Page<Resource> select(String name, int pageNo, int pageSize);

}
