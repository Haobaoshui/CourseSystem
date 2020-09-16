package com.haobaoshui.course.service.course.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.course.CourseType;
import com.haobaoshui.course.repository.course.CourseTypeRepository;
import com.haobaoshui.course.service.course.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    private final CourseTypeRepository courseTypeRepository;

    @Autowired
    public CourseTypeServiceImpl(CourseTypeRepository courseTypeRepository) {
        this.courseTypeRepository = courseTypeRepository;
    }

    @Override
    public String add(CourseType courseType) {
        return courseTypeRepository.add(courseType);
    }

    @Override
    public String add(String name, String note) {
        return courseTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return courseTypeRepository.deleteById(id);
    }

    @Override
    public int delete(CourseType courseType) {
        if (courseType == null) return 0;
        return deleteById(courseType.getId());
    }

    @Override
    public int deleteAll() {
        return courseTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return courseTypeRepository.update(id, name, note);
    }

    @Override
    public int update(CourseType courseType) {
        return courseTypeRepository.update(courseType);
    }

    @Override
    public int getCount() {
        return courseTypeRepository.getCount();
    }

    @Override
    public List<CourseType> getAllByLikeName(String name) {
        return courseTypeRepository.getAllByLikeName(name);
    }

    @Override
    public CourseType getByName(String name) {
        return courseTypeRepository.getByName(name);
    }

    @Override
    public CourseType getByID(String id) {
        return courseTypeRepository.getByID(id);
    }

    @Override
    public List<CourseType> getAll() {
        return courseTypeRepository.getAll();
    }

    @Override
    public Page<CourseType> getPage(int pageNo, int pageSize) {
        return courseTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<CourseType> getAllPage() {
        return courseTypeRepository.getAllPage();
    }

    @Override
    public Page<CourseType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return courseTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }


}
