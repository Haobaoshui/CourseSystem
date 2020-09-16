package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.KnowledgepointType;
import com.haobaoshui.course.service.database.KnowledgepointTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("knowledgepointtypetype/v1")
public class KnowledgepointTypeController extends BaseController {

    private final KnowledgepointTypeService knowledgepointTypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public KnowledgepointTypeController(KnowledgepointTypeService knowledgepointTypeService) {
        request = request;
        this.knowledgepointTypeService = knowledgepointTypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody KnowledgepointType knowledgepointType) {

        return knowledgepointTypeService.add(knowledgepointType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody KnowledgepointType knowledgepointType) {
        return knowledgepointTypeService.delete(knowledgepointType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<KnowledgepointType> select(@RequestBody String name) {
        return knowledgepointTypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return knowledgepointTypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody KnowledgepointType knowledgepointType) {


        return knowledgepointTypeService.update(knowledgepointType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<KnowledgepointType> getAllList() {
        return knowledgepointTypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<KnowledgepointType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return knowledgepointTypeService.getPage(pageNo, pageSize);


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
    public Page<KnowledgepointType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                    @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return knowledgepointTypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
