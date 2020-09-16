package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceMode;
import com.haobaoshui.course.service.attendance.AttendanceModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("attendancemode/v1")
public class AttendanceModeController extends BaseController {

    @Autowired
    private final HttpServletRequest request;

    @Autowired
    private final AttendanceModeService attendancemodeService;


    @Autowired
    public AttendanceModeController(HttpServletRequest request, AttendanceModeService attendancemodeService) {
        this.request = request;
        this.attendancemodeService = attendancemodeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody AttendanceMode attendanceMode) {
        return attendancemodeService.add(attendanceMode);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody AttendanceMode attendanceMode) {
        return attendancemodeService.delete(attendanceMode);
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<AttendanceMode> select(@RequestBody String attendanceModeName) {
        return attendancemodeService.getAllByLikeName(attendanceModeName);
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isExist(@RequestBody String name) {
        if (name != null && name.length() > 0) return attendancemodeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody AttendanceMode attendanceMode) {
        return attendancemodeService.update(attendanceMode);
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<AttendanceMode> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return attendancemodeService.getPage(pageNo, pageSize);


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
    public Page<AttendanceMode> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return attendancemodeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AttendanceMode> getAllList() {
        return attendancemodeService.getAll();
    }

}
