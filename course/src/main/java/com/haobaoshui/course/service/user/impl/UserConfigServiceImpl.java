package com.haobaoshui.course.service.user.impl;


import com.haobaoshui.course.configure.CommonConstant;
import com.haobaoshui.course.model.user.UserConfig;
import com.haobaoshui.course.repository.user.UserConfigRepository;
import com.haobaoshui.course.service.user.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserConfigServiceImpl implements UserConfigService {


    private final UserConfigRepository userConfigRepository;

    @Autowired
    public UserConfigServiceImpl(UserConfigRepository userConfigRepository) {
        this.userConfigRepository = userConfigRepository;
    }

    @Override
    public String add(String t_user_id, int page_size) {
        return userConfigRepository.add(t_user_id, page_size);
    }

    @Override
    public boolean isUserConfigExists(String t_user_id) {
        return userConfigRepository.isUserConfigExists(t_user_id);
    }

    @Override
    public void updateByUserId(String t_user_id, int page_size) {
        if (userConfigRepository.isUserConfigExists(t_user_id))
            userConfigRepository.updateByUserId(t_user_id, page_size);
        else add(t_user_id, page_size);
    }

    /**
     * 得到指定用户的页面大小
     */
    @Override
    public int getPageSize(String t_user_id) {

        UserConfig userConfig = userConfigRepository.getUserByUserId(t_user_id);
        if (userConfig != null) return userConfig.getPageSize();

        return CommonConstant.PAGE_SIZE;

    }

    @Override
    public void deleteByUserId(String t_user_id) {
        userConfigRepository.deleteById(t_user_id);
    }

}
