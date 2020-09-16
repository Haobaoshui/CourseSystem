package com.haobaoshui.course.controller;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Resource;
import com.haobaoshui.course.service.authority.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("permission/resource/v1")
public class ResourceController extends BaseController {


    private final ResourceService resourceService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ResourceController(ResourceService resourceService) {

        this.resourceService = resourceService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Resource resource) {

        return resourceService.add(resource);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int delete(@RequestBody Resource resource) {
        return resourceService.delete(resource);

    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<Resource> select(@RequestBody Resource resource) {
        //	if (name != null && name.length() > 0) return resourceService.getAllByLikeName(name);
        return null;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody Resource resource) {

        return resourceService.update(resource);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Resource> getAllList() {
        return resourceService.getAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<Resource> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        return resourceService.getPage(pageNo, pageSize);


    }


}
