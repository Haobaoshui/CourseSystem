package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkBaseinfoViewData;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("coursehomework/v1")
public class CourseTeachingClassHomeworkController extends BaseController {

    private final CourseTeachingClassHomeworkService courseTeachingClassHomeworkService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public CourseTeachingClassHomeworkController(CourseTeachingClassHomeworkService courseTeachingClassHomeworkService) {
        this.courseTeachingClassHomeworkService = courseTeachingClassHomeworkService;

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody School school) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_d) {

        return 0;
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<School> select(@RequestBody String name) {

        return null;
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isExist(@RequestBody String name) {
        return false;

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody School school) {

        return 0;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseTeachingClassHomeworkBaseinfoViewData> getPage(@RequestParam(value = "t_user_id", required = true) String t_user_id,
                                                                     @RequestParam(value = "t_course_teaching_class_id", required = true) String t_course_teaching_class_id,
                                                                     @RequestParam(value = "t_course_teaching_class_homeworktype_id", required = true) String t_course_teaching_class_homeworktype_id,
                                                                     @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return courseTeachingClassHomeworkService.getPage(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, pageNo, pageSize);


    }


}
