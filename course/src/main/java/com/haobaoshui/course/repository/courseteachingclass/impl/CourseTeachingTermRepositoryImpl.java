package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingTerm;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingTermRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class CourseTeachingTermRepositoryImpl implements CourseTeachingTermRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingTermRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     */
    @Override
    public String add(int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks, Date week_begin) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_term(id,teaching_year_begin,teaching_year_end,teaching_term,weeks,week_begin) VALUES(?,?,?,?,?,?)", newid, teaching_year_begin, teaching_year_end, teaching_term, weeks, week_begin);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_term where id=?", id);

    }

    @Override
    public int update(String id, int teaching_year_begin, int teaching_year_end, int teaching_term, int weeks,
                      Date week_begin) {
        if (id == null) return 0;
        return jdbcTemplate.update("update t_course_teaching_term set teaching_year_begin=?,teaching_year_end=?,teaching_term,weeks=?,week_begin=? WHERE id=?", teaching_year_begin, teaching_year_end, teaching_term, weeks, week_begin, id);


    }

    @Override
    public boolean isTermExist(int teaching_year_begin, int teaching_year_end, int teaching_term) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_term WHERE teaching_year_begin=? and teaching_year_end=? and teaching_term=?",
                new Object[]{teaching_year_begin, teaching_year_end, teaching_term},
                Integer.class) > 0;
    }

    @Override
    public CourseTeachingTerm getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_term WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_term WHERE id=?", new Object[]{id}, new CourseTeachingTermMapper());
    }

    @Override
    public List<CourseTeachingTerm> getAll() {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_term ", Integer.class) == 0)
            return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_term", new CourseTeachingTermMapper());
    }

    /**
     * 总数
     */
    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_term", Long.class);
    }

    /**
     * 分页查询
     */
    private List<CourseTeachingTerm> PageQuery(int PageBegin, int PageSize) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_term order by teaching_year_begin,teaching_term DESC limit ?,? ", new Object[]{PageBegin * PageSize, PageSize}, Integer.class) == 0)
            return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_term order by teaching_year_begin,teaching_term DESC limit ?,?", new Object[]{PageBegin * PageSize, PageSize}, new CourseTeachingTermMapper());


    }

    /**
     * 分页查询
     */
    @Override
    public Page<CourseTeachingTerm> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingTerm> data = PageQuery(pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingTermMapper implements RowMapper<CourseTeachingTerm> {

        @Override
        public CourseTeachingTerm mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingTerm courseTeachingTerm = new CourseTeachingTerm();
            courseTeachingTerm.setTeachingYearBegin(rs.getInt("teaching_year_begin"));
            courseTeachingTerm.setTeachingYearEnd(rs.getInt("teaching_year_end"));
            courseTeachingTerm.setTeachingTerm(rs.getInt("teaching_term"));
            courseTeachingTerm.setWeeks(rs.getInt("weeks"));
            courseTeachingTerm.setWeekBegin(rs.getDate("week_begin"));

            return courseTeachingTerm;
        }
    }

}
