package com.haobaoshui.course.repository.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.RolePermission;
import com.haobaoshui.course.repository.authority.RolePermissionRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RolePermissionRepositoryImpl implements RolePermissionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolePermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 添加Permission
     */
    @Override
    public String add(String t_role_id, String t_permission_id) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_role_permission(id,t_role_id,t_permission_id) VALUES(?,?,?)", newid, t_role_id, t_permission_id);
        return newid;

    }

    /*删除
     * */
    @Override
    public int deleteByID(String id) {
        return jdbcTemplate.update("DELETE FROM t_role_permission WHERE id=?", id);


    }

    /*删除
     * */
    @Override
    public int deleteByRoleId(String t_role_id) {
        return jdbcTemplate.update("DELETE FROM t_role_permission WHERE t_role_id=?", t_role_id);


    }

    /*删除
     * */
    @Override
    public int deletePermissionId(String t_permission_id) {
        return jdbcTemplate.update("DELETE FROM t_role_permission WHERE t_permission_id=?", t_permission_id);

    }

    @Override
    public List<RolePermission> getRolePermissionByRoleId(String t_role_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_role_permission WHERE t_role_id=?", new
                Object[]{t_role_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT id,t_permission_id FROM t_role_permission WHERE t_role_id=?", new Object[]{t_role_id}, new RolePermissionMapper());

    }

    private List<RolePermission> PageQuery(
            int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_role_permission limit ?,?", new
                Object[]{PageBegin * PageSize, PageSize}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_role_permission limit ?,?", new Object[]{PageBegin * PageSize, PageSize}, new RolePermissionMapper());


    }

    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject("select count(*) from t_role_permission", Integer.class);


    }

    @Override
    public Page<RolePermission> getPage(
            int pageNo, int pageSize) {

        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();

        //	System.out.println(t_group_id);

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<RolePermission> data = PageQuery(pageNo, pageSize);

        return new Page<>(startIndex, totalCount, pageSize,
                data);

    }

    private List<RolePermission> PageQuery(
            String roleId, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_role_permission where t_role_id=? limit ?,?", new
                Object[]{roleId, PageBegin * PageSize, PageSize}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_role_permission where t_role_id=? limit ?,?", new Object[]{roleId, PageBegin * PageSize, PageSize}, new RolePermissionMapper());


    }


    @Override
    public long getCount(String roleId) {

        return jdbcTemplate.queryForObject("SELECT count(*) from t_role_permission where t_role_id=?", Integer.class);


    }


    private static final class RolePermissionMapper implements RowMapper<RolePermission> {

        @Override
        public RolePermission mapRow(ResultSet rs, int rowNum) throws SQLException {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setId(rs.getString("id"));
            rolePermission.setPermissionId(rs.getString("t_permission_id"));
            rolePermission.setRoleId(rs.getString("t_role_id"));

            return rolePermission;
        }
    }

}
