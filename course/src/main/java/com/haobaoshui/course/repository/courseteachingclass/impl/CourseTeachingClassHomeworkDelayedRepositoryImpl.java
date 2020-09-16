package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkDelayed;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkDelayedRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class CourseTeachingClassHomeworkDelayedRepositoryImpl implements CourseTeachingClassHomeworkDelayedRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassHomeworkDelayedRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, Date pubdate, Date enddate) {
        if (id == null || t_teacher_id == null || t_course_teaching_class_homework_baseinfo_id == null) return;
        final String updateSQL = "update t_course_teaching_class_homework_delayed set t_course_teaching_class_homework_baseinfo_id=?, t_teacher_id=?,  pubdate=?, enddate=? WHERE id=?";

        Object[] params = new Object[]{t_course_teaching_class_homework_baseinfo_id, t_teacher_id, pubdate, id};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.VARCHAR};
        jdbcTemplate.update(updateSQL, params, types);
    }

    @Override
    public void update(String id, String t_teacher_id, Date pubdate, Date enddate) {
        if (id == null || t_teacher_id == null) return;
        final String updateSQL = "update t_course_teaching_class_homework_delayed set t_teacher_id=?,  pubdate=?, enddate=? WHERE id=?";
        Object[] params = new Object[]{t_teacher_id, pubdate, enddate, id};
        int[] types = new int[]{Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.VARCHAR};
        jdbcTemplate.update(updateSQL, params, types);
    }

    @Override
    public void update(String id, Date enddate) {
        if (id == null) return;

        final String updateSQL = "update t_course_teaching_class_homework_delayed set enddate=? WHERE id=?";
        Object[] params = new Object[]{enddate, id};
        int[] types = new int[]{Types.TIMESTAMP, Types.VARCHAR};
        jdbcTemplate.update(updateSQL, params, types);
    }


    @Override
    public CourseTeachingClassHomeworkDelayed getByID(String id) {


        final String GET_BY_ID = "SELECT * FROM t_course_teaching_class_homework_delayed WHERE id=?";
        return jdbcTemplate.queryForObject(GET_BY_ID, new Object[]{id}, new CourseTeachingClassHomeworkDelayedMapper());

    }


    @Override
    public List<CourseTeachingClassHomeworkDelayed> getByCourseTeachingClassHomeworkBaseInfoID(
            String t_course_teaching_class_homework_baseinfo_id) {
        final String GET_BY_COURSE_TEACHING_CLASS_HOMEWORK_BASEINFO_ID = "SELECT id, t_teacher_id,pubdate, enddate FROM t_course_teaching_class_homework_delayed WHERE t_course_teaching_class_homework_baseinfo_id=?";


        return jdbcTemplate.query(GET_BY_COURSE_TEACHING_CLASS_HOMEWORK_BASEINFO_ID,
                new Object[]{t_course_teaching_class_homework_baseinfo_id}, new CourseTeachingClassHomeworkDelayedMapper());


    }


    @Override
    public String add(CourseTeachingClassHomeworkDelayed expriment) {
        String id = GUID.getGUID();
        expriment.setId(id);
        Object[] params = new Object[]{expriment.getId(), expriment.getCourseTeachingClassHomeworkBaseinfoId(),
                expriment.getTeacherId(), expriment.getPubdate(), expriment.getEnddate()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP};

        final String insertSQL = "INSERT INTO t_course_teaching_class_homework_delayed( id,t_course_teaching_class_homework_baseinfo_id, t_teacher_id,  pubdate, enddate) VALUES(?,?,?,?,?)";


        jdbcTemplate.update(insertSQL, params, types);
        return id;
    }

    @Override
    public String add(String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, Date pubdate, Date enddate) {

        String id = GUID.getGUID();

        Object[] params = new Object[]{id, t_course_teaching_class_homework_baseinfo_id, t_teacher_id, pubdate, enddate};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP};

        final String insertSQL = "INSERT INTO t_course_teaching_class_homework_delayed( id,t_course_teaching_class_homework_baseinfo_id, t_teacher_id,  pubdate, enddate) VALUES(?,?,?,?,?)";


        jdbcTemplate.update(insertSQL, params, types);
        return id;
    }

    @Override
    public void deleteById(String id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.VARCHAR};
        final String DELETE_BY_ID = "DELETE FROM t_course_teaching_class_homework_delayed WHERE id=?";

        jdbcTemplate.update(DELETE_BY_ID, params, types);
    }

    @Override
    public void deleteByCourseTeachingClassHomeworkBaseInfoId(String t_course_teaching_class_homework_baseinfo_id) {
        Object[] params = new Object[]{t_course_teaching_class_homework_baseinfo_id};
        int[] types = new int[]{Types.VARCHAR};

        final String DELETE_BY_COURSE_TEACHING_CLASS_HOMEWORK_BASEINFO_ID = "DELETE FROM t_course_teaching_class_homework_delayed WHERE t_course_teaching_class_homework_baseinfo_id=?";

        jdbcTemplate.update(DELETE_BY_COURSE_TEACHING_CLASS_HOMEWORK_BASEINFO_ID, params, types);
    }

    @Override
    public void deleteByTeacherId(String t_teacher_id) {
        Object[] params = new Object[]{t_teacher_id};
        int[] types = new int[]{Types.VARCHAR};
        final String DELETE_BY_TEACHER_ID = "DELETE FROM t_course_teaching_class_homework_delayed WHERE t_teacher_id=?";

        jdbcTemplate.update(DELETE_BY_TEACHER_ID, params, types);
    }

    @Override
    public long getCount(String t_group_id) {
        final String GET_COUNT_BY_COURSE_TEACHING_CLASS_ID = "SELECT count(*) FROM t_course_teaching_class_homework_delayed WHERE t_course_teaching_class_homework_baseinfo_id=?";

        return jdbcTemplate.queryForObject(GET_COUNT_BY_COURSE_TEACHING_CLASS_ID, new Object[]{t_group_id},
                new int[]{Types.VARCHAR}, Long.class);
    }

    private List<CourseTeachingClassHomeworkDelayed> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;


        final String countSQL = "SELECT count(*) FROM t_course_teaching_class_homework_delayed WHERE t_course_teaching_class_homework_baseinfo_id=? ";

        if (jdbcTemplate.queryForObject(countSQL, new
                Object[]{t_course_teaching_class_id}, Integer.class) != 1) return null;


        final String querySQL = "SELECT * FROM t_course_teaching_class_homework_delayed WHERE t_course_teaching_class_homework_baseinfo_id=?   limit ?,?";

        return jdbcTemplate.query(querySQL,
                new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize}, new CourseTeachingClassHomeworkDelayedMapper());

    }

    @Override
    public Page<CourseTeachingClassHomeworkDelayed> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkDelayed> data = PageQuery(t_course_teaching_class_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassHomeworkDelayedMapper implements RowMapper<CourseTeachingClassHomeworkDelayed> {

        @Override
        public CourseTeachingClassHomeworkDelayed mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkDelayed courseTeachingClassHomeworkDelayed = new CourseTeachingClassHomeworkDelayed();


            courseTeachingClassHomeworkDelayed.setId(rs.getString("id"));
            courseTeachingClassHomeworkDelayed.setCourseTeachingClassHomeworkBaseinfoId(rs.getString("t_course_teaching_class_homework_baseinfo_id"));
            courseTeachingClassHomeworkDelayed.setTeacherId(rs.getString("t_teacher_id"));

            courseTeachingClassHomeworkDelayed.setEnddate(rs.getTimestamp("enddate"));
            courseTeachingClassHomeworkDelayed.setPubdate(rs.getTimestamp("pubdate"));


            return courseTeachingClassHomeworkDelayed;
        }
    }
}
