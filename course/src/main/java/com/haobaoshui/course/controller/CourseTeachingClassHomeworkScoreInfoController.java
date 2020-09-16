package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkScoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("coursehomeworkscoreinfo/v1")
public class CourseTeachingClassHomeworkScoreInfoController extends BaseController {


    private final CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public CourseTeachingClassHomeworkScoreInfoController(CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService) {
        this.courseTeachingClassHomeworkScoreInfoService = courseTeachingClassHomeworkScoreInfoService;

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody School school) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_id) {

        return 0;
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<School> select(@RequestBody String schoolname) {

        return null;
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isExist(@RequestBody String schoolname) {
        return false;

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
