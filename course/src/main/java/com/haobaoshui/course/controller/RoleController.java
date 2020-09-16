package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Role;
import com.haobaoshui.course.service.authority.RoleService;
import com.haobaoshui.course.service.organization.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("permission/role/v1")
public class RoleController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    private RoleService roleService;

    @Autowired
    public RoleController(SchoolService schoolService) {

        roleService = roleService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Role role) {

        return roleService.add(role);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody Role role) {
        return roleService.delete(role);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<Role> select(@RequestBody String name) {
        return roleService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isExist(@RequestBody String name) {
        if (name != null && name.length() > 0) return roleService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody Role role) {
        return roleService.update(role);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Role> getAllList() {
        return roleService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<Role> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return roleService.getPage(pageNo, pageSize);


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
    public Page<Role> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                      @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return roleService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
