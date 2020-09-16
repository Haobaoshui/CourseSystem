package com.haobaoshui.course.controller;

import com.haobaoshui.course.service.authority.PermissionService;
import com.haobaoshui.course.service.authority.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("permission/permission-resource/v1")
public class PermissionrResourceController extends BaseController {


    private final PermissionService permissionService;

    private final ResourceService resourceService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public PermissionrResourceController(PermissionService permissionService, ResourceService resourceService) {

        this.permissionService = permissionService;
        this.resourceService = resourceService;
    }


}
