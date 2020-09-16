package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ItemType;
import com.haobaoshui.course.service.database.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("itemtype/v1")
public class ItemTypeController extends BaseController {

    private final ItemTypeService itemtypeService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ItemTypeController(ItemTypeService itemtypeService) {

        this.itemtypeService = itemtypeService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ItemType itemType) {

        return itemtypeService.add(itemType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody ItemType itemType) {
        return itemtypeService.delete(itemType);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<ItemType> select(@RequestBody String name) {
        return itemtypeService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return itemtypeService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody ItemType itemType) {

        return itemtypeService.update(itemType);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ItemType> getAllList() {
        return itemtypeService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ItemType> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return itemtypeService.getPage(pageNo, pageSize);


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
    public Page<ItemType> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                          @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return itemtypeService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
