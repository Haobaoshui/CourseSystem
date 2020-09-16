package com.haobaoshui.course.controller;


import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassForumTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 下载课程-作业附件
 */

@RestController
@RequestMapping("courseforumtopicfile/v1")
public class CourseTeachingClassForumTopicDownloadController extends BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CourseTeachingClassForumTopicService courseTeachingClassForumTopicService;


}
