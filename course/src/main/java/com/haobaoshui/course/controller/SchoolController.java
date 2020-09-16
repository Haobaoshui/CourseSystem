package com.haobaoshui.course.controller;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.organization.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("school/v1")
public class SchoolController extends BaseController {

    private final SchoolService schoolService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public SchoolController(SchoolService schoolService) {

        this.schoolService = schoolService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody School school) {

        return schoolService.add(school);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody School school) {
        return schoolService.delete(school);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<School> select(@RequestBody String schoolname) {
        return schoolService.getAllByLikeName(schoolname);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String schoolname) {
        return schoolService.getByName(schoolname) != null;

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody School school) {

        return schoolService.update(school);
    }

    /**
     * 得到全部学院
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<School> getAllList() {
        return schoolService.getAll();
    }

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<School> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return schoolService.getPage(pageNo, pageSize);
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
        return schoolService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
