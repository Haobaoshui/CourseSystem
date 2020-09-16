package com.haobaoshui.course.controller;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ScoreMarkingType;
import com.haobaoshui.course.service.database.ScoreMarkingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("scoremarkingtype/v1")
public class ScoreMarkingTypeController extends BaseController {

    private final ScoreMarkingTypeService scoreMarkingTypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ScoreMarkingTypeController(ScoreMarkingTypeService scoreMarkingTypeService) {

        this.scoreMarkingTypeService = scoreMarkingTypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ScoreMarkingType scoreMarkingType) {

        return scoreMarkingTypeService.add(scoreMarkingType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody ScoreMarkingType scoreMarkingType) {
        return scoreMarkingTypeService.delete(scoreMarkingType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<ScoreMarkingType> select(@RequestBody String name) {
        return scoreMarkingTypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isExist(@RequestBody String name) {
        if (name != null && name.length() > 0) return scoreMarkingTypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody ScoreMarkingType scoreMarkingType) {

        return scoreMarkingTypeService.update(scoreMarkingType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ScoreMarkingType> getAllList() {
        return scoreMarkingTypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ScoreMarkingType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return scoreMarkingTypeService.getPage(pageNo, pageSize);


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
    public Page<ScoreMarkingType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                  @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return scoreMarkingTypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
