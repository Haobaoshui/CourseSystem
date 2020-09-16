package com.haobaoshui.course.repository.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.GroupRole;

import java.util.List;


public interface GroupRoleRepository {


    String add(String t_group_id, String t_role_id);

    int deleteById(String id);

    int deleteByGroupId(String t_group_id);

    GroupRole getById(String id);

    List<GroupRole> getByGroupId(String t_group_id);

    List<GroupRole> getByRoleId(String t_role_id);


    long getCount(String t_group_id);

    Page<GroupRole> getPageByGroupId(String t_group_id,
                                     int pageNo, int pageSize);

}
