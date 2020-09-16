package com.haobaoshui.course.repository.attendance.impl;


import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceState;
import com.haobaoshui.course.repository.attendance.AttendanceStateRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AttendanceStateRepositoryImpl implements AttendanceStateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AttendanceStateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     *
     * @param attendanceState
     * @return
     */
    @Override
    public String add(AttendanceState attendanceState) {

        if (attendanceState == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_attendance_state(id,name,note) VALUES(?,?,?)", newid, attendanceState.getName(), attendanceState.getNote());
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
        jdbcTemplate.update("INSERT INTO t_attendance_state(id,name,note) VALUES(?,?,?)", newid, name, note);
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
        return jdbcTemplate.update("DELETE from  t_attendance_state where id=?", id);
    }

    @Override
    public int deleteByName(String name) {
        return jdbcTemplate.update("DELETE from  t_attendance_state where name=?", name);
    }

    @Override
    public int deleteByLikeName(String name) {
        return jdbcTemplate.update("DELETE from  t_attendance_state where name like ?", "%" + name.trim() + "%");
    }

    /**
     * 删除全部
     *
     * @return
     */
    @Override
    public int deleteAll() {
        return jdbcTemplate.update("truncate t_attendance_state");
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

        return jdbcTemplate.update("update t_attendance_state set name=?,note=? WHERE id=?", name, note, id);
    }

    @Override
    public int updateName(String id, String name) {
        if (id == null || name == null) return 0;

        return jdbcTemplate.update("update t_attendance_state set name=?  WHERE id=?", name, id);
    }

    @Override
    public int updateNote(String id, String note) {
        if (id == null) return 0;

        return jdbcTemplate.update("update t_attendance_state set note=? WHERE id=?", note, id);
    }

    /**
     * 更新
     *
     * @param attendanceMode
     * @return
     */
    @Override
    public int update(AttendanceState attendanceMode) {
        if (attendanceMode == null || attendanceMode.getId() == null) return 0;

        return jdbcTemplate.update("update t_attendance_state set name=?,note=? WHERE id=?", attendanceMode.getName(), attendanceMode.getNote(), attendanceMode.getId());
    }

    /**
     * 得到所有数据总数
     *
     * @return
     */
    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from t_attendance_state", Integer.class);
    }

    private int getLikeNameCount(String name) {
        name = "%" + name.trim() + "%";
        return jdbcTemplate.queryForObject("select count(*) from t_attendance_state where name like ?", new
                Object[]{name}, Integer.class);
    }

    /**
     * 得到包含name的所有数据
     *
     * @param name
     * @return
     */
    @Override
    public List<AttendanceState> getAllByLikeName(String name) {
        if (name == null || name.length() == 0) return getAll();

        if (getLikeNameCount(name) == 0) return null;

        name = "%" + name.trim() + "%";

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance_state WHERE name like ?", new
                Object[]{name}, Integer.class) != 1) return null;

        return jdbcTemplate.query("select * from t_attendance_state where name like ?", new Object[]{name}, new AttendanceStateMapper());
    }

    @Override
    public AttendanceState getByName(String userName) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance_state WHERE name=?", new
                Object[]{userName}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_attendance_state WHERE name=?", new Object[]{userName}, new AttendanceStateMapper());
    }

    /**
     * 根据Id获得对象
     *
     * @param id
     * @return
     */
    @Override
    public AttendanceState getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance_state WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_attendance_state WHERE id=?", new Object[]{id}, new AttendanceStateMapper());
    }

    /**
     * 获得所有数据
     *
     * @return
     */
    @Override
    public List<AttendanceState> getAll() {
        if (getCount() == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_attendance_state", new AttendanceStateMapper());
    }

    /*
     * 分页查询
     * @PageBegin 开始页面，从0开始
     * */
    private List<AttendanceState> PageQuery(int PageBegin, int PageSize) {
        return jdbcTemplate.query("select * from t_attendance_state order by name asc limit ?,? ", new Object[]{PageBegin * PageSize, PageSize}, new AttendanceStateMapper());
    }

    /**
     * 获取学院
     *
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    @Override
    public Page<AttendanceState> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();


        //实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<AttendanceState> data = PageQuery(pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    /**
     * 所有数据位于同一页面
     *
     * @return
     */
    @Override
    public Page<AttendanceState> getAllPage() {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();


        List<AttendanceState> data = getAll();

        return new Page<>(0, totalCount, (int) totalCount, data);

    }

    /*
     * 分页查询
     * @PageBegin 开始页面，从0开始
     * */
    private List<AttendanceState> PageQuery(String name, int PageBegin, int PageSize) {
        name = "%" + name.trim() + "%";
        return jdbcTemplate.query("select * from t_attendance_state where name like ? order by name asc limit ?,? ", new Object[]{PageBegin * PageSize, PageSize}, new AttendanceStateMapper());
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
    public Page<AttendanceState> getPageByLikeName(String name, int pageNo, int pageSize) {
        long totalCount = getLikeNameCount(name);
        if (totalCount < 1) return new Page<>();


        //实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<AttendanceState> data = PageQuery(name, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);
    }

    private static final class AttendanceStateMapper implements RowMapper<AttendanceState> {

        @Override
        public AttendanceState mapRow(ResultSet rs, int rowNum) throws SQLException {
            AttendanceState attendanceState = new AttendanceState();
            attendanceState.setId(rs.getString("id"));
            attendanceState.setName(rs.getString("name"));
            attendanceState.setNote(rs.getString("note"));

            return attendanceState;
        }
    }
}
