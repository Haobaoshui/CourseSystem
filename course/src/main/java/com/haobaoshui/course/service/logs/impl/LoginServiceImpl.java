package com.haobaoshui.course.service.logs.impl;

import com.haobaoshui.course.model.Login;
import com.haobaoshui.course.repository.logs.LoginRepository;
import com.haobaoshui.course.service.logs.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void Add(Login login) {
        if (login != null) loginRepository.add(login);
    }

    @Override
    public void Add(String t_user_id, String login_ip) {
        if (t_user_id != null && login_ip != null) loginRepository.add(t_user_id, login_ip);
    }

    @Override
    public void deleteByUserId(String t_user_id) {
        loginRepository.deleteByUserId(t_user_id);
    }

}
