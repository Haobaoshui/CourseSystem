package com.haobaoshui.course.service.database;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.DatabaseLoggingType;

import java.util.List;


public interface DatabaseLoggingTypeService {
    /**
     * 添加
     *
     * @param databaseLoggingType
     * @return
     */
    String add(DatabaseLoggingType databaseLoggingType);


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

    int delete(DatabaseLoggingType databaseLoggingType);

    int deleteAll();

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param note
     */
    int update(String id, String name, String note);

    int update(DatabaseLoggingType databaseLoggingType);


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
    List<DatabaseLoggingType> getAllByLikeName(String name);


    /*根据name得到数据
     * */
    DatabaseLoggingType getByName(String userName);

    /*根据用户ID得到用户
     * */
    DatabaseLoggingType getByID(String id);

    /*
     * 返回所有结果
     * */
    List<DatabaseLoggingType> getAll();


    /**
     * 获取特定页面数据
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page<DatabaseLoggingType> getPage(int pageNo, int pageSize);

    /**
     * 所有数据位于同一页
     *
     * @return
     */
    Page<DatabaseLoggingType> getAllPage();

    /**
     * 得到包含name的分页查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DatabaseLoggingType> getPageByLikeName(String name, int pageNo, int pageSize);


}
