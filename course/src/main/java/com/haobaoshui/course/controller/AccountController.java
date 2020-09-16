package com.haobaoshui.course.controller;


import com.haobaoshui.course.model.ResultNormal;
import com.haobaoshui.course.model.user.User;
import com.haobaoshui.course.model.user.UserChangePassword;
import com.haobaoshui.course.model.user.UserSessionInfo;
import com.haobaoshui.course.service.logs.LoginService;
import com.haobaoshui.course.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("account/v1")
public class AccountController extends BaseController {

    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public AccountController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }


    /**
     * 登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public UserSessionInfo doLogin(@RequestBody User user) {

        UserSessionInfo userinfo = getSessionUser(request);

        if (userinfo != null) return userinfo;

        // 对密码进行md5运算，比较两个密码其实是比较两个密码的md5值
        user.EncoderPassword();

        User dbUser = userService.getUserByUserName(user.getUserName());

        if (dbUser == null) return null;// 用户名不存在

        // 用户密码不正确
        if (dbUser.getUserName() == null || !dbUser.getUserName().equals(user.getUserName())) return null;

        // 写入到登录日志中
        if (dbUser.getUserPassword() == null || !dbUser.getUserPassword().equals(user.getUserPassword())) return null;

        //    loginService.Add(dbUser.getId(), request.getRemoteAddr());

        UserSessionInfo userSessionInfo = userService.getUserSessionInfoByUserId(dbUser.getId());


        //保存到会话中
        BaseController.setSessionUser(request, userSessionInfo);

        return userSessionInfo;
    }

    /**
     * 修改密码
     *
     * @param userChangePassword
     * @return
     */
    @RequestMapping(value = "/changepwd", method = RequestMethod.PUT)
    public ResultNormal confirmpwd(@RequestBody UserChangePassword userChangePassword) {

        if (userChangePassword == null) return null;

        String old_user_password = userChangePassword.getUser().getUserPassword();
        String new_user_password = userChangePassword.getNewPassword();


        UserSessionInfo userinfo = getSessionUser(request);
        if (userinfo == null) return new ResultNormal("", 1);

        User user = new User();
        user.setUserPassword(old_user_password);
        user.EncoderPassword();

        if (!userinfo.getUser().getUserPassword().equals(user.getUserPassword()))
            return new ResultNormal("旧密码错误，请重新输入", 1);


        user.setUserPassword(new_user_password);
        user.EncoderPassword();

        userService.updateUserPassword(userinfo.getUser().getId(), user.getUserPassword());
        userinfo.getUser().setUserPassword(user.getUserPassword());
        return new ResultNormal("成功", 0);
    }

}
