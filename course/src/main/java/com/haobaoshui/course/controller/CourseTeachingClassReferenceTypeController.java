package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceType;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassReferenceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("coursereferencetype/v1")
public class CourseTeachingClassReferenceTypeController extends BaseController {

    private final CourseTeachingClassReferenceTypeService courseTeachingClassReferenceTypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public CourseTeachingClassReferenceTypeController(CourseTeachingClassReferenceTypeService courseTeachingClassReferenceTypeService) {

        this.courseTeachingClassReferenceTypeService = courseTeachingClassReferenceTypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody CourseTeachingClassReferenceType courseTeachingClassReferenceType) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody CourseTeachingClassReferenceType courseTeachingClassReferenceType) {

        return 0;
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<CourseTeachingClassReferenceType> select(@RequestBody String name) {

        return null;
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {

        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody CourseTeachingClassReferenceType courseTeachingClassReferenceType) {


        return 0;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CourseTeachingClassReferenceType> getAllList() {
        return null;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseTeachingClassReferenceType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
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
    public Page<CourseTeachingClassReferenceType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                                  @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;
    }


}
