package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxReceived;
import com.haobaoshui.course.model.mail.MailBoxSend;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.service.mail.MailBoxReceivedService;
import com.haobaoshui.course.service.mail.MailBoxSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("mailbox/v1")
public class MailBoxController extends BaseController {

    private final MailBoxReceivedService mailBoxReceivedService;
    private final MailBoxSendService mailBoxSendService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    public MailBoxController(MailBoxReceivedService mailBoxReceivedService, MailBoxSendService mailBoxSendService) {
        this.mailBoxReceivedService = mailBoxReceivedService;
        this.mailBoxSendService = mailBoxSendService;

    }


    @RequestMapping(value = "/addsend", method = RequestMethod.POST)
    public String addSend(@RequestBody MailBoxSend mailBoxSend) {

        return null;
    }

    @RequestMapping(value = "/addreceived", method = RequestMethod.POST)
    public String addReceived(@RequestBody MailBoxReceived mailBoxReceived) {

        return null;
    }

    @RequestMapping(value = "/deletesend", method = RequestMethod.DELETE)
    public int deleteSend(@RequestBody String t_id) {

        return 0;
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public List<School> select(@RequestBody String name) {

        return null;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestBody School school) {

        return 0;
    }


    @RequestMapping(value = "/pagesend", method = RequestMethod.GET)
    public Page<MailBoxSend> getPageSend(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        //return mailBoxSendService.getPage(pageNo, pageSize);
        return null;


    }

    @RequestMapping(value = "/pagereceived", method = RequestMethod.GET)
    public Page<MailBoxReceived> getPageReceived(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {


        pageNo = pageNo == null ? 1 : (pageNo < 1 ? 1 : pageNo);
        pageSize = getPageSize(request, pageSize);
        //return mailBoxReceivedService.getPage(pageNo, pageSize);
        return null;


    }


}
