package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClass;
import com.haobaoshui.course.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("course/v1")
public class CourseController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CourseService courseService;


    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_course_id) {

        return courseService.deleteById(request, t_course_id);


    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public void select(@RequestBody String departmentname) {


    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateCourse(@RequestBody String t_course_id) {
        // String t_school_id = request.getParameter("t_school_id");
        String course_num = request.getParameter("course_num");
        String course_name = request.getParameter("course_name");
        String course_english_name = request.getParameter("course_english_name");
        String t_course_style_id = request.getParameter("coursestyleID");
        String t_course_type_id = request.getParameter("coursetypeID");
        String class_hours = request.getParameter("class_hours");
        String experiment_hours = request.getParameter("experiment_hours");
        String[] t_department_id = request.getParameterValues("t_department_id");
        String[] precourseId = request.getParameterValues("precourseId");


        //     courseService.update(t_course_id, course_name, course_english_name, course_num, n_class_hours, n_experiment_hours, t_course_type_id,
        //             t_course_style_id, t_department_id, precourseId);


    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseTeachingClass> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;

    }


}
