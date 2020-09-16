package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceType;
import com.haobaoshui.course.service.attendance.AttendanceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("attendancetype/v1")
public class AttendanceTypeController extends BaseController {

    @Autowired
    private final HttpServletRequest request;
    /**
     * 自动注入
     */
    @Autowired
    private final AttendanceTypeService attendancetypeService;

    @Autowired
    public AttendanceTypeController(HttpServletRequest request, AttendanceTypeService attendancetypeService) {
        this.request = request;
        this.attendancetypeService = attendancetypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody AttendanceType attendanceType) {

        return attendancetypeService.add(attendanceType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody AttendanceType attendanceType) {
        return attendancetypeService.delete(attendanceType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<AttendanceType> select(@RequestBody String name) {
        return attendancetypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return attendancetypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody AttendanceType attendanceType) {

        return attendancetypeService.update(attendanceType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AttendanceType> getAllList() {
        return attendancetypeService.getAll();
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<AttendanceType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return attendancetypeService.getPage(pageNo, pageSize);


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
    public Page<AttendanceType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return attendancetypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
