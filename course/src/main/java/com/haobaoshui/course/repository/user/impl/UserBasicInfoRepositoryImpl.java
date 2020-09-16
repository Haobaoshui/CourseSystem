package com.haobaoshui.course.repository.user.impl;

import com.haobaoshui.course.model.user.UserBasicInfo;
import com.haobaoshui.course.repository.user.UserBasicInfoRepository;
import com.haobaoshui.course.utility.DateTimeSql;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Repository
public class UserBasicInfoRepositoryImpl implements UserBasicInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserBasicInfoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加UserContactInfo */
    @Override
    public String add(UserBasicInfo userbasicinfo) {
        String id = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_user_basic_info (id,t_user_id,user_contact_info_name,user_contact_info_birthday,user_contact_info_sex,user_contact_info_address) VALUES(?,?,?,?,?,?)", id, userbasicinfo.getUserId(), userbasicinfo.getUserBasicInfoName(),
                DateTimeSql.GetDate(userbasicinfo.getUserBasicInfoBirthday()),
                userbasicinfo.getUserBasicInfoSex(), userbasicinfo.getUserBasicInfoAddress());

        return id;
    }

    /*
     *
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("DELETE FROM t_user_basic_info WHERE id=?", id);


    }

    /*
     *
     */
    @Override
    public int deleteByUserId(String t_user_id) {

        return jdbcTemplate.update("DELETE FROM t_user_basic_info WHERE t_user_id=?", t_user_id);


    }

    @Override
    public int updateByUserId(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex) {
        if (t_user_id == null || user_basic_info_birthday == null || user_basic_info_sex == null) return 0;


        return jdbcTemplate.update("update t_user_basic_info set user_contact_info_birthday=?,user_contact_info_sex=? WHERE t_user_id=?", DateTimeSql.GetDate(user_basic_info_birthday),
                Integer.parseInt(user_basic_info_sex), t_user_id);


    }

    @Override
    public int updateByUserId(String t_user_id, String user_contact_info_name, String user_basic_info_birthday,
                              String user_basic_info_sex) {
        if (t_user_id == null || user_basic_info_birthday == null || user_basic_info_sex == null) return 0;

        return jdbcTemplate.update("update t_user_basic_info set user_contact_info_name=?,user_contact_info_birthday=?,user_contact_info_sex=? WHERE t_user_id=?", user_contact_info_name, DateTimeSql.GetDate(user_basic_info_birthday),
                Integer.parseInt(user_basic_info_sex), t_user_id);

    }

    @Override
    public int updateByUserId(String t_user_id, String user_contact_info_name, Date user_basic_info_birthday,
                              int user_basic_info_sex) {
        if (t_user_id == null || user_basic_info_birthday == null) return 0;

        return jdbcTemplate.update("update t_user_basic_info set user_contact_info_name=?,user_contact_info_birthday=?,user_contact_info_sex=? WHERE t_user_id=?", user_contact_info_name, user_basic_info_birthday, user_basic_info_sex,
                t_user_id);


    }

    @Override
    public int updateByUserId(String t_user_id, UserBasicInfo userbasicinfo) {
        return updateByUserId(t_user_id, userbasicinfo.getUserBasicInfoName(), userbasicinfo.getUserBasicInfoBirthday(),
                userbasicinfo.getUserBasicInfoSex());
    }

    /*
     * 根据ID得到UserContactInfo
     */
    @Override
    public UserBasicInfo getUserBasicInfoByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_basic_info WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_user_basic_info WHERE id=?", new Object[]{id}, new UserBasicInfoMapper());


    }

    /*
     * 根据用户ID得到UserContactInfo
     */
    @Override
    public UserBasicInfo getUserBasicInfoByUserId(String t_user_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_basic_info WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_user_basic_info WHERE t_user_id=?", new Object[]{t_user_id}, new UserBasicInfoMapper());


    }

    private static final class UserBasicInfoMapper implements RowMapper<UserBasicInfo> {

        @Override
        public UserBasicInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setUserId(rs.getString("t_user_id"));
            userBasicInfo.setUserBasicInfoName(rs.getString("user_contact_info_name"));
            userBasicInfo
                    .setUserBasicInfoBirthday(DateTimeSql.GetDate(rs.getString("user_contact_info_birthday")));
            userBasicInfo.setUserBasicInfoSex(Integer.parseInt(rs.getString("user_contact_info_sex")));
            userBasicInfo.setUserBasicInfoAddress(rs.getString("user_contact_info_address"));


            return userBasicInfo;
        }
    }


}
