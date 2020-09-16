package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.KnowledgepointType;
import com.haobaoshui.course.repository.database.KnowledgepointTypeRepository;
import com.haobaoshui.course.service.database.KnowledgepointTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KnowledgepointTypeServiceImpl implements KnowledgepointTypeService {


    private final KnowledgepointTypeRepository knowledgepointTypeRepository;

    @Autowired
    public KnowledgepointTypeServiceImpl(KnowledgepointTypeRepository knowledgepointTypeRepository) {
        this.knowledgepointTypeRepository = knowledgepointTypeRepository;
    }

    @Override
    public String add(KnowledgepointType knowledgepointType) {
        return knowledgepointTypeRepository.add(knowledgepointType);
    }

    @Override
    public String add(String name, String note) {
        return knowledgepointTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return knowledgepointTypeRepository.deleteById(id);
    }

    @Override
    public int delete(KnowledgepointType knowledgepointType) {
        if (knowledgepointType == null) return 0;
        return deleteById(knowledgepointType.getId());
    }

    @Override
    public int deleteAll() {
        return knowledgepointTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return knowledgepointTypeRepository.update(id, name, note);
    }

    @Override
    public int update(KnowledgepointType knowledgepointType) {
        return knowledgepointTypeRepository.update(knowledgepointType);
    }

    @Override
    public int getCount() {
        return knowledgepointTypeRepository.getCount();
    }

    @Override
    public List<KnowledgepointType> getAllByLikeName(String name) {
        return knowledgepointTypeRepository.getAllByLikeName(name);
    }

    @Override
    public KnowledgepointType getByName(String name) {
        return knowledgepointTypeRepository.getByName(name);
    }

    @Override
    public KnowledgepointType getByID(String id) {
        return knowledgepointTypeRepository.getByID(id);
    }

    @Override
    public List<KnowledgepointType> getAll() {
        return knowledgepointTypeRepository.getAll();
    }

    @Override
    public Page<KnowledgepointType> getPage(int pageNo, int pageSize) {
        return knowledgepointTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<KnowledgepointType> getAllPage() {
        return knowledgepointTypeRepository.getAllPage();
    }

    @Override
    public Page<KnowledgepointType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return knowledgepointTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }


}
