package com.haobaoshui.course.repository.organization.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.UserGroup;
import com.haobaoshui.course.repository.authority.UserGroupRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserGroupRepositoryImpl implements UserGroupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserGroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 添加用户-组
     */
    @Override
    public String add(String t_group_id, String t_user_id) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_user_group(id,t_group_id,t_user_id) VALUES(?,?,?)", newid, t_group_id, t_user_id);
        return newid;

    }

    /*
     *
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("DELETE FROM t_user_group WHERE id=?", id);


    }

    /*
     *
     */
    @Override
    public int deleteByUserId(String t_user_id) {
        return jdbcTemplate.update("DELETE FROM t_user_group WHERE t_user_id=?", t_user_id);


    }

    /*
     *
     */
    @Override
    public int deleteByGroupId(String t_group_id) {
        return jdbcTemplate.update("DELETE FROM t_user_group WHERE t_group_id=?", t_group_id);


    }

    @Override
    public int deleteByGroupIdAndUserId(String t_group_id, String t_user_id) {
        return jdbcTemplate.update("DELETE FROM t_user_group WHERE t_group_id=? and t_user_id=?", t_group_id, t_user_id);


    }

    @Override
    public int update(String t_user_id, String[] groupId) {
        if (groupId == null) return 0;

        deleteByUserId(t_user_id);

        for (String g : groupId) add(g, t_user_id);
        return 1;
    }

    /**
     * 根据t_group_id得到总数
     *
     * @param t_group_id
     * @return
     */
    @Override
    public long getCount(String t_group_id) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_group WHERE t_group_id=?", new Object[]{t_group_id}, Integer.class);
    }

    @Override
    public long getGroupCountByUserId(String t_user_id) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_group WHERE t_user_id=?", new Object[]{t_user_id}, Integer.class);


    }


    /**
     * 根据人得到所有组
     */
    @Override
    public List<UserGroup> getUserGroupByUserId(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_group WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_user_group WHERE t_user_id=?", new Object[]{t_user_id}, new UserGroupMapper());


    }

    private List<UserGroup> getUserGroupByUserId(String t_user_id, int PageBegin, int PageSize) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_group WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_user_group WHERE t_user_id=? limit ?,?", new Object[]{t_user_id, PageBegin * PageSize, PageSize}, new UserGroupMapper());
    }


    @Override
    public List<UserGroup> getUserGroupByGroupId(String t_group_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_user_group WHERE t_group_id=?", new
                Object[]{t_group_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_user_group WHERE t_group_id=?", new Object[]{t_group_id}, new UserGroupMapper());
    }


    private List<UserGroup> getUserGroupByGroupId(String t_group_id, int PageBegin, int PageSize) {


        return jdbcTemplate.query("SELECT id,t_user_id FROM t_user_group WHERE t_group_id=? limit ?,?", new Object[]{t_group_id,
                PageBegin * PageSize, PageSize}, new UserGroupMapper());


    }

    @Override
    public Page<UserGroup> getPageByGroupId(String t_group_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_group_id);
        if (totalCount < 1) return new Page<>();

        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<UserGroup> data = getUserGroupByGroupId(t_group_id, pageNo, pageSize);

        return new Page<>(startIndex, totalCount, (int) totalCount, data);
    }


    @Override
    public Page<UserGroup> getPageByUserId(String t_user_id, int pageNo, int pageSize) {

        long totalCount = getGroupCountByUserId(t_user_id);
        if (totalCount < 1) return new Page<>();


        final int startIndex = 0;

        List<UserGroup> data = getUserGroupByUserId(t_user_id, pageNo, pageSize);

        return new Page<>(startIndex, totalCount, (int) totalCount, data);

    }

    private static final class UserGroupMapper implements RowMapper<UserGroup> {

        @Override
        public UserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserGroup userGroup = new UserGroup();
            userGroup.setId(rs.getString("id"));
            userGroup.setGroupId(rs.getString("t_group_id"));
            userGroup.setUserId("t_user_id");
            return userGroup;
        }
    }
}
