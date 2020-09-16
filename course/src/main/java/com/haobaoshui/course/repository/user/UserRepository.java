package com.haobaoshui.course.repository.user;

import com.haobaoshui.course.model.user.User;


public interface UserRepository {

    /*增加用户*/
    String add(User user);

    /*
     * 删除用户
     */
    int deleteById(String t_user_id);


    /*根据用户名与密码判断用户是否存在，若存在返回true，否则返回false
     * */
    boolean isUserExists(String userName, String userPwd);


    /**
     * 更新用户密码
     *
     * @param id
     * @param strEncodedPwd
     * @return
     */
    int updateUserPassword(String id, String strEncodedPwd);


    /*根据用户名与密码得到用户
     * */
    User getUser(String userName, String userPwd);

    /*根据用户名得到用户
     * */
    User getUserByName(String userName);

    /*根据用户ID得到用户
     * */
    User getUserByID(String t_user_id);


}
