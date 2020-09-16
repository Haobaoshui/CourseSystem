package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ExaminationType;
import com.haobaoshui.course.service.database.ExaminationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("examinationtype/v1")
public class ExaminationTypeController extends BaseController {

    private final ExaminationTypeService examinationtypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ExaminationTypeController(ExaminationTypeService examinationtypeService) {

        this.examinationtypeService = examinationtypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ExaminationType examinationType) {

        return examinationtypeService.add(examinationType.getName(), examinationType.getNote());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody ExaminationType examinationType) {
        return examinationtypeService.delete(examinationType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<ExaminationType> select(@RequestBody String name) {
        return examinationtypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return examinationtypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody ExaminationType examinationType) {

        return examinationtypeService.update(examinationType.getId(), examinationType.getName(), examinationType.getNote());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ExaminationType> getAllList() {
        return examinationtypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ExaminationType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return examinationtypeService.getPage(pageNo, pageSize);


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
    public Page<ExaminationType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                 @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return examinationtypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
