package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.PermissionOperator;
import com.haobaoshui.course.repository.authority.PermissionOperatorRepository;
import com.haobaoshui.course.service.authority.PermissionOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionOperatorServiceImpl implements PermissionOperatorService {


    private final PermissionOperatorRepository permissionOperatorRepository;

    @Autowired
    public PermissionOperatorServiceImpl(PermissionOperatorRepository permissionOperatorRepository) {
        this.permissionOperatorRepository = permissionOperatorRepository;
    }

    @Override
    public String add(PermissionOperator permissionOperator) {
        return permissionOperatorRepository.add(permissionOperator);
    }

    @Override
    public String add(String name, String note) {
        return permissionOperatorRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return permissionOperatorRepository.deleteById(id);
    }

    @Override
    public int delete(PermissionOperator permissionOperator) {
        if (permissionOperator == null) return 0;
        return deleteById(permissionOperator.getId());
    }

    @Override
    public int deleteAll() {
        return permissionOperatorRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return permissionOperatorRepository.update(id, name, note);
    }

    @Override
    public int update(PermissionOperator permissionOperator) {
        return permissionOperatorRepository.update(permissionOperator);
    }

    @Override
    public int getCount() {
        return permissionOperatorRepository.getCount();
    }

    @Override
    public List<PermissionOperator> getAllByLikeName(String name) {
        return permissionOperatorRepository.getAllByLikeName(name);
    }

    @Override
    public PermissionOperator getByName(String name) {
        return permissionOperatorRepository.getByName(name);
    }

    @Override
    public PermissionOperator getByID(String id) {
        return permissionOperatorRepository.getByID(id);
    }

    @Override
    public List<PermissionOperator> getAll() {
        return permissionOperatorRepository.getAll();
    }

    @Override
    public Page<PermissionOperator> getPage(int pageNo, int pageSize) {
        return permissionOperatorRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<PermissionOperator> getAllPage() {
        return permissionOperatorRepository.getAllPage();
    }

    @Override
    public Page<PermissionOperator> getPageByLikeName(String name, int pageNo, int pageSize) {
        return permissionOperatorRepository.getPageByLikeName(name, pageNo, pageSize);
    }


}
