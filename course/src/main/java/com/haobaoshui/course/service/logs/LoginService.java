package com.haobaoshui.course.service.logs;

import com.haobaoshui.course.model.Login;


/**
 * 登录服务：记录用户登录相关信息，包括ip地址、时间等
 */
public interface LoginService {
    void Add(Login login);

    void Add(String t_user_id, String login_ip);

    void deleteByUserId(String t_user_id);
}
