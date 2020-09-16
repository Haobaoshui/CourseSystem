package com.haobaoshui.course.repository.organization.impl;

import com.haobaoshui.course.model.organization.DepartmentTeacher;
import com.haobaoshui.course.repository.organization.DepartmentTeacherRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentTeacherRepositoryImpl implements DepartmentTeacherRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentTeacherRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /*增加*/
    @Override
    public String add(String t_department_id, String t_teacher_id) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_department_teacher(id,t_department_id,t_teacher_id) VALUES(?,?,?)", newid, t_department_id, t_teacher_id);
        return newid;

    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_department_teacher where id=?", id);

    }

    @Override
    public int deleteByTeacherId(String t_teacher_id) {
        return jdbcTemplate.update("DELETE from  t_department_teacher where t_teacher_id=?", t_teacher_id);

    }

    @Override
    public int deleteByDepartmentId(String t_department_id) {
        return jdbcTemplate.update("DELETE from  t_department_teacher where t_department_id=?", t_department_id);
    }

    /**
     *
     */
    @Override
    public String getDepartmentIdByTeacherId(String t_teacher_id) {

        if (jdbcTemplate.queryForObject("select count(*) FROM t_department_teacher WHERE t_teacher_id=?", new
                Object[]{t_teacher_id}, Integer.class) == 0) return null;

        return jdbcTemplate.queryForObject("SELECT t_department_id FROM t_department_teacher WHERE t_teacher_id=?", new
                Object[]{t_teacher_id}, String.class);


    }


    /*根据用户ID得到用户
     * */
    @Override
    public DepartmentTeacher getById(String id) {

        if (jdbcTemplate.queryForObject("select count(*) FROM t_department_teacher WHERE id=?", new
                Object[]{id}, Integer.class) == 0) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_course_type WHERE id=?", new Object[]{id}, new DepartmentTeacherMapper());
    }

    /**
     * 得到指定部门的全部教师id
     */
    @Override
    public List<DepartmentTeacher> getByDepartmentId(String t_department_id) {

        if (jdbcTemplate.queryForObject("select count(*) FROM t_department_teacher WHERE t_department_id=?", new
                Object[]{t_department_id}, Integer.class) == 0) return null;


        return jdbcTemplate.query("SELECT * FROM t_department_teacher WHERE t_department_id=?",
                new Object[]{t_department_id}, new DepartmentTeacherMapper());


    }

    private static final class DepartmentTeacherMapper implements RowMapper<DepartmentTeacher> {

        @Override
        public DepartmentTeacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            DepartmentTeacher departmentTeacher = new DepartmentTeacher();
            departmentTeacher.setId(rs.getString("id"));
            departmentTeacher.setDepartmentId(rs.getString("t_department_id"));
            departmentTeacher.setTeacherId(rs.getString("t_teacher_id"));

            return departmentTeacher;
        }
    }


}
