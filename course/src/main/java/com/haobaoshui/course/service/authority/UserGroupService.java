package com.haobaoshui.course.service.authority;

import com.haobaoshui.course.model.authority.Group;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.UserGroup;
import com.haobaoshui.course.model.UserGroupViewData;

import java.util.List;


public interface UserGroupService {


     String add(String t_group_id, String t_user_id);

     void deleteById(String t_user_group_id);

     void deleteByGroupId(String t_group_id);

     void deleteByUserId(String t_user_id);

     void deleteByGroupIdAndUserId(String t_group_id, String t_user_id);

     long getCount(String t_group_id);

     UserGroupViewData getUserGroupViewData(UserGroup ug);

     List<UserGroup> getUserGroupByGroupId(String t_group_id);

     List<Group> getGroupByUserId(String t_user_id);

     List<UserGroupViewData> getUserGroupViewDataByGroupId(String t_group_id);


     Page<UserGroupViewData> getPageByGroupId(String t_group_id, int pageNo, int pageSize);


}
