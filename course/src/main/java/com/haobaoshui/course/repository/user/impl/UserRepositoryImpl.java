package com.haobaoshui.course.repository.user.impl;

import com.haobaoshui.course.model.user.User;
import com.haobaoshui.course.repository.user.UserRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryImpl implements UserRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加用户
     *
     * @param user
     * @return
     */
    @Override
    public String add(User user) {
        String id = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_user(id,user_name,user_password) VALUES(?,?,?)", id, user.getUserName(), user.getUserPassword());

        return id;
    }

    /**
     * 删除
     *
     * @param t_user_id
     * @return
     */
    @Override
    public int deleteById(String t_user_id) {
        return jdbcTemplate.update("DELETE FROM t_user WHERE id=?", t_user_id);
    }

    /**
     * 根据用户名与密码判断用户是否存在，若存在返回true，否则返回false
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @Override
    public boolean isUserExists(String userName, String userPwd) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_user WHERE user_name=? and user_password=?", new Object[]{userName, userPwd}, Integer.class) > 0;

    }

    /**
     * 更新用户密码
     *
     * @param id
     * @param strEncodedPwd
     * @return
     */
    @Override
    public int updateUserPassword(String id, String strEncodedPwd) {
        if (id == null || strEncodedPwd == null) return 0;
        return jdbcTemplate.update("update t_user set user_password=? WHERE id=?", strEncodedPwd, id);
    }

    /**
     * 根据用户名与密码得到用户
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @Override
    public User getUser(String userName, String userPwd) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user WHERE user_name=? and user_password=?", new
                Object[]{userName, userPwd}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_user WHERE user_name=? and user_password=?", new Object[]{userName, userPwd}, new UserMapper());

    }

    /**
     * 根据用户名得到用户
     *
     * @param userName
     * @return
     */
    @Override
    public User getUserByName(String userName) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user WHERE user_name=?", new
                Object[]{userName}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_user WHERE user_name=?", new Object[]{userName}, new UserMapper());
    }

    /**
     * 根据用户ID得到用户
     *
     * @param t_user_id
     * @return
     */
    @Override
    public User getUserByID(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user WHERE id=?", new
                Object[]{t_user_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_user WHERE id=?", new Object[]{t_user_id}, new UserMapper());
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setUserName(rs.getString("user_name"));
            user.setUserPassword(rs.getString("user_password"));

            return user;
        }
    }
}
