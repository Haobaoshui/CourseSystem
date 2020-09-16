package com.haobaoshui.course.repository.authority;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.UserGroup;

import java.util.List;


public interface UserGroupRepository {

    String add(String t_group_id, String t_user_id);

    int update(String t_user_id, String[] groupId);


    int deleteById(String id);


    int deleteByUserId(String t_user_id);


    int deleteByGroupId(String t_group_id);

    int deleteByGroupIdAndUserId(String t_group_id, String t_user_id);


    long getCount(String t_group_id);

    long getGroupCountByUserId(String t_user_id);

    List<UserGroup> getUserGroupByUserId(String t_user_id);


    List<UserGroup> getUserGroupByGroupId(String t_group_id);


    Page<UserGroup> getPageByGroupId(String t_group_id, int pageNo, int pageSize);


    Page<UserGroup> getPageByUserId(String t_user_id, int pageNo, int pageSize);
}
