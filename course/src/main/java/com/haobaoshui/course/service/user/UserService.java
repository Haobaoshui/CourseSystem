package com.haobaoshui.course.service.user;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.user.User;
import com.haobaoshui.course.model.user.UserInfoViewData;
import com.haobaoshui.course.model.user.UserSessionInfo;


public interface UserService {

    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param user
     */
    String add(User user) throws UserExistException;


    /**
     * 更新密码，注意传过的值是加密后的值
     */
    void updateUserPassword(String id, String strEncodedPwd);

    void deleteByUserId(String t_user_id);

    /**
     * 根据用户名/密码查询 User对象
     *
     * @param t_user_id 用户名
     * @return User
     */
    User getUserById(String t_user_id);

    /**
     * 根据用户名/密码查询 User对象
     *
     * @param userName 用户名
     * @return User
     */
    User getUserByUserName(String userName);

    UserInfoViewData getUserInfoViewDataById(String t_user_id);

    UserSessionInfo getUserSessionInfoByUserId(String t_user_id);


}
