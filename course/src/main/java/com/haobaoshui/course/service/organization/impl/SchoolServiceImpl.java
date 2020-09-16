package com.haobaoshui.course.service.organization.impl;


import com.haobaoshui.course.configure.CommonConstant;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.repository.organization.SchoolDepartmentRepository;
import com.haobaoshui.course.repository.organization.SchoolRepository;
import com.haobaoshui.course.service.organization.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolDepartmentRepository schoolDepartmentRepository;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolDepartmentRepository schoolDepartmentRepository) {
        this.schoolRepository = schoolRepository;
        this.schoolDepartmentRepository = schoolDepartmentRepository;
    }

    @Override
    public String add(School school) {
        return schoolRepository.add(school);
    }

    @Override
    public String add(String name, String note) {
        return schoolRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return schoolRepository.deleteById(id);
    }

    @Override
    public int delete(School school) {
        if (school == null) return 0;
        return deleteById(school.getId());
    }

    @Override
    public int deleteAll() {
        return schoolRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return schoolRepository.update(id, name, note);
    }

    @Override
    public int update(School school) {
        return schoolRepository.update(school);
    }

    @Override
    public int getCount() {
        return schoolRepository.getCount();
    }

    @Override
    public List<School> getAllByLikeName(String name) {
        return schoolRepository.getAllByLikeName(name);
    }

    @Override
    public School getByName(String name) {
        return schoolRepository.getByName(name);
    }

    @Override
    public School getByID(String id) {
        return schoolRepository.getByID(id);
    }

    @Override
    public School getByDepartmentId(String t_department_id) {
        String t_school_id = schoolDepartmentRepository.getSchoolIdByDepartmentId(t_department_id);
        return getByID(t_school_id);
    }

    @Override
    public List<School> getAll() {
        return schoolRepository.getAll();
    }

    @Override
    public Page<School> getPage(int pageNo, int pageSize) {
        return schoolRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<School> getAllPage() {
        return schoolRepository.getAllPage();
    }

    @Override
    public Page<School> getPageByLikeName(String name, int pageNo, int pageSize) {
        return schoolRepository.getPageByLikeName(name, pageNo, pageSize);
    }

    @Override
    public String getDefaultSchoolId() {
        School school = getByName(CommonConstant.DEFAULT_SCHOOL_NAME);
        if (school == null) {
            school = new School();
            school.setName(CommonConstant.DEFAULT_SCHOOL_NAME);
            return add(school);
        }
        return school.getId();
    }
}
