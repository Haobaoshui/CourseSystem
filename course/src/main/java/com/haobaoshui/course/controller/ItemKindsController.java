package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ItemKinds;
import com.haobaoshui.course.service.database.ItemKindsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("itemkinds/v1")
public class ItemKindsController extends BaseController {

    private final ItemKindsService itemkindsService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ItemKindsController(ItemKindsService itemkindsService) {

        this.itemkindsService = itemkindsService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ItemKinds itemKinds) {

        return itemkindsService.add(itemKinds);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody ItemKinds itemKinds) {
        return itemkindsService.delete(itemKinds);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<ItemKinds> select(@RequestBody String name) {
        return itemkindsService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return itemkindsService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody ItemKinds itemKinds) {


        return itemkindsService.update(itemKinds);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ItemKinds> getAllList() {
        return itemkindsService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ItemKinds> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return itemkindsService.getPage(pageNo, pageSize);


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
    public Page<ItemKinds> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                           @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return itemkindsService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
