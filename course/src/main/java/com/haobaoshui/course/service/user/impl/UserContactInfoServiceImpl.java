package com.haobaoshui.course.service.user.impl;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.user.UserContactInfo;
import com.haobaoshui.course.model.user.UserContactInfoViewData;
import com.haobaoshui.course.repository.user.UserContactInfoRepository;
import com.haobaoshui.course.service.user.UserContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserContactInfoServiceImpl implements UserContactInfoService {


    private final UserContactInfoRepository userContactInfoRepository;


    @Autowired
    public UserContactInfoServiceImpl(UserContactInfoRepository userContactInfoRepository) {
        this.userContactInfoRepository = userContactInfoRepository;
    }

    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     */
    @Override
    public String add(UserContactInfo usercontactinfo) throws UserExistException {
        return userContactInfoRepository.add(usercontactinfo);
    }

    @Override
    public void deleteByUserId(String t_user_id) {
        userContactInfoRepository.deleteByUserId(t_user_id);
    }

    @Override
    public int update(String t_user_id, String[] contacttypeId, String[] user_contact_value) {
        return userContactInfoRepository.update(t_user_id, contacttypeId, user_contact_value);
    }

    @Override
    public int update(String t_user_id, UserContactInfo[] usercontactinfos) {
        return userContactInfoRepository.update(t_user_id, usercontactinfos);
    }

    /**
     * @param t_user_id 用户id
     * @return User
     */
    @Override
    public List<UserContactInfo> getByUserId(String t_user_id) {
        return userContactInfoRepository.getByUserId(t_user_id);
    }

    @Override
    public List<UserContactInfoViewData> getUserContactInfoViewDataByUserId(String t_user_id) {
        return null;
    }
}
