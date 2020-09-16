package com.haobaoshui.course.controller;


import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 下载教学计划
 */

@RestController
@RequestMapping("coursehomeworksubmitfile/v1")
public class CourseTeachingClassHomeworkSubmitDownloadController extends BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService;


}
