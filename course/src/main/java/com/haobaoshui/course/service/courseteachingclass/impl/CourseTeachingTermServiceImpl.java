package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingTerm;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingTermRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class CourseTeachingTermServiceImpl implements CourseTeachingTermService {

    private final CourseTeachingTermRepository courseTeachingTermRepository;

    @Autowired
    public CourseTeachingTermServiceImpl(CourseTeachingTermRepository courseTeachingTermRepository) {
        this.courseTeachingTermRepository = courseTeachingTermRepository;
    }


    @Override
    public String add(CourseTeachingTerm courseTeachingTerm) {
        return null;
    }

    @Override
    public String add(int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks, Date week_begin) {
        return courseTeachingTermRepository.add(teaching_year_begin, teaching_year_end, teaching_term, weeks, week_begin);
    }

    @Override
    public int deleteById(String t_course_teaching_term_id) {
        return courseTeachingTermRepository.deleteById(t_course_teaching_term_id);
    }

    @Override
    public int update(String id, int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks, Date week_begin) {
        return courseTeachingTermRepository.update(id, teaching_year_begin, teaching_year_end, teaching_term, weeks, week_begin);
    }

    @Override
    public int update(CourseTeachingTerm courseTeachingTerm) {
        if (courseTeachingTerm != null)
			return update(courseTeachingTerm.getId(), courseTeachingTerm.getTeachingYearBegin(), courseTeachingTerm.getTeachingYearEnd(), courseTeachingTerm.getTeachingTerm(), courseTeachingTerm.getWeeks(), courseTeachingTerm.getWeekBegin());
        return 0;
    }

    @Override
    public boolean isTermExist(int teaching_year_begin, int teaching_year_end, int teaching_term) {
        return courseTeachingTermRepository.isTermExist(teaching_year_begin, teaching_year_end, teaching_term);
    }

    @Override
    public CourseTeachingTerm getById(String id) {
        return courseTeachingTermRepository.getByID(id);
    }

    @Override
    public Page<CourseTeachingTerm> getPage(int pageNo, int pageSize) {
        return courseTeachingTermRepository.getPage(pageNo, pageSize);
    }

    @Override
    public List<CourseTeachingTerm> getAll() {
        return courseTeachingTermRepository.getAll();
    }
}
