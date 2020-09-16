package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingTerm;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("courseteachingterm/v1")
public class CourseTeachingTermController extends BaseController {

    private final CourseTeachingTermService courseTeachingTermService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public CourseTeachingTermController(CourseTeachingTermService courseTeachingTermService) {

        this.courseTeachingTermService = courseTeachingTermService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody CourseTeachingTerm courseTeachingTerm) {

        return courseTeachingTermService.add(courseTeachingTerm);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_id) {
        return courseTeachingTermService.deleteById(t_id);

    }


    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        return false;

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody CourseTeachingTerm courseTeachingTerm) {


        return courseTeachingTermService.update(courseTeachingTerm);
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseTeachingTerm> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;


    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CourseTeachingTerm> getAllList() {
        return null;
    }

}
