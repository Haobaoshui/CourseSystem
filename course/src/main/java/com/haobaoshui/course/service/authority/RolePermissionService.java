package com.haobaoshui.course.service.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.RolePermission;
import com.haobaoshui.course.model.authority.RolePermissionViewData;

import java.util.List;


public interface RolePermissionService {


    String add(String t_role_id, String t_permission_id);

    List<RolePermission> getByRoleId(String t_role_id);

    Page<RolePermissionViewData> getPageRolePermissionViewData(String roleId,
                                                               int pageNo, int pageSize);

    Page<RolePermission> getPage(int pageNo, int pageSize);

    /*删除
     * */
    void deleteByRoleId(String t_role_id);


}
