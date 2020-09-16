package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Role;
import com.haobaoshui.course.repository.authority.RoleRepository;
import com.haobaoshui.course.service.authority.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String add(Role role) {
        return roleRepository.add(role);
    }

    @Override
    public String add(String name, String note) {
        return roleRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return roleRepository.deleteById(id);
    }

    @Override
    public int delete(Role role) {
        if (role == null) return 0;
        return deleteById(role.getId());
    }

    @Override
    public int deleteAll() {
        return roleRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return roleRepository.update(id, name, note);
    }

    @Override
    public int update(Role role) {
        return roleRepository.update(role);
    }

    @Override
    public int getCount() {
        return roleRepository.getCount();
    }

    @Override
    public List<Role> getAllByLikeName(String name) {
        return roleRepository.getAllByLikeName(name);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(name);
    }

    @Override
    public Role getByID(String id) {
        return roleRepository.getByID(id);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.getAll();
    }

    @Override
    public Page<Role> getPage(int pageNo, int pageSize) {
        return roleRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<Role> getAllPage() {
        return roleRepository.getAllPage();
    }

    @Override
    public Page<Role> getPageByLikeName(String name, int pageNo, int pageSize) {
        return roleRepository.getPageByLikeName(name, pageNo, pageSize);
    }
}
