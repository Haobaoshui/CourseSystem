package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.course.CourseType;
import com.haobaoshui.course.service.course.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("coursetype/v1")
public class CourseTypeController extends BaseController {


    private final CourseTypeService coursetypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public CourseTypeController(CourseTypeService coursetypeService) {

        this.coursetypeService = coursetypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody CourseType courseType) {

        return coursetypeService.add(courseType.getName(), courseType.getNote());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody CourseType courseType) {
        return coursetypeService.delete(courseType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<CourseType> select(@RequestBody String name) {
        return coursetypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return coursetypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody CourseType courseType) {
        return coursetypeService.update(courseType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CourseType> getAllList() {
        return coursetypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return coursetypeService.getPage(pageNo, pageSize);


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
    public Page<CourseType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                            @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return coursetypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
