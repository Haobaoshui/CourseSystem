package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.GroupRole;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.authority.GroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("permission/group-role/v1")
public class GroupRoleController extends BaseController {

    private final GroupRoleService groupRoleService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public GroupRoleController(GroupRoleService groupRoleService) {
        this.groupRoleService = groupRoleService;

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody GroupRole groupRole) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_id) {

        return 0;
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<GroupRole> select(@RequestBody String schoolname) {

        return null;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody GroupRole groupRole) {

        return 0;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<School> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;


    }


}
