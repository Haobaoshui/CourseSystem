package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.TeachingType;
import com.haobaoshui.course.repository.courseteachingclass.TeachingTypeRepository;
import com.haobaoshui.course.service.courseteachingclass.TeachingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeachingTypeServiceImpl implements TeachingTypeService {


    private final TeachingTypeRepository teachingTypeRepository;

    @Autowired
    public TeachingTypeServiceImpl(TeachingTypeRepository teachingTypeRepository) {
        this.teachingTypeRepository = teachingTypeRepository;
    }

    @Override
    public String add(TeachingType teachingType) {
        return teachingTypeRepository.add(teachingType);
    }

    @Override
    public String add(String name, String note) {
        return teachingTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return teachingTypeRepository.deleteById(id);
    }

    @Override
    public int delete(TeachingType teachingType) {
        if (teachingType == null) return 0;
        return deleteById(teachingType.getId());
    }

    @Override
    public int deleteAll() {
        return teachingTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return teachingTypeRepository.update(id, name, note);
    }

    @Override
    public int update(TeachingType teachingType) {
        return teachingTypeRepository.update(teachingType);
    }

    @Override
    public int getCount() {
        return teachingTypeRepository.getCount();
    }

    @Override
    public List<TeachingType> getAllByLikeName(String name) {
        return teachingTypeRepository.getAllByLikeName(name);
    }

    @Override
    public TeachingType getByName(String name) {
        return teachingTypeRepository.getByName(name);
    }

    @Override
    public TeachingType getByID(String id) {
        return teachingTypeRepository.getByID(id);
    }

    @Override
    public List<TeachingType> getAll() {
        return teachingTypeRepository.getAll();
    }

    @Override
    public Page<TeachingType> getPage(int pageNo, int pageSize) {
        return teachingTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<TeachingType> getAllPage() {
        return teachingTypeRepository.getAllPage();
    }

    @Override
    public Page<TeachingType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return teachingTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }


}
