package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("coursehomeworksubmit/v1")
public class CourseTeachingClassHomeworkSubmitController extends BaseController {

    private final CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public CourseTeachingClassHomeworkSubmitController(CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService) {
        this.courseTeachingClassHomeworkSubmitService = courseTeachingClassHomeworkSubmitService;

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody School school) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_school_id) {

        return 0;
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<School> select(@RequestBody String schoolname) {

        return null;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody School school) {

        return 0;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<School> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;


    }


}
