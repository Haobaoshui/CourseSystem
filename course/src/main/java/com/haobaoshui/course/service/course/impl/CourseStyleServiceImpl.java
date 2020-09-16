package com.haobaoshui.course.service.course.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.course.CourseStyle;
import com.haobaoshui.course.repository.course.CourseStyleRepository;
import com.haobaoshui.course.service.course.CourseStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseStyleServiceImpl implements CourseStyleService {


    private final CourseStyleRepository courseStyleRepository;

    @Autowired
    public CourseStyleServiceImpl(CourseStyleRepository courseStyleRepository) {
        this.courseStyleRepository = courseStyleRepository;
    }


    @Override
    public String add(CourseStyle courseStyle) {
        return courseStyleRepository.add(courseStyle);
    }

    @Override
    public String add(String name, String note) {
        return courseStyleRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return courseStyleRepository.deleteById(id);
    }

    @Override
    public int delete(CourseStyle courseStyle) {
        if (courseStyle == null) return 0;
        return deleteById(courseStyle.getId());
    }

    @Override
    public int deleteAll() {
        return courseStyleRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return courseStyleRepository.update(id, name, note);
    }

    @Override
    public int update(CourseStyle courseStyle) {
        return courseStyleRepository.update(courseStyle);
    }

    @Override
    public int getCount() {
        return courseStyleRepository.getCount();
    }

    @Override
    public List<CourseStyle> getAllByLikeName(String name) {
        return courseStyleRepository.getAllByLikeName(name);
    }

    @Override
    public CourseStyle getByName(String name) {
        return courseStyleRepository.getByName(name);
    }

    @Override
    public CourseStyle getByID(String id) {
        return courseStyleRepository.getByID(id);
    }

    @Override
    public List<CourseStyle> getAll() {
        return courseStyleRepository.getAll();
    }

    @Override
    public Page<CourseStyle> getPage(int pageNo, int pageSize) {
        return courseStyleRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<CourseStyle> getAllPage() {
        return courseStyleRepository.getAllPage();
    }

    @Override
    public Page<CourseStyle> getPageByLikeName(String name, int pageNo, int pageSize) {
        return courseStyleRepository.getPageByLikeName(name, pageNo, pageSize);
    }
}
