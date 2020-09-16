package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ItemDifficulty;
import com.haobaoshui.course.service.database.ItemDifficultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("itemdifficulty/v1")
public class ItemDifficultyController extends BaseController {

    private final ItemDifficultyService itemdifficultyService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ItemDifficultyController(ItemDifficultyService itemdifficultyService) {

        this.itemdifficultyService = itemdifficultyService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ItemDifficulty itemDifficulty) {

        return itemdifficultyService.add(itemDifficulty);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody ItemDifficulty itemDifficulty) {
        return itemdifficultyService.delete(itemDifficulty);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<ItemDifficulty> select(@RequestBody String name) {
        return itemdifficultyService.getAllByLikeName(name);

    }

    @RequestMapping(value = "/isexist", method = RequestMethod.GET)
    public boolean isexist(@RequestBody String name) {
        if (name != null && name.length() > 0) return itemdifficultyService.getByName(name) != null;
        return false;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody ItemDifficulty itemDifficulty) {


        return itemdifficultyService.update(itemDifficulty);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ItemDifficulty> getAllList() {
        return itemdifficultyService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ItemDifficulty> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return itemdifficultyService.getPage(pageNo, pageSize);


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
    public Page<ItemDifficulty> getSearchedPage(@RequestParam(value = "searchText", required = true) String searchText,
                                                @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return itemdifficultyService.getPageByLikeName(searchText, pageNo, pageSize);
    }


}
