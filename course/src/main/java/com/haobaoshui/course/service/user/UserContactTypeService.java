package com.haobaoshui.course.service.user;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.user.UserContactType;

import java.util.List;


public interface UserContactTypeService {
    /**
     * 添加
     *
     * @param userContactType
     * @return
     */
    String add(UserContactType userContactType);


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

    int delete(UserContactType userContactType);

    int deleteAll();

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param note
     */
    int update(String id, String name, String note);

    int update(UserContactType userContactType);


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
    List<UserContactType> getAllByLikeName(String name);


    /*根据name得到数据
     * */
    UserContactType getByName(String name);

    /*根据用户ID得到用户
     * */
    UserContactType getByID(String id);

    /*
     * 返回所有结果
     * */
    List<UserContactType> getAll();


    /**
     * 获取特定页面数据
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page<UserContactType> getPage(int pageNo, int pageSize);

    /**
     * 所有数据位于同一页
     *
     * @return
     */
    Page<UserContactType> getAllPage();

    /**
     * 得到包含name的分页查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<UserContactType> getPageByLikeName(String name, int pageNo, int pageSize);


}
