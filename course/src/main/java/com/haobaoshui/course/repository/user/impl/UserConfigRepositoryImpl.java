package com.haobaoshui.course.repository.user.impl;

import com.haobaoshui.course.model.user.UserConfig;
import com.haobaoshui.course.repository.user.UserConfigRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserConfigRepositoryImpl implements UserConfigRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserConfigRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加用户配置
     */
    @Override
    public String add(UserConfig userConfig) {

        if (userConfig == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_user_config(id,t_user_id,page_size) VALUES(?,?,?)", newid, userConfig.getUserId(), userConfig.getPageSize());
        return newid;

    }

    /**
     * 增加用户配置
     */
    @Override
    public String add(String t_user_id, int page_size) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_user_config(id,t_user_id,page_size) VALUES(?,?,?)", newid, t_user_id, page_size);
        return newid;


    }

    /**
     * 删除
     */
    @Override
    public int deleteById(String t_user_config_id) {

        return jdbcTemplate.update("DELETE from  t_user_config where id=?", t_user_config_id);


    }

    /**
     * 根据用户Id删除
     */
    @Override
    public int deleteByUserId(String t_user_id) {
        return jdbcTemplate.update("DELETE from  t_user_config where t_user_id=?", t_user_id);

    }

    /**
     * 根据用户id判断用户是否存在，若存在返回true，否则返回false
     */
    @Override
    public boolean isUserConfigExists(String t_user_id) {
        return jdbcTemplate.queryForObject("select count(*) from t_user_config where t_user_id=?", Integer.class) > 0;

    }

    @Override
    public int updateById(String id, String t_user_id, int page_size) {
        if (id == null || t_user_id == null) return 0;

        return jdbcTemplate.update("update t_user_config set t_user_id=?, page_size=? WHERE id=?", t_user_id, page_size, id);


    }

    @Override
    public int updateById(String id, int page_size) {
        if (id == null) return 0;

        return jdbcTemplate.update("update t_user_config set page_size=? WHERE id=?", page_size, id);


    }

    @Override
    public int updateByUserId(String t_user_id, int page_size) {
        if (t_user_id == null) return 0;
        return jdbcTemplate.update("update t_user_config set page_size=? WHERE t_user_id=?", page_size, t_user_id);

    }

    /*根据用户名与密码得到用户
     * */
    @Override
    public UserConfig getById(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_config WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_user_config WHERE id=?", new Object[]{id}, new UserConfigMapper());


    }

    /*根据用户名得到用户
     * */
    @Override
    public UserConfig getUserByUserId(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_config WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_user_config WHERE t_user_id=?", new Object[]{t_user_id}, new UserConfigMapper());


    }

    private static final class UserConfigMapper implements RowMapper<UserConfig> {

        @Override
        public UserConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserConfig userConfig = new UserConfig();
            userConfig.setId(rs.getString("id"));
            userConfig.setUserId(rs.getString("t_user_id"));
            userConfig.setPageSize(rs.getInt("page_size"));

            return userConfig;
        }
    }


}
