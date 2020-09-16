package com.haobaoshui.course.repository.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Permission;


public interface PermissionRepository {

    String add(String name, String note, String t_permission_operator_id, String t_resource_id);

    int deleteById(String id);


    Permission GetById(String id);


    long getCount();

    Page<Permission> getPage(
            int pageNo, int pageSize);


}
