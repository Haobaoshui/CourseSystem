package com.haobaoshui.course.repository.course.impl;

import com.haobaoshui.course.model.course.CourseDepartment;
import com.haobaoshui.course.repository.course.CourseDepartmentRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseDepartmentRepositoryImpl implements CourseDepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDepartmentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加 */
    @Override
    public String add(String t_course_id, String t_department_id) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_department(id,t_course_id,t_department_id) VALUES(?,?,?)", newid, t_course_id, t_department_id);
        return newid;


    }

    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("DELETE from  t_course_department where id=?", id);

    }

    @Override
    public int deleteByCourseId(String t_course_id) {
        return jdbcTemplate.update("DELETE from  t_course_department where t_course_id=?", t_course_id);
    }

    @Override
    public int deleteByDepartmentId(String t_department_id) {
        return jdbcTemplate.update("DELETE from  t_course_department where id=?", t_department_id);
    }

    @Override
    public int update(String id, String t_course_id_pre, String t_department_id) {
        if (id == null) return 0;

        return jdbcTemplate.update("update t_course_department set  t_course_id=?, t_department_id=?  WHERE id=?", t_course_id_pre, t_department_id, id);

    }

    @Override
    public long getCount(String t_course_id) {
        return jdbcTemplate.queryForObject("select count(*) from t_course_department   where  t_course_id=?", new Object[]{t_course_id}, Integer.class);


    }

    @Override
    public CourseDepartment getById(String id) {

        if (jdbcTemplate.queryForObject("select count(*) from t_course_department   where  id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("select * from t_course_department   where  id=?", new Object[]{id}, new CourseDepartmentMapper());


    }

    @Override
    public List<CourseDepartment> getByCourseId(String t_course_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_department WHERE t_course_id=?", new
                Object[]{t_course_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("select * from t_course_department where t_course_id= ?", new Object[]{t_course_id}, new CourseDepartmentMapper());
    }

    @Override
    public List<CourseDepartment> getByDepartmentId(String t_department_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_department WHERE t_department_id=?", new
                Object[]{t_department_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("select * from t_course_department where t_department_id= ?", new Object[]{t_department_id}, new CourseDepartmentMapper());
    }

    private static final class CourseDepartmentMapper implements RowMapper<CourseDepartment> {

        @Override
        public CourseDepartment mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseDepartment courseDepartment = new CourseDepartment();
            courseDepartment.setId(rs.getString("id"));
            courseDepartment.setCourseId(rs.getString("t_course_id"));
            courseDepartment.setDepartmentId(rs.getString("t_department_id"));


            return courseDepartment;
        }
    }


}
