package com.haobaoshui.course.service.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Resource;

import java.util.List;


public interface ResourceService {


    String add(Resource resource);

    /*
     * 添加
     */
    String add(String uri, String uri_element);

    /*
     * 删除
     */
    int delete(String id);

    int delete(Resource resource);

    /*
     * 更新
     */
    int update(String id, String uri, String uri_element);

    int update(Resource resource);

    /*
     * 查找
     */
    List<Resource> select(String uri);


    Resource getByURI(String uri);


    Resource getByID(String id);

    /*
     * 返回所有结果
     */
    List<Resource> getAll();


    Page<Resource> getPage(int pageNo, int pageSize);

    Page<Resource> getAllPage();

    Page<Resource> select(String name, int pageNo, int pageSize);

}
