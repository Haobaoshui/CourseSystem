package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopic;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumTopicRepository;
import com.haobaoshui.course.utility.DateTimeSql;
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
public class CourseTeachingClassForumTopicRepositoryImpl implements CourseTeachingClassForumTopicRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassForumTopicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(String t_course_teaching_class_id, String t_user_id, String title, String content, String created_date,
                      String last_modified_date) {
        return add(t_course_teaching_class_id, t_user_id, title, content, DateTimeSql.GetDateTime(created_date),
                DateTimeSql.GetDateTime(last_modified_date));
    }

    @Override
    public String add(String t_course_teaching_class_id, String t_user_id, String title, String content, Date created_date,
                      Date last_modified_date) {
        String id = GUID.getGUID();

        Object[] params = new Object[]{id, t_course_teaching_class_id, t_user_id, title, content, created_date, last_modified_date, 0, 0};

        final String insertSQL = "INSERT INTO t_course_teaching_class_forum_topic(id,t_course_teaching_class_id,t_user_id ,title,content,created_date,last_modified_date,view_count,flag) VALUES(?,?,?,?,?,?,?,?,?)";


        jdbcTemplate.update(insertSQL, params);
        return id;
    }

    @Override
    public int deleteById(String id) {
        Object[] params = new Object[]{id};

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_forum_topic WHERE id=?", params);
    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        Object[] params = new Object[]{t_course_teaching_class_id};

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=?", params);
    }

    @Override
    public int deleteByUserId(String t_user_id) {
        Object[] params = new Object[]{t_user_id};

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_forum_topic WHERE t_user_id=?", params);
    }

    @Override
    public int update(String id, String t_user_id, String title, String content, String last_modified_date) {
        return update(id, t_user_id, title, content, DateTimeSql.GetDateTime(last_modified_date));
    }

    @Override
    public int update(String id, String t_user_id, String title, String content, Date last_modified_date) {
        if (id == null || t_user_id == null || title == null) return 0;

        Object[] params = new Object[]{t_user_id, title, content, last_modified_date, id};
        jdbcTemplate.update("update t_course_teaching_class_forum_topic set t_user_id=?,title=?,content=? ,last_modified_date=? WHERE id=?", params);

        return incViewCount(id);
    }

    /**
     * 将浏览次数增1
     */
    @Override
    public int incViewCount(String id) {
        if (id == null) return 0;
        // System.out.println(id);
        long n = getViewCount(id);

        Object[] params = new Object[]{n + 1, id};
        return jdbcTemplate.update("update t_course_teaching_class_forum_topic set view_count=? WHERE id=?", params);
    }

    /**
     * 得到浏览次数
     */
    @Override
    public long getViewCount(String id) {
        final String GET_VIEW_COUNT_BY_ID = "SELECT view_count FROM t_course_teaching_class_forum_topic WHERE id=?";
        return jdbcTemplate.queryForObject(GET_VIEW_COUNT_BY_ID, new Object[]{id}, new int[]{Types.VARCHAR}, Long.class);
    }

    /**
     * 得到标记
     */
    @Override
    public long getFlag(String id) {
        final String GET_FLAG_BY_ID = "SELECT flag FROM t_course_teaching_class_forum_topic WHERE id=?";
        return jdbcTemplate.queryForObject(GET_FLAG_BY_ID, new Object[]{id}, new int[]{Types.VARCHAR}, Long.class);
    }

    /**
     * 设计标记
     */
    @Override
    public int setFlag(String id, int flag) {
        Object[] params = new Object[]{flag, id};
        return jdbcTemplate.update("update t_course_teaching_class_forum_topic set flag=? WHERE id=?", params);
    }

    @Override
    public long getCount(String t_group_id) {
        final String GET_COUNT_BY_COURSE_TEACHING_CLASS_ID = "SELECT count(*) FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=?";

        return jdbcTemplate.queryForObject(GET_COUNT_BY_COURSE_TEACHING_CLASS_ID, new Object[]{t_group_id},
                Long.class);
    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassForumTopic getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_topic WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_forum_topic WHERE id=?", new Object[]{id}, new CourseTeachingClassForumTopicMapper());


    }


    /**
     * 根据教学班得到论坛id
     */
    @Override
    public List<CourseTeachingClassForumTopic> getByCourseTeachingClassID(String t_course_teaching_class_id) {


        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassForumTopicMapper());


    }

    /**
     * 根据t_user_id得到论坛id
     */
    @Override
    public List<CourseTeachingClassForumTopic> getByUserID(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_topic WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_topic WHERE t_user_id=?", new Object[]{t_user_id}, new CourseTeachingClassForumTopicMapper());


    }

    /**
     * 根据t_course_teaching_class_id和t_user_id得到论坛id
     */
    @Override
    public List<CourseTeachingClassForumTopic> getByCourseTeachingClassIDAndUserID(String t_course_teaching_class_id, String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=? and t_user_id=?", new
                Object[]{t_course_teaching_class_id, t_user_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=? and t_user_id=?", new Object[]{t_course_teaching_class_id, t_user_id}, new CourseTeachingClassForumTopicMapper());


    }

    private List<CourseTeachingClassForumTopic> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {

        final String countSQL = "SELECT count(*) FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=? order by created_date desc limit ?,?";

        if (jdbcTemplate.queryForObject(countSQL, new
                Object[]{t_course_teaching_class_id}, Integer.class) == 0) return null;

        final String querySQL = "SELECT * FROM t_course_teaching_class_forum_topic WHERE t_course_teaching_class_id=? order by created_date desc limit ?,?";

        return jdbcTemplate.query(querySQL,
                new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize}, new CourseTeachingClassForumTopicMapper());

    }

    @Override
    public Page<CourseTeachingClassForumTopic> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassForumTopic> data = PageQuery(t_course_teaching_class_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassForumTopicMapper implements RowMapper<CourseTeachingClassForumTopic> {

        @Override
        public CourseTeachingClassForumTopic mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassForumTopic courseTeachingClassForumTopic = new CourseTeachingClassForumTopic();
            courseTeachingClassForumTopic.setId(rs.getString("id"));
            courseTeachingClassForumTopic.setCourseTeachingClassId(rs.getString("t_course_teaching_class_id"));
            courseTeachingClassForumTopic.setUserId(rs.getString("t_user_id"));
            courseTeachingClassForumTopic.setCreatedDate(DateTimeSql.GetDateTime(rs.getString("created_date")));
            courseTeachingClassForumTopic.setLastModifiedDate(DateTimeSql.GetDateTime(rs.getString("last_modified_date")));
            courseTeachingClassForumTopic.setTitle(rs.getString("title"));
            courseTeachingClassForumTopic.setContent(rs.getString("content"));
            courseTeachingClassForumTopic.setViewCount(Integer.parseInt(rs.getString("view_count")));
            courseTeachingClassForumTopic.setFlag(Integer.parseInt(rs.getString("flag")));

            return courseTeachingClassForumTopic;
        }
    }
}
