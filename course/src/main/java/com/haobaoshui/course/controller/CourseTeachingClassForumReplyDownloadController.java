package com.haobaoshui.course.controller;


import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassForumReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 下载课程-作业附件
 */

@RestController
@RequestMapping("courseforumreplyfile/v1")
public class CourseTeachingClassForumReplyDownloadController extends BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CourseTeachingClassForumReplyService courseTeachingClassForumReplyService;


}
