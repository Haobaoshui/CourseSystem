package com.haobaoshui.course.repository.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Permission;
import com.haobaoshui.course.repository.authority.PermissionRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 添加Permission
     */
    @Override
    public String add(String name, String note, String t_permission_operator_id, String t_resource_id) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_permission(id,name,note,t_permission_operator_id,t_resource_id) VALUES(?,?,?,?,?)", newid, name, note, t_permission_operator_id, t_resource_id);
        return newid;

    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("delete from t_permission where id=?", id);
    }

    @Override
    public Permission GetById(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_permission WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_permission WHERE id=?", new Object[]{id}, new PermissionMapper());


    }

    private List<Permission> PageQuery(
            int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;


        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_permission order by name limit ?,?", new
                Object[]{PageBegin * PageSize, PageSize}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_permission order by name limit ?,?", new Object[]{PageBegin * PageSize, PageSize}, new PermissionMapper());


    }

    @Override
    public long getCount() {

        return jdbcTemplate.queryForObject("select count(*) from t_permission", Integer.class);


    }

    @Override
    public Page<Permission> getPage(
            int pageNo, int pageSize) {

        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();

        //	System.out.println(t_group_id);

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<Permission> data = PageQuery(pageNo, pageSize);

        return new Page<>(startIndex, totalCount, pageSize,
                data);

    }


    private static final class PermissionMapper implements RowMapper<Permission> {

        @Override
        public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
            Permission permission = new Permission();
            permission.setId(rs.getString("id"));
            permission.setName(rs.getString("name"));
            permission.setNote(rs.getString("note"));
            permission.setPermissionOperatorId(rs.getString("t_permission_operator_id"));
            permission.setResourceId(rs.getString("t_resource_id"));

            return permission;
        }
    }


}
