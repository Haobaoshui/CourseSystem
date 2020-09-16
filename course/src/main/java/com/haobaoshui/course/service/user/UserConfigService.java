package com.haobaoshui.course.service.user;

public interface UserConfigService {


    String add(String t_user_id, int page_size);

    boolean isUserConfigExists(String t_user_id);

    void updateByUserId(String t_user_id, int page_size);

    /**
     * 得到指定用户的页面大小
     */
    int getPageSize(String t_user_id);

    void deleteByUserId(String t_user_id);

}
