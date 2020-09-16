package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClass;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class CourseTeachingClassRepositoryImpl implements CourseTeachingClassRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     */
    @Override
    public String add(String t_course_id, String name, String t_course_teaching_term_id) {
        String id = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class(id,t_course_id,name,t_course_teaching_term_id) VALUES(?,?,?,?)", id, t_course_id, name, t_course_teaching_term_id);

        return id;
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class WHERE id=?", id);
    }

    /**
     * 删除
     */
    @Override
    public int deleteByCourseId(String t_course_id) {

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class WHERE t_course_id=?", t_course_id);
    }

    /**
     * 更新
     */
    @Override
    public int update(String id, String t_course_id, String name, String t_course_teaching_term_id) {


        return jdbcTemplate.update("update  t_course_teaching_class set t_course_id=?,name=?,t_course_teaching_term_id=?  where id=?", t_course_id, name, t_course_teaching_term_id, id);


    }

    /**
     * 更新
     */
    @Override
    public int update(String id, String name) {


        return jdbcTemplate.update("update  t_course_teaching_class set name=?  where id=?", name, id);


    }

    // 根据id
    @Override
    public CourseTeachingClass getCourseTeachingClassById(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class WHERE id=?", new Object[]{id}, new CourseTeachingClassMapper());


    }

    /**
     * 根据课程id
     */
    @Override
    public List<CourseTeachingClass> getCourseTeachingClassByCourseId(String t_course_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class WHERE t_course_id=?", new
                Object[]{t_course_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class WHERE t_course_id=?", new Object[]{t_course_id}, new CourseTeachingClassMapper());

    }

    /**
     * 根据课程id和教学班id
     */
    @Override
    public List<CourseTeachingClass> getCourseTeachingClassByCourseIdTeachingClassId(String t_course_id,
                                                                                     String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class WHERE t_course_id=? and id=?", new
                Object[]{t_course_id, id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class WHERE t_course_id=? and id=?", new Object[]{t_course_id, id}, new CourseTeachingClassMapper());

    }

    /**
     * 根据学年学期
     */
    @Override
    public List<CourseTeachingClass> getCourseTeachingClassByCourseTeachingTermId(String t_course_teaching_term_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class WHERE t_course_teaching_term_id=? and id=?", new
                Object[]{t_course_teaching_term_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class WHERE t_course_teaching_term_id=? and id=?", new Object[]{t_course_teaching_term_id}, new CourseTeachingClassMapper());


    }

    /**
     *
     */
    @Override
    public long getCount() {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class", Long.class);
    }

    /**
     *
     */
    private List<CourseTeachingClass> PageQuery(int PageBegin, int PageSize) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class limit ?,?", new
                Object[]{PageBegin * PageSize, PageSize}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class limit ?,?", new Object[]{PageBegin * PageSize, PageSize}, new CourseTeachingClassMapper());


    }

    /**
     *
     */
    @Override
    public Page<CourseTeachingClass> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClass> data = PageQuery(pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassMapper implements RowMapper<CourseTeachingClass> {

        @Override
        public CourseTeachingClass mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClass courseTeachingClass = new CourseTeachingClass();
            courseTeachingClass.setId(rs.getString("id"));
            courseTeachingClass.setCourseId(rs.getString("t_course_id"));
            courseTeachingClass.setName(rs.getString("name"));
            courseTeachingClass.setCourseTeachingTermId(rs.getString("t_course_teaching_term_id"));

            return courseTeachingClass;
        }
    }

}
