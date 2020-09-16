package com.haobaoshui.course.repository.organization.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.NaturalClass;
import com.haobaoshui.course.repository.organization.NaturalClassRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NaturalClassRepositoryImpl implements NaturalClassRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NaturalClassRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     *
     * @param naturalClass
     * @return
     */
    @Override
    public String add(NaturalClass naturalClass) {

        if (naturalClass == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_natural_class(id,name,note) VALUES(?,?,?)", newid, naturalClass.getName(), naturalClass.getNote());
        return newid;
    }

    /**
     * 增加
     *
     * @param name
     * @param note
     * @return
     */
    @Override
    public String add(String name, String note) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_natural_class(id,name,note) VALUES(?,?,?)", newid, name, note);
        return newid;
    }

    /**
     * 删除
     *
     * @param id
     * @returnint
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_natural_class where id=?", id);
    }

    @Override
    public int deleteByName(String name) {
        return jdbcTemplate.update("DELETE from  t_natural_class where name=?", name);
    }

    @Override
    public int deleteByLikeName(String name) {
        return jdbcTemplate.update("DELETE from  t_natural_class where name like ?", "%" + name.trim() + "%");
    }

    /**
     * 删除全部
     *
     * @return
     */
    @Override
    public int deleteAll() {
        return jdbcTemplate.update("truncate t_natural_class");
    }

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param note
     * @return
     */
    @Override
    public int update(String id, String name, String note) {
        if (id == null || name == null) return 0;

        return jdbcTemplate.update("update t_natural_class set name=?,note=? WHERE id=?", name, note, id);
    }

    @Override
    public int updateName(String id, String name) {
        if (id == null || name == null) return 0;

        return jdbcTemplate.update("update t_natural_class set name=?  WHERE id=?", name, id);
    }

    @Override
    public int updateNote(String id, String note) {
        if (id == null) return 0;

        return jdbcTemplate.update("update t_natural_class set note=? WHERE id=?", note, id);
    }

    /**
     * 更新
     *
     * @param naturalClass
     * @return
     */
    @Override
    public int update(NaturalClass naturalClass) {
        if (naturalClass == null || naturalClass.getId() == null) return 0;

        return jdbcTemplate.update("update t_natural_class set name=?,note=? WHERE id=?", naturalClass.getName(), naturalClass.getNote(), naturalClass.getId());
    }

    /**
     * 得到所有数据总数
     *
     * @return
     */
    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from t_natural_class", Integer.class);
    }

    private int getLikeNameCount(String name) {
        name = "%" + name.trim() + "%";
        return jdbcTemplate.queryForObject("select count(*) from t_natural_class where name like ?", new
                Object[]{name}, Integer.class);
    }

    /**
     * 得到包含name的所有数据
     *
     * @param name
     * @return
     */
    @Override
    public List<NaturalClass> getAllByLikeName(String name) {
        if (name == null || name.length() == 0) return getAll();

        if (getLikeNameCount(name) == 0) return null;

        name = "%" + name.trim() + "%";

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class WHERE name like ?", new
                Object[]{name}, Integer.class) == 0) return null;

        return jdbcTemplate.query("select * from t_natural_class where name like ?", new Object[]{name}, new NaturalClassMapper());
    }

    @Override
    public NaturalClass getByName(String name) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class WHERE name=?", new
                Object[]{name}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_natural_class WHERE name=?", new Object[]{name}, new NaturalClassMapper());
    }

    /**
     * 根据Id获得对象
     *
     * @param id
     * @return
     */
    @Override
    public NaturalClass getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_natural_class WHERE id=?", new Object[]{id}, new NaturalClassMapper());
    }

    /**
     * 获得所有数据
     *
     * @return
     */
    @Override
    public List<NaturalClass> getAll() {
        if (getCount() == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_natural_class", new NaturalClassMapper());
    }

    /*
     * 分页查询
     * @PageBegin 开始页面，从0开始
     * */
    private List<NaturalClass> PageQuery(int PageBegin, int PageSize) {
        return jdbcTemplate.query("select * from t_natural_class order by name asc limit ?,? ", new Object[]{PageBegin * PageSize, PageSize}, new NaturalClassMapper());
    }

    /**
     * 获取学院
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    @Override
    public Page<NaturalClass> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();


        //实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<NaturalClass> data = PageQuery(pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    /**
     * 所有数据位于同一页面
     *
     * @return
     */
    @Override
    public Page<NaturalClass> getAllPage() {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();


        List<NaturalClass> data = getAll();

        return new Page<>(0, totalCount, (int) totalCount, data);

    }

    /*
     * 分页查询
     * @PageBegin 开始页面，从0开始
     * */
    private List<NaturalClass> PageQuery(String name, int PageBegin, int PageSize) {
        name = "%" + name.trim() + "%";
        return jdbcTemplate.query("select * from t_natural_class where name like ? order by name asc limit ?,? ", new Object[]{PageBegin * PageSize, PageSize}, new NaturalClassMapper());
    }

    /**
     * 得到包含name的分页查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<NaturalClass> getPageByLikeName(String name, int pageNo, int pageSize) {
        long totalCount = getLikeNameCount(name);
        if (totalCount < 1) return new Page<>();


        //实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<NaturalClass> data = PageQuery(name, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);
    }

    private static final class NaturalClassMapper implements RowMapper<NaturalClass> {

        @Override
        public NaturalClass mapRow(ResultSet rs, int rowNum) throws SQLException {
            NaturalClass naturalClass = new NaturalClass();
            naturalClass.setId(rs.getString("id"));
            naturalClass.setName(rs.getString("name"));
            naturalClass.setNote(rs.getString("note"));

            return naturalClass;
        }
    }
}