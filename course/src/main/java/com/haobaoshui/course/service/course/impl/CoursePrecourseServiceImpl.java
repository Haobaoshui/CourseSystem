package com.haobaoshui.course.service.course.impl;

import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.course.CoursePrecourse;
import com.haobaoshui.course.model.course.CoursePrecourseViewData;
import com.haobaoshui.course.repository.course.CoursePrecourseRepository;
import com.haobaoshui.course.service.course.CoursePrecourseService;
import com.haobaoshui.course.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoursePrecourseServiceImpl implements CoursePrecourseService {
    private final CoursePrecourseRepository coursePrecourseRepository;
    @Autowired
    CourseService courseService;

    @Autowired
    public CoursePrecourseServiceImpl(CoursePrecourseRepository coursePrecourseRepository) {
        this.coursePrecourseRepository = coursePrecourseRepository;

    }


    @Override
    public String add(CoursePrecourse coursePrecourse) {
        return coursePrecourseRepository.add(coursePrecourse);
    }

    @Override
    public String add(String t_course_id_this, String t_course_id_pre) {
        return coursePrecourseRepository.add(t_course_id_this, t_course_id_pre);
    }

    @Override
    public int deleteById(String id) {
        return coursePrecourseRepository.deleteById(id);
    }

    @Override
    public int deleteByCourseId(String t_course_id) {
        return coursePrecourseRepository.deleteByCourseId(t_course_id);
    }

    @Override
    public int update(CoursePrecourse coursePrecourse) {
        return coursePrecourseRepository.update(coursePrecourse);
    }

    @Override
    public int update(String id, String t_course_id_this, String t_course_id_pre) {
        return coursePrecourseRepository.update(id, t_course_id_this, t_course_id_pre);
    }

    @Override
    public CoursePrecourse getById(String id) {
        return coursePrecourseRepository.getById(id);
    }

    @Override
    public List<CoursePrecourse> getPreCourse(String t_course_id) {
        return coursePrecourseRepository.getPreCourse(t_course_id);
    }

    /**
     * 得到指定课程的先修课程
     *
     * @param t_course_id
     * @return
     */
    @Override
    public List<CoursePrecourseViewData> getPreCourseViewData(String t_course_id) {

        List<CoursePrecourse> coursePrecourseList = getPreCourse(t_course_id);
        if (coursePrecourseList == null || coursePrecourseList.size() == 0) return null;

        List<CoursePrecourseViewData> list = new ArrayList<>();

        for (CoursePrecourse coursePrecourse : coursePrecourseList) {
            Course coursethis = courseService.getById(coursePrecourse.getCourseIdThis());
            Course coursepre = courseService.getById(coursePrecourse.getCourseIdPre());

            CoursePrecourseViewData cpvd = new CoursePrecourseViewData();
            cpvd.setCourseprecourse(coursePrecourse);
            cpvd.setCourseThis(coursethis);
            cpvd.setCoursePre(coursepre);
            list.add(cpvd);
        }


        return list;
    }
}
