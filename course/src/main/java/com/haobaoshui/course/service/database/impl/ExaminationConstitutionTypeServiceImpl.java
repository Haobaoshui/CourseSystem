package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ExaminationConstitutionType;
import com.haobaoshui.course.repository.database.ExaminationConstitutionTypeRepository;
import com.haobaoshui.course.service.database.ExaminationConstitutionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExaminationConstitutionTypeServiceImpl implements ExaminationConstitutionTypeService {


    private final ExaminationConstitutionTypeRepository examinationConstitutionTypeRepository;

    @Autowired
    public ExaminationConstitutionTypeServiceImpl(ExaminationConstitutionTypeRepository examinationConstitutionTypeRepository) {
        this.examinationConstitutionTypeRepository = examinationConstitutionTypeRepository;
    }

    @Override
    public String add(ExaminationConstitutionType examinationConstitutionType) {
        return examinationConstitutionTypeRepository.add(examinationConstitutionType);
    }

    @Override
    public String add(String name, String note) {
        return examinationConstitutionTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return examinationConstitutionTypeRepository.deleteById(id);
    }

    @Override
    public int delete(ExaminationConstitutionType examinationConstitutionType) {
        if (examinationConstitutionType == null) return 0;
        return deleteById(examinationConstitutionType.getId());
    }

    @Override
    public int deleteAll() {
        return examinationConstitutionTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return examinationConstitutionTypeRepository.update(id, name, note);
    }

    @Override
    public int update(ExaminationConstitutionType examinationConstitutionType) {
        return examinationConstitutionTypeRepository.update(examinationConstitutionType);
    }

    @Override
    public int getCount() {
        return examinationConstitutionTypeRepository.getCount();
    }

    @Override
    public List<ExaminationConstitutionType> getAllByLikeName(String name) {
        return examinationConstitutionTypeRepository.getAllByLikeName(name);
    }

    @Override
    public ExaminationConstitutionType getByName(String name) {
        return examinationConstitutionTypeRepository.getByName(name);
    }

    @Override
    public ExaminationConstitutionType getByID(String id) {
        return examinationConstitutionTypeRepository.getByID(id);
    }

    @Override
    public List<ExaminationConstitutionType> getAll() {
        return examinationConstitutionTypeRepository.getAll();
    }

    @Override
    public Page<ExaminationConstitutionType> getPage(int pageNo, int pageSize) {
        return examinationConstitutionTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ExaminationConstitutionType> getAllPage() {
        return examinationConstitutionTypeRepository.getAllPage();
    }

    @Override
    public Page<ExaminationConstitutionType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return examinationConstitutionTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }


}
