package com.haobaoshui.course.repository.user.impl;

import com.haobaoshui.course.model.user.UserContactInfo;
import com.haobaoshui.course.repository.user.UserContactInfoRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserContactInfoRepositoryImpl implements UserContactInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserContactInfoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     *
     * @param usercontactinfo
     * @return
     */
    @Override
    public String add(UserContactInfo usercontactinfo) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_user_contact_info (id,t_user_id,t_user_contact_type_id,user_contact_value) VALUES(?,?,?,?)", newid, usercontactinfo.getUserId(),
                usercontactinfo.getUserContactTypeId(),
                usercontactinfo.getUserContactValue());
        return newid;

    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_user_contact_info where id=?", id);


    }

    /**
     * @param t_user_id
     * @return
     */
    @Override
    public int deleteByUserId(String t_user_id) {
        return jdbcTemplate.update("DELETE from  t_user_contact_info where t_user_id=?", t_user_id);


    }

    /**
     * @param t_user_contact_type_id
     * @return
     */
    @Override
    public int deleteByUserContactTypeId(String t_user_contact_type_id) {

        return jdbcTemplate.update("DELETE from  t_user_contact_info where t_user_contact_type_id=?", t_user_contact_type_id);


    }

    /**
     * 更新用户个人信息，更新前先删除以前旧信息
     *
     * @param t_user_id
     * @param contacttypeId
     * @param user_contact_value
     * @return
     */
    @Override
    public int update(String t_user_id, String[] contacttypeId, String[] user_contact_value) {

        deleteByUserId(t_user_id);

        int contactCount = 0;
        if (contacttypeId != null) contactCount = contacttypeId.length;

        if (contactCount > 0) {

            UserContactInfo[] usercontactinfos = new UserContactInfo[contactCount];

            for (int i = 0; i < contactCount; i++) {

                usercontactinfos[i] = new UserContactInfo();

                usercontactinfos[i].setUserContactTypeId(contacttypeId[i]);
                usercontactinfos[i].setUserContactValue(user_contact_value[i]);
            }

            // 添加联系方式
            if (usercontactinfos != null) {
                for (UserContactInfo u : usercontactinfos) u.setUserId(t_user_id);

                for (UserContactInfo u : usercontactinfos) add(u);
            }
        }
        return 1;
    }

    @Override
    public int update(String t_user_id, UserContactInfo[] usercontactinfos) {

        deleteByUserId(t_user_id);

        if (usercontactinfos != null) {
            for (UserContactInfo u : usercontactinfos) u.setUserId(t_user_id);

            for (UserContactInfo u : usercontactinfos) add(u);
        }
        return 1;
    }

    /*
     * 根据ID得到UserContactInfo
     */
    @Override
    public UserContactInfo getById(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_contact_info WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_user_contact_info WHERE id=?", new Object[]{id}, new UserContactInfoMapper());

    }

    /*
     * 根据用户ID得到UserContactInfo
     */
    @Override
    public List<UserContactInfo> getByUserId(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_contact_info WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("select * from t_user_contact_info where t_user_id=?", new Object[]{t_user_id}, new UserContactInfoMapper());

    }

    private static final class UserContactInfoMapper implements RowMapper<UserContactInfo> {

        @Override
        public UserContactInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserContactInfo userContactInfo = new UserContactInfo();
            userContactInfo.setId(rs.getString("id"));
            userContactInfo.setUserId(rs.getString("t_user_id"));
            userContactInfo.setUserContactTypeId(rs
                    .getString("t_user_contact_type_id"));
            userContactInfo.setUserContactValue(rs
                    .getString("user_contact_value"));

            return userContactInfo;
        }
    }


}
