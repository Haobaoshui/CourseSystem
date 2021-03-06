package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.NaturalClass;
import com.haobaoshui.course.service.organization.NaturalClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 自然班管理
 */

@RestController
@RequestMapping("naturalclass/v1")
public class NaturalClassController extends BaseController {


    private final NaturalClassService naturalClassService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public NaturalClassController(NaturalClassService naturalClassService) {

        this.naturalClassService = naturalClassService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody NaturalClass naturalClass) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody NaturalClass naturalClass) {
        return 0;

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<NaturalClass> select(@RequestBody String name) {
        return null;

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {

        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody NaturalClass naturalClass) {

        return 0;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<NaturalClass> getAllList() {
        return null;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<NaturalClass> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
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
    public Page<NaturalClass> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;
    }


}
