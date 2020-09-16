package com.haobaoshui.course.service.course.impl;

import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.course.CourseDepartment;
import com.haobaoshui.course.model.course.CourseDepartmentViewData;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.repository.course.CourseDepartmentRepository;
import com.haobaoshui.course.service.course.CourseService;
import com.haobaoshui.course.service.course.CourseDepartmentService;
import com.haobaoshui.course.service.organization.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseDepartmentServiceImpl implements CourseDepartmentService {

    private final CourseDepartmentRepository courseDepartmentRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    public CourseDepartmentServiceImpl(CourseDepartmentRepository courseDepartmentRepository) {

        this.courseDepartmentRepository = courseDepartmentRepository;

    }


    @Override
    public String add(CourseDepartment courseDepartment) {
        if (courseDepartment == null || courseDepartment.getCourseId() == null || courseDepartment.getDepartmentId() == null)
            return null;
        return add(courseDepartment.getCourseId(), courseDepartment.getDepartmentId());
    }

    @Override
    public String add(String t_course_id, String t_department_id) {
        return courseDepartmentRepository.add(t_course_id, t_department_id);
    }

    @Override
    public int deleteById(String id) {
        return courseDepartmentRepository.deleteById(id);
    }

    @Override
    public int deleteByCourseId(String t_course_id) {
        return courseDepartmentRepository.deleteByCourseId(t_course_id);
    }

    @Override
    public int deleteByDepartmentId(String t_department_id) {
        return courseDepartmentRepository.deleteByDepartmentId(t_department_id);
    }

    @Override
    public int update(String id, String t_course_id, String t_department_id) {
        return courseDepartmentRepository.update(id, t_course_id, t_department_id);
    }

    @Override
    public long getCount(String t_course_id) {
        return courseDepartmentRepository.getCount(t_course_id);
    }

    @Override
    public CourseDepartment getById(String id) {
        return courseDepartmentRepository.getById(id);
    }

    @Override
    public List<CourseDepartment> getByCourseId(String t_course_id) {
        return courseDepartmentRepository.getByCourseId(t_course_id);
    }

    @Override
    public List<CourseDepartment> getByDepartmentId(String t_department_id) {
        return courseDepartmentRepository.getByDepartmentId(t_department_id);
    }

    @Override
    public CourseDepartmentViewData getCourseDepartmentViewDataById(String id) {
        CourseDepartmentViewData courseDepartmentViewData = new CourseDepartmentViewData();
        CourseDepartment cd = getById(id);
        courseDepartmentViewData.setCourseDepartment(cd);

        Course course = courseService.getById(cd.getCourseId());
        courseDepartmentViewData.setCourse(course);

        Department department = departmentService.getByID(cd.getDepartmentId());
        courseDepartmentViewData.setDepartment(department);

        return courseDepartmentViewData;
    }

    @Override
    public List<CourseDepartmentViewData> getCourseDepartmentViewDataByCourseId(String t_course_id) {

        List<CourseDepartment> courseDepartmentList = getByCourseId(t_course_id);
        if (courseDepartmentList == null || courseDepartmentList.size() == 0) return null;

        List<CourseDepartmentViewData> list = new ArrayList<>();
        for (CourseDepartment courseDepartment : courseDepartmentList) {
            CourseDepartmentViewData cd = getCourseDepartmentViewDataById(courseDepartment.getId());
            list.add(cd);
        }


        return list;
    }
}
