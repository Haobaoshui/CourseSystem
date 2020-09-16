package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkType;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("coursehomeworktype/v1")
public class CourseTeachingClassHomeworkTypeController extends BaseController {


    private final CourseTeachingClassHomeworkTypeService courseTeachingClassHomeworkTypeService;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    public CourseTeachingClassHomeworkTypeController(CourseTeachingClassHomeworkTypeService courseTeachingClassHomeworkTypeService) {
        this.courseTeachingClassHomeworkTypeService = courseTeachingClassHomeworkTypeService;

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody CourseTeachingClassHomeworkType courseTeachingClassHomeworkType) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody CourseTeachingClassHomeworkType courseTeachingClassHomeworkType) {

        return 0;
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<CourseTeachingClassHomeworkType> select(@RequestBody String name) {

        return null;
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isExist(@RequestBody String name) {
        return false;

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody CourseTeachingClassHomeworkType courseTeachingClassHomeworkType) {

        return 0;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseTeachingClassHomeworkType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;


    }

    /**
     * 带查询的分页查询
     *
     * @param searchText
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "searchpage", method = RequestMethod.GET)
    public Page<School> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                        @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CourseTeachingClassHomeworkType> getHomeworkTypeList(@RequestParam(value = "t_user_id", required = true) String t_user_id,
                                                                     @RequestParam(value = "t_course_teaching_class_id", required = true) String t_course_teaching_class_id) {
        return courseTeachingClassHomeworkTypeService.getList(t_course_teaching_class_id);
    }

}
