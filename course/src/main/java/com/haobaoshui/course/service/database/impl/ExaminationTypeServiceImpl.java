package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ExaminationType;
import com.haobaoshui.course.repository.database.ExaminationTypeRepository;
import com.haobaoshui.course.service.database.ExaminationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExaminationTypeServiceImpl implements ExaminationTypeService {


    private final ExaminationTypeRepository examinationTypeRepository;

    @Autowired
    public ExaminationTypeServiceImpl(ExaminationTypeRepository examinationTypeRepository) {
        this.examinationTypeRepository = examinationTypeRepository;
    }

    @Override
    public String add(ExaminationType examinationType) {
        return examinationTypeRepository.add(examinationType);
    }

    @Override
    public String add(String name, String note) {
        return examinationTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return examinationTypeRepository.deleteById(id);
    }

    @Override
    public int delete(ExaminationType examinationType) {
        if (examinationType == null) return 0;
        return deleteById(examinationType.getId());
    }

    @Override
    public int deleteAll() {
        return examinationTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return examinationTypeRepository.update(id, name, note);
    }

    @Override
    public int update(ExaminationType examinationType) {
        return examinationTypeRepository.update(examinationType);
    }

    @Override
    public int getCount() {
        return examinationTypeRepository.getCount();
    }

    @Override
    public List<ExaminationType> getAllByLikeName(String name) {
        return examinationTypeRepository.getAllByLikeName(name);
    }

    @Override
    public ExaminationType getByName(String name) {
        return examinationTypeRepository.getByName(name);
    }

    @Override
    public ExaminationType getByID(String id) {
        return examinationTypeRepository.getByID(id);
    }

    @Override
    public List<ExaminationType> getAll() {
        return examinationTypeRepository.getAll();
    }

    @Override
    public Page<ExaminationType> getPage(int pageNo, int pageSize) {
        return examinationTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ExaminationType> getAllPage() {
        return examinationTypeRepository.getAllPage();
    }

    @Override
    public Page<ExaminationType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return examinationTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
