package com.haobaoshui.course.repository.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.RolePermission;

import java.util.List;


public interface RolePermissionRepository {


    /**
     * 添加Permission
     */
    String add(String t_role_id, String t_permission_id);

    /*删除
     * */
    int deleteByID(String id);

    /*删除
     * */
    int deleteByRoleId(String t_role_id);

    /*删除
     * */
    int deletePermissionId(String t_permission_id);


    List<RolePermission> getRolePermissionByRoleId(String t_role_id);


    long getCount();

    long getCount(String roleId);

    Page<RolePermission> getPage(int pageNo, int pageSize);


}
