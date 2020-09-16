package com.haobaoshui.course.service.course.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.course.*;
import com.haobaoshui.course.repository.course.CourseRepository;
import com.haobaoshui.course.service.course.CoursePrecourseService;
import com.haobaoshui.course.service.course.CourseService;
import com.haobaoshui.course.service.course.CourseStyleService;
import com.haobaoshui.course.service.course.CourseTypeService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassService;
import com.haobaoshui.course.service.course.CourseDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程管理服务
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseTeachingClassService courseTeachingClassService;
    private final CourseStyleService courseStyleService;
    private final CourseTypeService courseTypeService;
    @Autowired
    CoursePrecourseService coursePrecourseService;
    @Autowired
    CourseDepartmentService courseDepartmentService;


    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,

                             CourseTeachingClassService courseTeachingClassService, CourseStyleService courseStyleService,
                             CourseTypeService courseTypeService) {

        this.courseRepository = courseRepository;

        this.courseTeachingClassService = courseTeachingClassService;
        this.courseStyleService = courseStyleService;
        this.courseTypeService = courseTypeService;

    }

    /**
     * 添加课程
     *
     * @param course
     * @return
     */
    @Override
    public String add(Course course, String[] t_department_ids, String[] precourseIds) {
        if (course == null) return null;
        if (course.getCourseStyleId() == null || course.getCourseTypeId() == null) return null;
        if (course.getName() == null || course.getNum() == null) return null;

        if (isExist(course.getNum())) return null;

        return add(course.getName(), course.getEnglishname(),
                course.getNum(), course.getClass_hours(), course.getExperiment_hours(),
                course.getCourseTypeId(), course.getCourseStyleId(), t_department_ids, precourseIds);
    }

    /**
     * 添加课程
     */
    @Override
    public String add(String name, String englishname, String num, int class_hours, int experiment_hours, String t_course_type_id,
                      String t_course_style_id, String[] t_department_ids, String[] precourseIds) {

        if (name == null || num == null || t_course_type_id == null || t_course_style_id == null) return null;

        if (isExist(num)) return null;


        String id = courseRepository.add(name, englishname, num, class_hours, experiment_hours, t_course_type_id, t_course_style_id);

        // 课程适用专业
        if (t_department_ids != null && t_department_ids.length > 0)
            for (String t_department_id : t_department_ids) courseDepartmentService.add(id, t_department_id);

        // 先修课程
        if (precourseIds != null && precourseIds.length > 0)
            for (String t_course_id_pre : precourseIds) coursePrecourseService.add(id, t_course_id_pre);

        return id;

    }

    /**
     * 更新课程
     */
    @Override
    public String update(String t_course_id, String name, String englishname, String num, int class_hours, int experiment_hours,
                         String t_course_type_id, String t_course_style_id, String[] t_department_ids, String[] precourseIds) {

        // 更新课程
        courseRepository.update(t_course_id, name, englishname, num, class_hours, experiment_hours, t_course_type_id,
                t_course_style_id);

        // 课程适用专业
        courseDepartmentService.deleteByCourseId(t_course_id);
        if (t_department_ids != null && t_department_ids.length > 0)
            for (String t_department_id : t_department_ids) courseDepartmentService.add(t_course_id, t_department_id);

        // 先修课程
        coursePrecourseService.deleteByCourseId(t_course_id);
        if (precourseIds != null && precourseIds.length > 0)
            for (String t_course_id_pre : precourseIds) coursePrecourseService.add(t_course_id, t_course_id_pre);

        return t_course_id;

    }

    @Override
    public boolean isExist(String num) {
        return courseRepository.getCountByCourseNum(num) > 0;

    }

    /**
     * 删除课程
     */
    @Override
    public int deleteById(HttpServletRequest request, String t_course_id) {


        //1.删除使用专业
        courseDepartmentService.deleteByCourseId(t_course_id);


        //2.删除先修课程
        coursePrecourseService.deleteByCourseId(t_course_id);


        //3.删除课程-教学班
        courseTeachingClassService.deleteByCourseId(request, t_course_id);

        //删除自己
        courseRepository.deleteById(t_course_id);

        return 1;
    }

    /**
     * 根据t_course_type_id删除课程
     */
    @Override
    public void deleteByCourseTypeId(HttpServletRequest request, String t_course_type_id) {
        List<Course> list = courseRepository.getByCourseTypeId(t_course_type_id);
        if (list == null) return;
        for (Course c : list) deleteById(request, c.getId());


    }

    /**
     * 根据t_course_style_id删除课程
     */
    @Override
    public void deleteByCourseStyleId(HttpServletRequest request, String t_course_style_id) {
        List<Course> list = courseRepository.getByCourseStyleId(t_course_style_id);
        if (list == null) return;
        for (Course c : list) deleteById(request, c.getId());


    }

    /**
     * 根据t_course_type_id、t_course_style_id删除课程
     */
    @Override
    public void deleteByCourseTypeIdAndStyleId(HttpServletRequest request, String t_course_type_id, String t_course_style_id) {
        List<Course> list = courseRepository.getByCourseTypeIdAndStyleId(t_course_type_id, t_course_style_id);
        if (list == null) return;
        for (Course c : list) deleteById(request, c.getId());


    }

    @Override
    public Course getById(String t_course_id) {
        return courseRepository.getCourseById(t_course_id);
    }


    @Override
    public CourseViewData getCourseViewDataById(String t_course_id) {
        Course course = getById(t_course_id);
        if (course == null) return null;
        CourseViewData courseViewData = new CourseViewData();
        courseViewData.setCourse(course);

        CourseType courseType = courseTypeService.getByID(course.getCourseTypeId());
        courseViewData.setCourseType(courseType);


        CourseStyle courseStyle = courseStyleService.getByID(course.getCourseTypeId());
        courseViewData.setCourseStyle(courseStyle);

        List<CourseDepartmentViewData> courseDepartmentList = courseDepartmentService.getCourseDepartmentViewDataByCourseId(course.getId());
        courseViewData.setListCourseDepartment(courseDepartmentList);

        List<CoursePrecourseViewData> coursePrecourseViewDataList = coursePrecourseService.getPreCourseViewData(course.getId());
        courseViewData.setListCoursePrecourse(coursePrecourseViewDataList);


        return courseViewData;

    }

    /**
     * 得到
     */
    @Override
    public Page<CourseViewData> getPage(int pageNo, int pageSize) {

        Page<Course> coursePage = courseRepository.getPage(pageNo, pageSize);
        if (coursePage == null) return null;

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseViewData> list = new ArrayList<>();


        for (Course course : coursePage.getResult()) {
            CourseViewData courseViewData = getCourseViewDataByCourseId(course.getId());
            if (courseViewData != null) list.add(courseViewData);
        }


        return new Page<>(startIndex, coursePage.getTotalCount(), pageSize, list);


    }


    private CourseViewData getCourseViewDataByCourseId(String t_course_id) {
        Course course = courseRepository.getCourseById(t_course_id);
        if (course == null) return null;

        CourseViewData courseViewData = new CourseViewData();
        courseViewData.setCourse(course);

        CourseStyle courseStyle = courseStyleService.getByID(course.getCourseStyleId());
        courseViewData.setCourseStyle(courseStyle);

        CourseType courseType = courseTypeService.getByID(course.getCourseTypeId());
        courseViewData.setCourseType(courseType);

        List<CoursePrecourseViewData> listpre = coursePrecourseService.getPreCourseViewData(t_course_id);
        courseViewData.setListCoursePrecourse(listpre);

        List<CourseDepartmentViewData> listdept = courseDepartmentService.getCourseDepartmentViewDataByCourseId(t_course_id);
        courseViewData.setListCourseDepartment(listdept);


        return courseViewData;

    }

}
