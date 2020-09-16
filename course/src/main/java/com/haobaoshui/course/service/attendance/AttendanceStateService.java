package com.haobaoshui.course.service.attendance;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceState;

import java.util.List;


public interface AttendanceStateService {

    /**
     * 添加
     *
     * @param attendanceState
     * @return
     */
    String add(AttendanceState attendanceState);


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

    int delete(AttendanceState attendanceState);

    int deleteAll();

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param note
     */
    int update(String id, String name, String note);

    int update(AttendanceState attendanceState);


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
    List<AttendanceState> getAllByLikeName(String name);


    /*根据name得到数据
     * */
    AttendanceState getByName(String userName);

    /*根据用户ID得到用户
     * */
    AttendanceState getByID(String id);

    /*
     * 返回所有结果
     * */
    List<AttendanceState> getAll();


    /**
     * 获取特定页面数据
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page<AttendanceState> getPage(int pageNo, int pageSize);

    /**
     * 所有数据位于同一页
     *
     * @return
     */
    Page<AttendanceState> getAllPage();

    /**
     * 得到包含name的分页查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AttendanceState> getPageByLikeName(String name, int pageNo, int pageSize);
}
