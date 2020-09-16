package com.haobaoshui.course.utility;


import com.haobaoshui.course.configure.CommonConstant;
import com.haobaoshui.course.model.user.UserSessionInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class SessionUser {

    /**
     * 得到操作用户的IP地址
     *
     * @param request
     * @return
     */
    private static String getLogIp(HttpServletRequest request) {


        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) ip = request.getRemoteAddr();


        return ip;
    }

    /**
     * 保存用户对象到Session中
     *
     * @param request
     * @param user
     */
    public static void setSessionUser(HttpServletRequest request, UserSessionInfo user) {
        //   user.setLogIP(SessionUser.getLogIp(request));

        request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
    }

    public static void setSessionUser(HttpServletRequest request) {

        request.getSession().removeAttribute(CommonConstant.USER_CONTEXT);
    }

    /**
     * 获取保存在Session中的用户对象
     *
     * @param request
     * @return
     */
    public static UserSessionInfo getSessionUser(HttpServletRequest request) {
        return (UserSessionInfo) request.getSession().getAttribute(
                CommonConstant.USER_CONTEXT);
    }

    /**
     * 产生验证码，这里仅产生4个数字
     *
     * @param request
     * @return
     */
    public static String genCode(HttpServletRequest request) {
        Random r = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) code += r.nextInt(10);
        request.getSession().setAttribute(CommonConstant.USER_IDENTIFY_CODE, code);
        return code;

    }

    public static String getCode(HttpServletRequest request) {
        return request.getSession().getAttribute(CommonConstant.USER_IDENTIFY_CODE).toString();

    }
}
