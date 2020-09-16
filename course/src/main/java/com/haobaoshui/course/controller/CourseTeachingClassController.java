package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClass;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassViewData;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassService;
import com.haobaoshui.course.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 自然班管理
 */

@RestController
@RequestMapping("courseteachingclass/v1")
public class CourseTeachingClassController extends BaseController {


    private final CourseTeachingClassService courseTeachingClassService;
    private final StudentService studentService;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    public CourseTeachingClassController(CourseTeachingClassService courseTeachingClassService, StudentService studentService) {
        this.courseTeachingClassService = courseTeachingClassService;
        this.studentService = studentService;

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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CourseTeachingClassViewData> getCourseTeachingClassViewDataList(@RequestParam(value = "t_user_id", required = true) String t_user_id) {
        return courseTeachingClassService.getCourseTeachingClassViewDataByUserId(t_user_id);
    }

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public CourseTeachingClassViewData getCourseTeachingClassViewData(@RequestParam(value = "t_user_id", required = true) String t_user_id,
                                                                      @RequestParam(value = "t_course_teaching_class_id", required = true) String t_course_teaching_class_id) {
        return courseTeachingClassService.getTeachingClassViewDataByCourseTeachingClassId(t_course_teaching_class_id);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseTeachingClass> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;


    }

    @RequestMapping(value = "/studentpage", method = RequestMethod.GET)
    public Page<StudentViewData> getPageByCourseTeachingClassId(@RequestParam(value = "t_user_id", required = true) String t_user_id,
                                                                @RequestParam(value = "t_course_teaching_class_id", required = true) String t_course_teaching_class_id,
                                                                @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return studentService.getPageByCourseTeachingClassId(t_course_teaching_class_id, pageNo, pageSize);


    }

}
