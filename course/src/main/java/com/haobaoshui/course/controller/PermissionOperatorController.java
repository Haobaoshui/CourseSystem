package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.PermissionOperator;
import com.haobaoshui.course.service.authority.PermissionOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("permission/operator/v1")
public class PermissionOperatorController extends BaseController {

    private final PermissionOperatorService permissionoperatorService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public PermissionOperatorController(PermissionOperatorService permissionoperatorService) {

        this.permissionoperatorService = permissionoperatorService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody PermissionOperator permissionOperator) {

        return permissionoperatorService.add(permissionOperator);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody PermissionOperator permissionOperator) {
        return permissionoperatorService.delete(permissionOperator);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<PermissionOperator> select(@RequestBody String name) {
        return permissionoperatorService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return permissionoperatorService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody PermissionOperator permissionOperator) {

        return permissionoperatorService.update(permissionOperator);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<PermissionOperator> getAllList() {
        return permissionoperatorService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<PermissionOperator> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return permissionoperatorService.getPage(pageNo, pageSize);


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
    public Page<PermissionOperator> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                    @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return permissionoperatorService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
