package com.haobaoshui.course.service.authority;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Permission;
import com.haobaoshui.course.model.authority.PermissionViewData;

import java.util.List;


public interface PermissionService {


     String add(String name, String note, String t_permission_operator_id, String t_resource_id);

     String add(Permission permission);

     Page<Permission> getPage(int pageNo, int pageSize);

     Page<PermissionViewData> getPagePermissionViewData(int pageNo, int pageSize);

     Page<PermissionViewData> getPagePermissionViewData();


     List<PermissionViewData> getPermissionViewData();

     Permission getById(String t_permission_id);

     PermissionViewData getPermissionViewDataById(String t_permission_id);

     List<PermissionViewData> getPermissionViewDataByUserId(String t_user_id);
}
