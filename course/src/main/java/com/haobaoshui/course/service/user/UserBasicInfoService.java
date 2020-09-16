package com.haobaoshui.course.service.user;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.user.UserBasicInfo;

import java.util.Date;


public interface UserBasicInfoService {


    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param userbasicinfo
     */
    String add(UserBasicInfo userbasicinfo) throws UserExistException;

    int updateByUserId(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex);

    int updateByUserId(String t_user_id, String user_contact_info_name, String user_basic_info_birthday,
                       String user_basic_info_sex);

    int updateByUserId(String t_user_id, String user_contact_info_name, Date user_basic_info_birthday,
                       int user_basic_info_sex);

    int updateByUserId(String t_user_id, UserBasicInfo userbasicinfo);

    void deleteByUserId(String t_user_id);

    /**
     * 根据用户名/密码查询 User对象
     *
     * @param t_user_id 用户名
     * @return User
     */
    UserBasicInfo getByUserId(String t_user_id);
}
