package com.haobaoshui.course.repository.course.impl;

import com.haobaoshui.course.model.course.CoursePrecourse;
import com.haobaoshui.course.repository.course.CoursePrecourseRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CoursePrecourseRepositoryImpl implements CoursePrecourseRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CoursePrecourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     *
     * @param coursePrecourse
     * @return
     */
    @Override
    public String add(CoursePrecourse coursePrecourse) {
        if (coursePrecourse == null || coursePrecourse.getCourseIdThis() == null || coursePrecourse.getCourseIdPre() == null)
            return null;
        return add(coursePrecourse.getCourseIdThis(), coursePrecourse.getCourseIdPre());
    }

    /**
     * 增加
     *
     * @param t_course_id_this
     * @param t_course_id_pre
     * @return
     */
    @Override
    public String add(String t_course_id_this, String t_course_id_pre) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_precourse( id,t_course_id_this,t_course_id_pre) VALUES(?,?,?)", newid, t_course_id_this, t_course_id_pre);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_precourse where id=?", id);

    }

    @Override
    public int deleteByCourseId(String t_course_id) {
        return jdbcTemplate.update("DELETE from  t_precourse where t_course_id_this=?", t_course_id);
    }

    @Override
    public int update(CoursePrecourse coursePrecourse) {
        if (coursePrecourse == null || coursePrecourse.getId() == null || coursePrecourse.getCourseIdThis() == null || coursePrecourse.getCourseIdPre() == null)
            return 0;
        return update(coursePrecourse.getId(), coursePrecourse.getCourseIdThis(), coursePrecourse.getCourseIdPre());
    }

    @Override
    public int update(String id, String t_course_id_this, String t_course_id_pre) {
        if (id == null) return 0;

        return jdbcTemplate.update("update t_precourse set  t_course_id_this=?, t_course_id_pre=?  WHERE id=?", t_course_id_this, t_course_id_pre, id);


    }

    @Override
    public CoursePrecourse getById(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_precourse WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_precourse WHERE id=?", new Object[]{id}, new CoursePrecourseMapper());


    }

    @Override
    public List<CoursePrecourse> getPreCourse(String t_course_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_precourse WHERE t_course_id=?", new
                Object[]{t_course_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_precourse WHERE t_course_id=?", new CoursePrecourseMapper());
    }

    private static final class CoursePrecourseMapper implements RowMapper<CoursePrecourse> {

        @Override
        public CoursePrecourse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CoursePrecourse coursePrecourse = new CoursePrecourse();
            coursePrecourse.setId(rs.getString("id"));
            coursePrecourse.setCourseIdThis(rs.getString("t_course_id_this"));
            coursePrecourse.setCourseIdPre(rs.getString("t_course_id_pre"));
            return coursePrecourse;
        }
    }


}
