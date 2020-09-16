package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.DatabaseLoggingType;
import com.haobaoshui.course.service.database.DatabaseLoggingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("databaseloggingtype/v1")
public class DatabaseLoggingTypeController extends BaseController {

    private final DatabaseLoggingTypeService databaseloggingtypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public DatabaseLoggingTypeController(DatabaseLoggingTypeService databaseloggingtypeService) {

        this.databaseloggingtypeService = databaseloggingtypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody DatabaseLoggingType databaseLoggingType) {

        return databaseloggingtypeService.add(databaseLoggingType.getName(), databaseLoggingType.getNote());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody DatabaseLoggingType databaseLoggingType) {
        return databaseloggingtypeService.delete(databaseLoggingType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<DatabaseLoggingType> select(@RequestBody String name) {
        return databaseloggingtypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return databaseloggingtypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody DatabaseLoggingType databaseLoggingType) {


        return databaseloggingtypeService.update(databaseLoggingType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DatabaseLoggingType> getAllList() {
        return databaseloggingtypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<DatabaseLoggingType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return databaseloggingtypeService.getPage(pageNo, pageSize);
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
    public Page<DatabaseLoggingType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                     @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return databaseloggingtypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
