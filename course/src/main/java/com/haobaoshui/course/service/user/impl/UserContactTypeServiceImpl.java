package com.haobaoshui.course.service.user.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.user.UserContactType;
import com.haobaoshui.course.repository.user.UserContactTypeRepository;
import com.haobaoshui.course.service.user.UserContactTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserContactTypeServiceImpl implements UserContactTypeService {

    private final UserContactTypeRepository userContactTypeRepository;

    @Autowired
    public UserContactTypeServiceImpl(UserContactTypeRepository userContactTypeRepository) {
        this.userContactTypeRepository = userContactTypeRepository;
    }


    @Override
    public String add(UserContactType userContactType) {
        return userContactTypeRepository.add(userContactType);
    }

    @Override
    public String add(String name, String note) {
        return userContactTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return userContactTypeRepository.deleteById(id);
    }

    @Override
    public int delete(UserContactType userContactType) {
        if (userContactType == null) return 0;
        return deleteById(userContactType.getId());
    }

    @Override
    public int deleteAll() {
        return userContactTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return userContactTypeRepository.update(id, name, note);
    }

    @Override
    public int update(UserContactType userContactType) {
        return userContactTypeRepository.update(userContactType);
    }

    @Override
    public int getCount() {
        return userContactTypeRepository.getCount();
    }

    @Override
    public List<UserContactType> getAllByLikeName(String name) {
        return userContactTypeRepository.getAllByLikeName(name);
    }

    @Override
    public UserContactType getByName(String name) {
        return userContactTypeRepository.getByName(name);
    }

    @Override
    public UserContactType getByID(String id) {
        return userContactTypeRepository.getByID(id);
    }

    @Override
    public List<UserContactType> getAll() {
        return userContactTypeRepository.getAll();
    }

    @Override
    public Page<UserContactType> getPage(int pageNo, int pageSize) {
        return userContactTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<UserContactType> getAllPage() {
        return userContactTypeRepository.getAllPage();
    }

    @Override
    public Page<UserContactType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return userContactTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
