package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ExaminationConstitutionType;
import com.haobaoshui.course.service.database.ExaminationConstitutionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("examinationconstitutiontype/v1")
public class ExaminationConstitutionTypeController extends BaseController {

    private final ExaminationConstitutionTypeService examinationconstitutiontypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ExaminationConstitutionTypeController(ExaminationConstitutionTypeService examinationconstitutiontypeService) {

        this.examinationconstitutiontypeService = examinationconstitutiontypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ExaminationConstitutionType examinationConstitutionType) {

        return examinationconstitutiontypeService.add(examinationConstitutionType.getName(), examinationConstitutionType.getNote());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody ExaminationConstitutionType examinationConstitutionType) {
        return examinationconstitutiontypeService.delete(examinationConstitutionType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<ExaminationConstitutionType> select(@RequestBody String name) {
        return examinationconstitutiontypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return examinationconstitutiontypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody ExaminationConstitutionType examinationConstitutionType) {


        return examinationconstitutiontypeService.update(examinationConstitutionType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ExaminationConstitutionType> getAllList() {
        return examinationconstitutiontypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ExaminationConstitutionType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return examinationconstitutiontypeService.getPage(pageNo, pageSize);


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
    public Page<ExaminationConstitutionType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                             @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return examinationconstitutiontypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
