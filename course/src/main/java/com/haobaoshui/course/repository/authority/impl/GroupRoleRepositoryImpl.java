package com.haobaoshui.course.repository.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.GroupRole;
import com.haobaoshui.course.repository.authority.GroupRoleRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class GroupRoleRepositoryImpl implements GroupRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(String t_group_id, String t_role_id) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_group_role(id,t_group_id,t_role_id) VALUES(?,?,?)", newid, t_group_id, t_role_id);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_group_role where id=?", id);

    }

    @Override
    public int deleteByGroupId(String t_group_id) {
        return jdbcTemplate.update("DELETE FROM t_group_role WHERE t_group_id=?", t_group_id);
    }

    @Override
    public GroupRole getById(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_group_role WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_group_role WHERE id=?", new Object[]{id}, new GroupRoleMapper());
    }

    @Override
    public List<GroupRole> getByGroupId(String t_group_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_group_role WHERE t_group_id=?", new
                Object[]{t_group_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_group_role WHERE t_group_id=?", new Object[]{t_group_id}, new GroupRoleMapper());


    }

    @Override
    public List<GroupRole> getByRoleId(String t_role_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_group_role WHERE t_role_id=?", new
                Object[]{t_role_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_group_role WHERE t_role_id=?", new Object[]{t_role_id}, new GroupRoleMapper());


    }

    private List<GroupRole> PageQuery(String t_group_id, int PageBegin,
                                      int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        if (getCount(t_group_id) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_group_role WHERE t_group_id=? limit ?,?",
                new Object[]{t_group_id, PageBegin * PageSize, PageSize},
                new GroupRoleMapper());


    }

    @Override
    public long getCount(String t_group_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_group_role WHERE t_group_id=?",
                new Object[]{t_group_id}, new int[]{Types.VARCHAR},
                Long.class);
    }

    @Override
    public Page<GroupRole> getPageByGroupId(String t_group_id,
                                            int pageNo, int pageSize) {

        long totalCount = getCount(t_group_id);
        if (totalCount < 1) return new Page<>();

        //	System.out.println(t_group_id);

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<GroupRole> data = PageQuery(t_group_id, pageNo, pageSize);

        return new Page<>(startIndex, totalCount, pageSize,
                data);

    }

    private static final class GroupRoleMapper implements RowMapper<GroupRole> {

        @Override
        public GroupRole mapRow(ResultSet rs, int rowNum) throws SQLException {
            GroupRole groupRole = new GroupRole();
            groupRole.setId(rs.getString("id"));
            groupRole.setGroupId(rs.getString("t_group_id"));
            groupRole.setRoleId(rs.getString("t_role_id"));

            return groupRole;
        }
    }

}
