package com.haobaoshui.course.controller;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Permission;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.authority.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("permission/v1")
public class PermissionController extends BaseController {

    @Autowired
    private final HttpServletRequest request;
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(HttpServletRequest request, PermissionService permissionService) {
        this.request = request;
        this.permissionService = permissionService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Permission permission) {

        return permissionService.add(permission);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody Permission permission) {
        return 0;

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<Permission> select(@RequestBody String name) {
        return null;

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {

        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody Permission permission) {

        return 0;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Permission> getAllList() {
        return null;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<Permission> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return permissionService.getPage(pageNo, pageSize);


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
    public Page<School> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                        @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return null;
    }


}
