package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ScoreShowType;
import com.haobaoshui.course.service.database.ScoreShowTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("scoreshowtype/v1")
public class ScoreShowTypeController extends BaseController {

    @Autowired
    private final HttpServletRequest request;


    private final ScoreShowTypeService scoreShowTypeService;

    @Autowired
    public ScoreShowTypeController(HttpServletRequest request, ScoreShowTypeService scoreShowTypeService) {
        this.request = request;
        this.scoreShowTypeService = scoreShowTypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ScoreShowType scoreShowType) {

        return scoreShowTypeService.add(scoreShowType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody ScoreShowType scoreShowType) {
        return scoreShowTypeService.delete(scoreShowType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<ScoreShowType> select(@RequestBody String name) {
        return scoreShowTypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String schoolname) {
        if (schoolname != null && schoolname.length() > 0) return scoreShowTypeService.getByName(schoolname) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody ScoreShowType scoreShowType) {

        return scoreShowTypeService.update(scoreShowType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ScoreShowType> getAllList() {
        return scoreShowTypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ScoreShowType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return scoreShowTypeService.getPage(pageNo, pageSize);


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
    public Page<ScoreShowType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                               @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return scoreShowTypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
