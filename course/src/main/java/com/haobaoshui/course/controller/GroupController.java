package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Group;
import com.haobaoshui.course.service.authority.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/permission/group")
public class GroupController extends BaseController {


    private final GroupService groupService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public GroupController(GroupService groupService) {

        this.groupService = groupService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Group group) {

        return groupService.add(group);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody Group group) {
        return groupService.delete(group);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<Group> select(@RequestBody String name) {
        return groupService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return groupService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody Group group) {
        return groupService.update(group);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Group> getAllList() {
        return groupService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<Group> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return groupService.getPage(pageNo, pageSize);


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
    public Page<Group> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                       @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return groupService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
