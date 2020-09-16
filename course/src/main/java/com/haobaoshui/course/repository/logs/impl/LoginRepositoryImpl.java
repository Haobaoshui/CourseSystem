package com.haobaoshui.course.repository.logs.impl;

import com.haobaoshui.course.model.Login;
import com.haobaoshui.course.repository.logs.LoginRepository;
import com.haobaoshui.course.utility.DateTimeSql;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class LoginRepositoryImpl implements LoginRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LoginRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * 删除指定用户的登录信息
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_login where id=?", id);

    }

    @Override
    public int deleteByUserId(String t_user_id) {
        return jdbcTemplate.update("DELETE from  t_login where t_user_id=?", t_user_id);


    }

    /**
     * 增加登录信息
     */
    @Override
    public String add(Login login) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_login(id,t_user_id,login_datetime,login_ip) VALUES(?,?,?,?)", newid, login.getUserId(), login.getLoginDatetime(), login.getLoginIp());
        return newid;

    }

    @Override
    public String add(String t_user_id, Timestamp login_datetime, String login_ip) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_login(id,t_user_id,login_datetime,login_ip) VALUES(?,?,?,?)", newid, t_user_id, login_datetime, login_ip);
        return newid;


    }

    @Override
    public String add(String t_user_id, String login_ip) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_login(id,t_user_id,login_datetime,login_ip) VALUES(?,?,?,?)", newid, t_user_id, DateTimeSql.Now(), login_ip);
        return newid;


    }
}
