package com.haobaoshui.course.controller;


import com.haobaoshui.course.service.organization.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("department/v1")
public class DepartmentController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    private DepartmentService departmentService;


}
