package com.haobaoshui.course.controller;


import com.haobaoshui.course.configure.CommonConstant;
import com.haobaoshui.course.model.user.UserSessionInfo;
import com.haobaoshui.course.service.user.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {


    @Autowired
    UserConfigService userConfigService;

    /**
     * 保存用户对象到Session中
     *
     * @param request
     * @param user
     */
    static void setSessionUser(HttpServletRequest request, UserSessionInfo user) {
        request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
    }

    /**
     * 获取保存在Session中的用户对象
     *
     * @param request
     * @return
     */
    static UserSessionInfo getSessionUser(HttpServletRequest request) {
        return (UserSessionInfo) request.getSession().getAttribute(
                CommonConstant.USER_CONTEXT);
    }

    public static int getPageSize(HttpServletRequest request) {
        UserSessionInfo userinfo = BaseController.getSessionUser(request);
        if (userinfo == null) return 0;

        return userinfo.getPageSize();

    }

    /**
     * 得到用户的页面大小。
     *
     * @param request
     * @param pageSize
     * @return
     */
    public int getPageSize(HttpServletRequest request, Integer pageSize) {
        UserSessionInfo userinfo = BaseController.getSessionUser(request);
        if (userinfo == null) return CommonConstant.PAGE_SIZE;


        if (pageSize == null || pageSize <= 0) return userinfo.getPageSize();
        else if (pageSize == userinfo.getPageSize()) return pageSize;
        else {
            userConfigService.updateByUserId(userinfo.getUser().getId(), pageSize);
            userinfo.setPageSize(pageSize);
            return pageSize;
        }
    }
}
