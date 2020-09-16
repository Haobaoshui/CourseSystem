package com.haobaoshui.course.controller;


import com.haobaoshui.course.service.authority.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("permission/role-permission/v1")
public class RolePermissionController extends BaseController {

    private final RolePermissionService rolepermissionService;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    public RolePermissionController(RolePermissionService rolepermissionService) {

        this.rolepermissionService = rolepermissionService;
    }


}
