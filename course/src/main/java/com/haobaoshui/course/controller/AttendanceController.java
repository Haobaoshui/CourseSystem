package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.Attendance;
import com.haobaoshui.course.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("courseattendance/v1")
public class AttendanceController extends BaseController {

    private final AttendanceService attendanceService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Attendance attendance) {

        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody String t_school_id) {
        return 0;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody Attendance attendance) {

        return 0;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<Attendance> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);

        pageSize = getPageSize(request, pageSize);
        return null;


    }

}
