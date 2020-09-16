package com.haobaoshui.course.service.user.impl;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.user.UserBasicInfo;
import com.haobaoshui.course.repository.user.UserBasicInfoRepository;
import com.haobaoshui.course.service.user.UserBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserBasicInfoServiceImpl implements UserBasicInfoService {


    private final UserBasicInfoRepository userBasicInfoRepository;


    @Autowired
    public UserBasicInfoServiceImpl(UserBasicInfoRepository userBasicInfoRepository) {
        this.userBasicInfoRepository = userBasicInfoRepository;
    }

    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     */
    @Override
    public String add(UserBasicInfo userbasicinfo) throws UserExistException {
        return userBasicInfoRepository.add(userbasicinfo);
    }

    @Override
    public int updateByUserId(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex) {
        return userBasicInfoRepository.updateByUserId(t_user_id, user_basic_info_birthday, user_basic_info_sex);
    }

    @Override
    public int updateByUserId(String t_user_id, String user_contact_info_name, String user_basic_info_birthday, String user_basic_info_sex) {
        return userBasicInfoRepository.updateByUserId(t_user_id, user_contact_info_name, user_basic_info_birthday, user_basic_info_sex);
    }

    @Override
    public int updateByUserId(String t_user_id, String user_contact_info_name, Date user_basic_info_birthday, int user_basic_info_sex) {
        return userBasicInfoRepository.updateByUserId(t_user_id, user_contact_info_name, user_basic_info_birthday, user_basic_info_sex);
    }

    @Override
    public int updateByUserId(String t_user_id, UserBasicInfo userbasicinfo) {
        return userBasicInfoRepository.updateByUserId(t_user_id, userbasicinfo);
    }

    @Override
    public void deleteByUserId(String t_user_id) {
        userBasicInfoRepository.deleteByUserId(t_user_id);
    }

    /**
     * 根据用户名/密码查询 User对象
     * <p>
     * 用户名
     *
     * @return User
     */
    @Override
    public UserBasicInfo getByUserId(String t_user_id) {
        return userBasicInfoRepository.getUserBasicInfoByUserId(t_user_id);
    }
}
