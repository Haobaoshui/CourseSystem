package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.UserGroup;
import com.haobaoshui.course.model.authority.GroupRole;
import com.haobaoshui.course.model.authority.Permission;
import com.haobaoshui.course.model.authority.PermissionViewData;
import com.haobaoshui.course.model.authority.RolePermission;
import com.haobaoshui.course.repository.authority.PermissionRepository;
import com.haobaoshui.course.service.authority.GroupRoleService;
import com.haobaoshui.course.service.authority.PermissionService;
import com.haobaoshui.course.service.authority.RolePermissionService;
import com.haobaoshui.course.service.authority.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {


    private final PermissionRepository permissionRepository;
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    GroupRoleService groupRoleService;
    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public String add(String name, String note, String t_permission_operator_id, String t_resource_id) {
        return permissionRepository.add(name, note, t_permission_operator_id, t_resource_id);
    }

    @Override
    public String add(Permission permission) {
        return permissionRepository.add(permission.getName(), permission.getNote(), permission.getPermissionOperatorId(), permission.getResourceId());
    }

    @Override
    public Page<Permission> getPage(
            int pageNo, int pageSize) {
        return permissionRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<PermissionViewData> getPagePermissionViewData(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Page<PermissionViewData> getPagePermissionViewData() {
        return null;
    }


    @Override
    public List<PermissionViewData> getPermissionViewData() {
        return null;
    }

    @Override
    public Permission getById(String t_permission_id) {
        return null;
    }

    @Override
    public PermissionViewData getPermissionViewDataById(String t_permission_id) {
        return null;
    }

    @Override
    public List<PermissionViewData> getPermissionViewDataByUserId(String t_user_id) {

        List<UserGroup> usergrouplist = userGroupService.getUserGroupByGroupId(t_user_id);
        if (usergrouplist == null || usergrouplist.size() == 0) return null;


        List<PermissionViewData> data = new ArrayList<>();

        // 找到用户所在的组，找到组所具有的角色，找到角色具有的权限

        for (UserGroup usergroup : usergrouplist) {
            List<GroupRole> grouprolelist = groupRoleService.getListByGroupId(usergroup.getGroupId());
            for (GroupRole grouprole : grouprolelist) {
                List<RolePermission> rolepermissionlist = rolePermissionService.getByRoleId(grouprole.getRoleId());
                for (RolePermission rolepermission : rolepermissionlist) {
                    PermissionViewData view = getPermissionViewDataById(rolepermission.getPermissionId());
                    data.add(view);
                }
            }
        }

        if (data.size() == 0) return null;
        return data;

    }
}
