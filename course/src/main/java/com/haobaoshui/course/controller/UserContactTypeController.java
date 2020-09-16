package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.user.UserContactType;
import com.haobaoshui.course.service.user.UserContactTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("contacttype")
public class UserContactTypeController extends BaseController {

    @Autowired
    private final HttpServletRequest request;

    private final UserContactTypeService userContactTypeService;

    @Autowired
    public UserContactTypeController(HttpServletRequest request, UserContactTypeService userContactTypeService) {
        this.request = request;
        this.userContactTypeService = userContactTypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody UserContactType userContactType) {

        return userContactTypeService.add(userContactType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody UserContactType userContactType) {
        return userContactTypeService.delete(userContactType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<UserContactType> select(@RequestBody String schoolname) {
        return userContactTypeService.getAllByLikeName(schoolname);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String schoolname) {
        return userContactTypeService.getByName(schoolname) != null;

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody UserContactType userContactType) {
        return userContactTypeService.update(userContactType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserContactType> getAllList() {
        return userContactTypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<UserContactType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return userContactTypeService.getPage(pageNo, pageSize);


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
    public Page<UserContactType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                 @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return userContactTypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
