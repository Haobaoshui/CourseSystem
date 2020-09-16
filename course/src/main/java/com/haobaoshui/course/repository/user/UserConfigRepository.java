package com.haobaoshui.course.repository.user;

import com.haobaoshui.course.model.user.UserConfig;


public interface UserConfigRepository {


    /**
     * 增加用户配置
     */
    String add(UserConfig userConfig);

    /**
     * 增加用户配置
     */
    String add(String t_user_id, int page_size);


    /**
     * 删除
     */
    int deleteById(String t_user_config_id);

    /**
     * 根据用户Id删除
     */
    int deleteByUserId(String t_user_id);

    int updateById(String id, String t_user_id, int page_size);


    int updateById(String id, int page_size);

    int updateByUserId(String t_user_id, int page_size);


    /**
     * 根据用户id判断用户是否存在，若存在返回true，否则返回false
     */
    boolean isUserConfigExists(String t_user_id);


    /*根据用户名与密码得到用户
     * */
    UserConfig getById(String id);

    /*根据用户名得到用户
     * */
    UserConfig getUserByUserId(String t_user_id);


}
