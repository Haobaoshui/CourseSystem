package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingTerm;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingTermService {


     String add(CourseTeachingTerm courseTeachingTerm);

     String add(int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks, Date week_begin);

     int deleteById(String t_course_teaching_term_id);

     int update(String id, int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks, Date week_begin);

     int update(CourseTeachingTerm courseTeachingTerm);

     boolean isTermExist(int teaching_year_begin, int teaching_year_end, int teaching_term);

     CourseTeachingTerm getById(String id);

     Page<CourseTeachingTerm> getPage(int pageNo, int pageSize);

     List<CourseTeachingTerm> getAll();
}
