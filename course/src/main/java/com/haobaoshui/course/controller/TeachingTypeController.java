package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.TeachingType;
import com.haobaoshui.course.service.courseteachingclass.TeachingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("teachingtype/v1")
public class TeachingTypeController extends BaseController {

    @Autowired
    private final HttpServletRequest request;

    private final TeachingTypeService teachingtypeService;

    @Autowired
    public TeachingTypeController(HttpServletRequest request, TeachingTypeService teachingtypeService) {
        this.request = request;
        this.teachingtypeService = teachingtypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody TeachingType teachingType) {

        return teachingtypeService.add(teachingType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody TeachingType teachingType) {
        return teachingtypeService.delete(teachingType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<TeachingType> select(@RequestBody String schoolname) {
        return teachingtypeService.getAllByLikeName(schoolname);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String schoolname) {
        return teachingtypeService.getByName(schoolname) != null;

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody TeachingType teachingTypel) {

        return teachingtypeService.update(teachingTypel);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TeachingType> getAllList() {
        return teachingtypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<TeachingType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return teachingtypeService.getPage(pageNo, pageSize);


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
    public Page<TeachingType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return teachingtypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
