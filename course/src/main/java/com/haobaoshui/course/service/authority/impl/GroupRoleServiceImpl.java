package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Group;
import com.haobaoshui.course.model.authority.GroupRole;
import com.haobaoshui.course.model.authority.GroupRoleViewData;
import com.haobaoshui.course.model.authority.Role;
import com.haobaoshui.course.repository.authority.GroupRoleRepository;
import com.haobaoshui.course.service.authority.GroupRoleService;
import com.haobaoshui.course.service.authority.GroupService;
import com.haobaoshui.course.service.authority.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GroupRoleServiceImpl implements GroupRoleService {


    @Autowired
    GroupService groupService;
    @Autowired
    RoleService roleService;
    private GroupRoleRepository groupRoleRepository;

    @Autowired
    public GroupRoleServiceImpl(GroupRoleRepository attendanceModeRepository) {
        groupRoleRepository = groupRoleRepository;
    }

    @Override
    public void add(String t_group_id, String t_role_id) {
        groupRoleRepository.add(t_group_id, t_role_id);
    }

    @Override
    public void deleteById(String id) {
        groupRoleRepository.deleteById(id);
    }

    /**
     * 删除组
     */
    @Override
    public void deleteByGroupId(String t_group_id) {
        groupRoleRepository.deleteById(t_group_id);
    }

    @Override
    public GroupRole getById(String id) {
        return groupRoleRepository.getById(id);
    }

    @Override
    public GroupRoleViewData getGroupRoleViewDataById(String id) {
        GroupRole groupRole = getById(id);
        if (groupRole == null) return null;

        GroupRoleViewData groupRoleViewData = new GroupRoleViewData();
        groupRoleViewData.setGrouprole(groupRole);

        Group group = groupService.getByID(groupRole.getGroupId());
        Role role = roleService.getByID(groupRole.getRoleId());

        groupRoleViewData.setGroup(group);
        groupRoleViewData.setRole(role);

        return null;
    }

    @Override
    public List<GroupRole> getListByGroupId(String t_group_id) {
        return groupRoleRepository.getByGroupId(t_group_id);
    }

    @Override
    public List<GroupRole> getListByRoleId(String t_role_id) {
        return groupRoleRepository.getByRoleId(t_role_id);
    }

    @Override
    public Page<GroupRoleViewData> getPageByGroupId(String t_group_id, int pageNo, int pageSize) {
        Page<GroupRole> groupRolePage = groupRoleRepository.getPageByGroupId(t_group_id, pageNo, pageSize);

        if (groupRolePage == null) return null;
        List<GroupRoleViewData> data = new ArrayList<>();

        for (GroupRole groupRole : groupRolePage.getResult()) {
            GroupRoleViewData groupRoleViewData = getGroupRoleViewDataById(groupRole.getId());
            if (groupRoleViewData != null) data.add(groupRoleViewData);
        }

        return new Page<>(groupRolePage.getStart(),
                groupRolePage.getTotalCount(), pageSize, data);
    }
}
