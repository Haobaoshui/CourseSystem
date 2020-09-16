package com.haobaoshui.course.repository.course;


import com.haobaoshui.course.model.course.CourseStyle;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface CourseStyleRepository {

    /**
     * 添加
     * @param courseStyle
     * @return
     */
     String add(CourseStyle courseStyle);

    /**
     * 添加
     * @param name
     * @param note
     * @return
     */
     String add(String name,String note);

    /**
     * 根据id删除
     * @param id
     */
     int deleteById(String id);
     int deleteByName(String name);
     int deleteByLikeName(String name);
     int deleteAll();

    /**
     * 更新
     * @param id
     * @param name
     * @param note
     */
     int update(String id,String name,String note);
     int updateName(String id,String name);
     int updateNote(String id,String note);
     int update(CourseStyle courseStyle);

    /*
     * 得到所有数据总数
     * */
     int getCount();

    /*
     * 返回所有结果
     * */
     List<CourseStyle> getAll();

    /**
     * 得到包含name的所有数据
     * @param name
     * @return
     */
     List<CourseStyle> getAllByLikeName(String name);



    /*根据name得到数据
     * */
     CourseStyle getByName(String userName);

    /*根据用户ID得到用户
     * */
     CourseStyle getByID(String id);


    /**
     * 获取特定页面数据
     * @param pageNo 页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
     Page<CourseStyle> getPage(int pageNo, int pageSize);

    /**
     * 所有数据位于同一页
     * @return
     */
     Page<CourseStyle> getAllPage();

    /**
     * 得到包含name的分页查询
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
     Page<CourseStyle> getPageByLikeName(String name, int pageNo, int pageSize);
}