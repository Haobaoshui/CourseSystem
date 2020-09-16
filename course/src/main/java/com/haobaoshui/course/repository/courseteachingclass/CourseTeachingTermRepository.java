package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingTerm;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingTermRepository {


    /**
     * 增加
     */
    String add(int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks, Date week_begin);

    int deleteById(String id);

    int update(String id, int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks,
               Date week_begin);

    boolean isTermExist(int teaching_year_begin, int teaching_year_end, int teaching_term);

    CourseTeachingTerm getByID(String id);

    List<CourseTeachingTerm> getAll();


    /**
     * 总数
     */
    long getCount();


    /**
     * 分页查询
     */
    Page<CourseTeachingTerm> getPage(int pageNo, int pageSize);

}
