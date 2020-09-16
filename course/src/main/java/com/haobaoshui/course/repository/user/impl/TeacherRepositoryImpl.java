package com.haobaoshui.course.repository.user.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.user.Teacher;
import com.haobaoshui.course.repository.user.TeacherRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加教师 */
    @Override
    public String add(Department department, Teacher teacher) {

        if (department == null) return null;

        if (teacher == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_teacher(id,t_user_id,teacher_num) VALUES(?,?,?)", newid, teacher.getUserId(), teacher.getTeacherNum());
        jdbcTemplate.update("INSERT INTO t_department_teacher(id,t_department_id,t_teacher_id) VALUES(?,?,?)", newid, department.getId(), newid);
        return newid;


    }

    @Override
    public int UpdateTeacherNumById(String t_teacher_id, String teacher_num) {
        if (t_teacher_id == null || teacher_num == null) return 0;

        return jdbcTemplate.update("update t_teacher set teacher_num=? WHERE id=?", teacher_num, t_teacher_id);
    }

    /*
     * 删除
     */
    @Override
    public int deleteById(String t_teacher_id) {
        return jdbcTemplate.update("DELETE FROM t_teacher WHERE id=?", t_teacher_id);

    }

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_teacher", Integer.class);
    }

    /**
     * 根据教师id得到教师所在部门id
     */
    @Override
    public String getDepartmentIdByTeacherId(String t_teacher_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_department_teacher WHERE t_teacher_id=?", new
                Object[]{t_teacher_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT t_department_id FROM t_department_teacher WHERE t_teacher_id=?", new Object[]{t_teacher_id}, String.class);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public Teacher getTeacherByID(String t_teacher_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_teacher WHERE id=?", new
                Object[]{t_teacher_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_teacher WHERE id=?", new Object[]{t_teacher_id}, new TeacherMapper());

    }

    /*
     * 根据教师工号ID得到用户
     */
    @Override
    public Teacher getTeacherByTeacherNum(String teacher_num) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_teacher WHERE teacher_num=?", new
                Object[]{teacher_num}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_teacher WHERE teacher_num=?", new Object[]{teacher_num}, new TeacherMapper());
    }

    /*
     * 根据t_user_id得到用户
     */
    @Override
    public Teacher getTeacherByUserId(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_teacher WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_teacher WHERE t_user_id=?", new Object[]{t_user_id}, new TeacherMapper());


    }

    /*
     * 根据t_user_id得到用户
     */
    private List<Teacher> PageQuery(String t_school_id, int PageBegin, int PageSize) {

        if (getCount(t_school_id) == 0) return null;


        return jdbcTemplate.query("select *  from t_teacher  where id in("
                + " select t_teacher_id from t_department_teacher where t_department_id in( "
                + " select t_department_id from t_department_teacher where t_department_id in(select t_department_id from t_school_department where t_school_id=?)))"
                + " order by teacher_num    limit ?,?", new Object[]{t_school_id, PageBegin * PageSize, PageSize,}, new TeacherMapper());
    }

    /*
     * 得到学院总数
     */
    public long getCount(String t_school_id) {

        return jdbcTemplate.queryForObject("select count(*) from t_teacher  where id in("
                + " select t_teacher_id from t_department_teacher where t_department_id in( "
                + " select t_department_id from t_department_teacher where t_department_id in(select t_department_id from t_school_department where t_school_id=?)))", new Object[]{t_school_id}, Long.class);
    }

    /**
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    @Override
    public Page<Teacher> getPage(String t_school_id, int pageNo, int pageSize) {

        long totalCount = getCount(t_school_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<Teacher> data = PageQuery(t_school_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class TeacherMapper implements RowMapper<Teacher> {

        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            Teacher teacher = new Teacher();
            teacher.setId(rs.getString("id"));


            teacher.setUserId(rs.getString("t_user_id"));
            teacher.setTeacherNum(rs.getString("teacher_num"));
            return teacher;
        }
    }


}
