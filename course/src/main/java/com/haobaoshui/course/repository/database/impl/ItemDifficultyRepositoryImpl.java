package com.haobaoshui.course.repository.database.impl;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ItemDifficulty;
import com.haobaoshui.course.repository.database.ItemDifficultyRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class ItemDifficultyRepositoryImpl implements ItemDifficultyRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemDifficultyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     *
     * @param itemDifficulty
     * @return
     */
    @Override
    public String add(ItemDifficulty itemDifficulty) {

        if (itemDifficulty == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_item_difficulty(id,name,note) VALUES(?,?,?)", newid, itemDifficulty.getName(), itemDifficulty.getNote());
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
        jdbcTemplate.update("INSERT INTO t_item_difficulty(id,name,note) VALUES(?,?,?)", newid, name, note);
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
        return jdbcTemplate.update("DELETE from  t_item_difficulty where id=?", id);
    }

    @Override
    public int deleteByName(String name) {
        return jdbcTemplate.update("DELETE from  t_item_difficulty where name=?", name);
    }

    @Override
    public int deleteByLikeName(String name) {
        return jdbcTemplate.update("DELETE from  t_item_difficulty where name like ?", "%" + name.trim() + "%");
    }

    /**
     * 删除全部
     *
     * @return
     */
    @Override
    public int deleteAll() {
        return jdbcTemplate.update("truncate t_item_difficulty");
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

        return jdbcTemplate.update("update t_item_difficulty set name=?,note=? WHERE id=?", name, note, id);
    }

    @Override
    public int updateName(String id, String name) {
        if (id == null || name == null) return 0;

        return jdbcTemplate.update("update t_item_difficulty set name=?  WHERE id=?", name, id);
    }

    @Override
    public int updateNote(String id, String note) {
        if (id == null) return 0;

        return jdbcTemplate.update("update t_item_difficulty set note=? WHERE id=?", note, id);
    }

    /**
     * 更新
     *
     * @param itemDifficulty
     * @return
     */
    @Override
    public int update(ItemDifficulty itemDifficulty) {
        if (itemDifficulty == null || itemDifficulty.getId() == null) return 0;

        return jdbcTemplate.update("update t_item_difficulty set name=?,note=? WHERE id=?", itemDifficulty.getName(), itemDifficulty.getNote(), itemDifficulty.getId());
    }

    /**
     * 得到所有数据总数
     *
     * @return
     */
    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from t_item_difficulty", Integer.class);
    }

    private int getLikeNameCount(String name) {
        name = "%" + name.trim() + "%";
        return jdbcTemplate.queryForObject("select count(*) from t_item_difficulty where name like ?", new
                Object[]{name}, Integer.class);
    }

    /**
     * 得到包含name的所有数据
     *
     * @param name
     * @return
     */
    @Override
    public List<ItemDifficulty> getAllByLikeName(String name) {
        if (name == null || name.length() == 0) return getAll();

        if (getLikeNameCount(name) == 0) return null;

        name = "%" + name.trim() + "%";

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_item_difficulty WHERE name like ?", new
                Object[]{name}, Integer.class) == 0) return null;

        return jdbcTemplate.query("select * from t_item_difficulty where name like ?", new Object[]{name}, new ItemDifficultyMapper());
    }

    @Override
    public ItemDifficulty getByName(String userName) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_item_difficulty WHERE name=?", new
                Object[]{userName}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_item_difficulty WHERE name=?", new Object[]{userName}, new ItemDifficultyMapper());
    }

    /**
     * 根据Id获得对象
     *
     * @param id
     * @return
     */
    @Override
    public ItemDifficulty getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_item_difficulty WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_item_difficulty WHERE id=?", new Object[]{id}, new ItemDifficultyMapper());
    }

    /**
     * 获得所有数据
     *
     * @return
     */
    @Override
    public List<ItemDifficulty> getAll() {
        if (getCount() == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_item_difficulty", new ItemDifficultyMapper());
    }

    /*
     * 分页查询
     * @PageBegin 开始页面，从0开始
     * */
    private List<ItemDifficulty> PageQuery(int PageBegin, int PageSize) {

        if (getCount() == 0) return null;

        return jdbcTemplate.query("select * from t_item_difficulty order by name asc limit ?,? ", new Object[]{PageBegin * PageSize, PageSize}, new ItemDifficultyMapper());
    }

    /**
     * 获取学院
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    @Override
    public Page<ItemDifficulty> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();


        //实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<ItemDifficulty> data = PageQuery(pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    /**
     * 所有数据位于同一页面
     *
     * @return
     */
    @Override
    public Page<ItemDifficulty> getAllPage() {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();


        List<ItemDifficulty> data = getAll();

        return new Page<>(0, totalCount, (int) totalCount, data);

    }

    /*
     * 分页查询
     * @PageBegin 开始页面，从0开始
     * */
    private List<ItemDifficulty> PageQuery(String name, int PageBegin, int PageSize) {
        name = "%" + name.trim() + "%";
        return jdbcTemplate.query("select * from t_item_difficulty where name like ? order by name asc limit ?,? ", new Object[]{PageBegin * PageSize, PageSize}, new ItemDifficultyMapper());
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
    public Page<ItemDifficulty> getPageByLikeName(String name, int pageNo, int pageSize) {
        long totalCount = getLikeNameCount(name);
        if (totalCount < 1) return new Page<>();


        //实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<ItemDifficulty> data = PageQuery(name, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);
    }

    private static final class ItemDifficultyMapper implements RowMapper<ItemDifficulty> {

        @Override
        public ItemDifficulty mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemDifficulty itemDifficulty = new ItemDifficulty();
            itemDifficulty.setId(rs.getString("id"));
            itemDifficulty.setName(rs.getString("name"));
            itemDifficulty.setNote(rs.getString("note"));

            return itemDifficulty;
        }
    }
}