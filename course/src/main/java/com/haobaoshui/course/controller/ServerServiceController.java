package com.haobaoshui.course.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("serverservice/v1")
public class ServerServiceController extends BaseController {
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/now", method = RequestMethod.GET)
    public Date getDate() {
        return new Date();
    }

}
