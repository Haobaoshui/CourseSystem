package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.RolePermission;
import com.haobaoshui.course.model.authority.RolePermissionViewData;
import com.haobaoshui.course.repository.authority.RolePermissionRepository;
import com.haobaoshui.course.service.authority.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RolePermissionServiceImpl implements RolePermissionService {


    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public String add(String t_role_id, String t_permission_id) {
        return rolePermissionRepository.add(t_role_id, t_permission_id);
    }

    @Override
    public List<RolePermission> getByRoleId(String t_role_id) {
        return rolePermissionRepository.getRolePermissionByRoleId(t_role_id);
    }

    @Override
    public Page<RolePermissionViewData> getPageRolePermissionViewData(String roleId,
																	  int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Page<RolePermission> getPage(
			int pageNo, int pageSize) {
        return rolePermissionRepository.getPage(pageNo, pageSize);
    }

    /*删除
     * */
    @Override
    public void deleteByRoleId(String t_role_id) {
		rolePermissionRepository.deleteByRoleId(t_role_id);
    }


}
