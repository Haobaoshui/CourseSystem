package com.haobaoshui.course.repository.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Resource;
import com.haobaoshui.course.repository.authority.ResourceRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ResourceRepositoryImpl implements ResourceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResourceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加用户 */
    @Override
    public String add(Resource resource) {
        if (resource == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_resource(id,uri,uri_element) VALUES(?,?,?)", newid, resource.getUri(), resource.getUriElement());
        return newid;


    }

    /*
     * 添加
     */
    @Override
    public String add(String uri, String uri_element) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_resource(id,uri,uri_element) VALUES(?,?,?)", newid, uri.trim(), uri_element);
        return newid;


    }

    /*
     * 删除
     */
    @Override
    public int delete(String id) {
        return jdbcTemplate.update("DELETE FROM t_resource WHERE id=?", id);

    }

    /*
     * 更新
     */
    @Override
    public int update(String id, String uri, String uri_element) {
        if (id == null || uri == null) return 0;

        return jdbcTemplate.update("update t_resource set uri=?,uri_element=? WHERE id=?", uri.trim(), uri_element, id);


    }

    /*
     * 查找
     */
    @Override
    public List<Resource> select(String uri) {

        if (uri == null || uri.length() == 0) return getAll();

        uri = "%" + uri.trim() + "%";

        if (jdbcTemplate.queryForObject("select count(*) FROM t_resource WHERE uri like ?", new
                Object[]{uri}, Integer.class) == 0) return null;

        return jdbcTemplate.query("select * FROM t_resource WHERE uri like ?", new Object[]{uri}, new ResourceMapper());


    }

    /*
     * 根据用户名得到用户
     */
    @Override
    public Resource getByURI(String uri) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_resource WHERE uri=?", new
                Object[]{uri}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_resource WHERE uri=?", new Object[]{uri}, new ResourceMapper());


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public Resource getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_resource WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_resource WHERE id=?", new Object[]{id}, new ResourceMapper());


    }

    /*
     * 返回所有结果
     */
    @Override
    public List<Resource> getAll() {

        if (jdbcTemplate.queryForObject("select count(*) FROM t_resource ", Integer.class) == 0) return null;

        return jdbcTemplate.query("select *FROM t_resource ", new ResourceMapper());


    }

    /*
     * 得到学院总数
     */
    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_resource", Integer.class);
    }

    /*
     * 分页查询
     *
     * @PageBegin 开始页面，从0开始
     */
    @Override
    public List<Resource> PageQuery(int PageBegin, int PageSize) {

        return jdbcTemplate.query("select a.* from t_resource as a inner join (select id from t_resource limit ?, ?) as b on a.id = b.id ", new Object[]{PageBegin * PageSize, PageSize}, new ResourceMapper());


    }

    /**
     * 获取学院
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    @Override
    public Page<Resource> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<Resource> data = PageQuery(pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    @Override
    public Page<Resource> getAllPage() {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();

        List<Resource> data = getAll();

        return new Page<>(0, totalCount, (int) totalCount, data);

    }

    @Override
    public Page<Resource> select(String name, int pageNo, int pageSize) {
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Resource> data = select(name);
        return new Page<>(startIndex, data.size(), pageSize, data);
    }

    private static final class ResourceMapper implements RowMapper<Resource> {

        @Override
        public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
            Resource resource = new Resource();
            resource.setId(rs.getString("id"));
            resource.setUri(rs.getString("uri"));
            resource.setUriElement(rs.getString("uri_element"));

            return resource;
        }
    }

}
