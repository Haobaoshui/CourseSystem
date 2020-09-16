package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceState;
import com.haobaoshui.course.service.attendance.AttendanceStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("attendancestate/v1")
public class AttendanceStateController extends BaseController {

    @Autowired
    private final HttpServletRequest request;

    @Autowired
    private final AttendanceStateService attendancestateService;


    @Autowired
    public AttendanceStateController(HttpServletRequest request, AttendanceStateService attendancestateService) {
        this.request = request;
        this.attendancestateService = attendancestateService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody AttendanceState attendanceState) {

        return attendancestateService.add(attendanceState);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody AttendanceState attendanceState) {
        return attendancestateService.delete(attendanceState);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<AttendanceState> select(@RequestBody String schoolname) {
        if (schoolname != null && schoolname.length() > 0) return attendancestateService.getAllByLikeName(schoolname);
        return null;
    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return attendancestateService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody AttendanceState school) {

        return attendancestateService.update(school.getId(), school.getName(), school.getNote());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AttendanceState> getAllList() {
        return attendancestateService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<AttendanceState> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return attendancestateService.getPage(pageNo, pageSize);


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
    public Page<AttendanceState> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                 @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return attendancestateService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
