package com.haobaoshui.course.repository.user;

import com.haobaoshui.course.model.user.UserContactInfo;

import java.util.List;


public interface UserContactInfoRepository {

    /* 增加UserContactInfo */
    String add(UserContactInfo usercontactinfo);

    /*
     *
     */
    int deleteById(String id);

    /*
     *
     */
    int deleteByUserId(String t_user_id);

    /*
     *
     */
    int deleteByUserContactTypeId(String t_user_contact_type_id);


    /**
     * 更新用户个人信息，更新前先删除以前旧信息
     */
    int update(String t_user_id, String[] contacttypeId, String[] user_contact_value);

    int update(String t_user_id, UserContactInfo[] usercontactinfos);


    /*
     * 根据ID得到UserContactInfo
     */
    UserContactInfo getById(String id);

    /*
     * 根据用户ID得到UserContactInfo
     */
    List<UserContactInfo> getByUserId(String t_user_id);


}
