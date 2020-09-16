package com.haobaoshui.course.repository.user;

import com.haobaoshui.course.model.user.UserBasicInfo;

import java.util.Date;


public interface UserBasicInfoRepository {


    /* 增加UserContactInfo */
    String add(UserBasicInfo userbasicinfo);

    /*
     *
     */
    int deleteById(String id);

    /*
     *
     */
    int deleteByUserId(String t_user_id);


    int updateByUserId(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex);

    int updateByUserId(String t_user_id, String user_contact_info_name, String user_basic_info_birthday,
                       String user_basic_info_sex);

    int updateByUserId(String t_user_id, String user_contact_info_name, Date user_basic_info_birthday,
                       int user_basic_info_sex);

    int updateByUserId(String t_user_id, UserBasicInfo userbasicinfo);

    /*
     * 根据用户ID得到UserContactInfo
     */
    UserBasicInfo getUserBasicInfoByUserId(String t_user_id);

    /*
     * 根据ID得到UserContactInfo
     */
    UserBasicInfo getUserBasicInfoByID(String id);


}
