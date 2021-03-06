package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClass;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.user.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("teacher/v1")
public class TeacherController extends BaseController {


    private final TeacherService teacherService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody School school) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_school_id) {
        return 0;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody School school) {

        return 0;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseTeachingClass> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;


    }

}
