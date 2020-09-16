package com.haobaoshui.course.repository.logs;

import com.haobaoshui.course.model.Login;

import java.sql.Timestamp;


public interface LoginRepository {


    /**
     * 删除指定用户的登录信息
     */
    int deleteById(String id);

    int deleteByUserId(String t_user_id);

    /**
     * 增加登录信息
     */
    String add(Login login);

    String add(String t_user_id, Timestamp login_datetime, String login_ip);

    String add(String t_user_id, String login_ip);
}
