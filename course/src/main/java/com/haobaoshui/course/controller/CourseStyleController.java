package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.course.CourseStyle;
import com.haobaoshui.course.service.course.CourseStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("coursestyle/v1")
public class CourseStyleController extends BaseController {

    @Autowired
    private final HttpServletRequest request;

    private final CourseStyleService coursestyleService;

    @Autowired
    public CourseStyleController(HttpServletRequest request, CourseStyleService coursestyleService) {
        this.request = request;
        this.coursestyleService = coursestyleService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody CourseStyle courseStyle) {

        return coursestyleService.add(courseStyle);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody CourseStyle courseStyle) {
        return coursestyleService.delete(courseStyle);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<CourseStyle> select(@RequestBody String name) {
        return coursestyleService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isExist(@RequestBody String name) {
        if (name != null && name.length() > 0) return coursestyleService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody CourseStyle courseStyle) {
        return coursestyleService.update(courseStyle);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CourseStyle> getAllList() {
        return coursestyleService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<CourseStyle> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return coursestyleService.getPage(pageNo, pageSize);


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
    public Page<CourseStyle> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                             @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return coursestyleService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
