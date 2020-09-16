package com.haobaoshui.course.service.user;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.user.UserContactInfo;
import com.haobaoshui.course.model.user.UserContactInfoViewData;

import java.util.List;


public interface UserContactInfoService {


    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param usercontactinfo
     */
    String add(UserContactInfo usercontactinfo) throws UserExistException;

    void deleteByUserId(String t_user_id);

    int update(String t_user_id, String[] contacttypeId, String[] user_contact_value);

    int update(String t_user_id, UserContactInfo[] usercontactinfos);

    /**
     * @param t_user_id 用户id
     * @return User
     */
    List<UserContactInfo> getByUserId(String t_user_id);

    List<UserContactInfoViewData> getUserContactInfoViewDataByUserId(String t_user_id);
}
