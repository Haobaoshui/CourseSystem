package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReply;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumReplyRepository;
import com.haobaoshui.course.utility.DateTimeSql;
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
public class CourseTeachingClassForumReplyRepositoryImpl implements CourseTeachingClassForumReplyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassForumReplyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(String t_course_teaching_class_forum_topic_id, String t_user_id, String title, String content, String created_date,
                      String last_modified_date) {
        return add(t_course_teaching_class_forum_topic_id, t_user_id, title, content, DateTimeSql.GetDateTime(created_date),
                DateTimeSql.GetDateTime(last_modified_date));
    }

    @Override
    public String add(String t_course_teaching_class_forum_topic_id, String t_user_id, String title, String content, Date created_date, Date last_modified_date) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_forum_reply(id,t_course_teaching_class_forum_topic_id,t_user_id ,title,content,created_date,last_modified_date) VALUES(?,?,?,?,?,?,?)", newid, t_course_teaching_class_forum_topic_id, t_user_id, title, content, created_date, last_modified_date);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_forum_reply where id=?", id);
    }

    @Override
    public int deleteByForumTopicId(String t_course_teaching_class_forum_topic_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_forum_reply where t_course_teaching_class_forum_topic_id=?", t_course_teaching_class_forum_topic_id);
    }

    @Override
    public int deleteByt_user_id(String t_user_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_forum_reply where t_user_id=?", t_user_id);
    }

    /**
     * 更新
     */
    @Override
    public int update(String id, String t_user_id, String title, String content, Date last_modified_date) {
        if (id == null || t_user_id == null || title == null) return 0;

        return jdbcTemplate.update("UPDATE t_course_teaching_class_forum_reply set t_user_id=?,title=?,content=? ,last_modified_date=? WHERE id=?", t_user_id, title, content, last_modified_date, id);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassForumReply getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_reply WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_course_teaching_class_forum_reply WHERE id=?", new Object[]{id}, new CourseTeachingClassForumReplyMapper());


    }

    /**
     * 根据教学班得到论坛id
     */
    @Override
    public List<CourseTeachingClassForumReply> getByForumTopicID(String t_course_teaching_class_forum_topic_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_reply WHERE t_course_teaching_class_forum_topic_id=?", new
                Object[]{t_course_teaching_class_forum_topic_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_reply where t_course_teaching_class_forum_topic_id=?", new Object[]{t_course_teaching_class_forum_topic_id}, new CourseTeachingClassForumReplyMapper());


    }

    /**
     * 根据t_user_id得到论坛id
     */
    @Override
    public List<CourseTeachingClassForumReply> getByUserID(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_reply WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_reply where t_user_id=?", new Object[]{t_user_id}, new CourseTeachingClassForumReplyMapper());

    }

    /**
     * 根据t_user_id得到论坛id
     */
    @Override
    public List<CourseTeachingClassForumReply> getByCourseTeachingClassIdAndUserID(String t_course_teaching_class_id, String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_reply WHERE t_course_teaching_class_id=? and t_user_id=?", new
                Object[]{t_user_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_reply where t_course_teaching_class_id=? and t_user_id=?", new Object[]{t_course_teaching_class_id, t_user_id}, new CourseTeachingClassForumReplyMapper());

    }

    @Override
    public long getCount(String t_course_teaching_class_forum_topic_id) {

        return jdbcTemplate.queryForObject("select count(*) from t_course_teaching_class_forum_reply WHERE t_course_teaching_class_forum_topic_id=?", new Object[]{t_course_teaching_class_forum_topic_id,}, Integer.class);
    }

    private List<CourseTeachingClassForumReply> PageQuery(String t_course_teaching_class_forum_topic_id, int PageBegin, int PageSize) {


        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_reply WHERE t_course_teaching_class_forum_topic_id=? order by created_date asc limit ?,?", new
                Object[]{t_course_teaching_class_forum_topic_id, PageBegin * PageSize, PageSize}, Integer.class) != 1)
            return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_reply WHERE t_course_teaching_class_forum_topic_id=? order by created_date asc limit ?,?", new Object[]{t_course_teaching_class_forum_topic_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassForumReplyMapper());

    }

    @Override
    public Page<CourseTeachingClassForumReply> getPage(String t_course_teaching_class_forum_topic_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_forum_topic_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassForumReply> data = PageQuery(t_course_teaching_class_forum_topic_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassForumReplyMapper implements RowMapper<CourseTeachingClassForumReply> {

        @Override
        public CourseTeachingClassForumReply mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassForumReply courseTeachingClassForumReply = new CourseTeachingClassForumReply();
            courseTeachingClassForumReply.setId(rs.getString("id"));
            courseTeachingClassForumReply.setCourseTeachingClassForumTopicId(rs.getString("t_course_teaching_class_forum_topic_id"));
            courseTeachingClassForumReply.setUserId(rs.getString("t_user_id"));
            courseTeachingClassForumReply.setCreatedDate(DateTimeSql.GetDateTime(rs.getString("created_date")));
            courseTeachingClassForumReply.setLastModifiedDate(DateTimeSql.GetDateTime(rs.getString("last_modified_date")));
            courseTeachingClassForumReply.setTitle(rs.getString("title"));
            courseTeachingClassForumReply.setContent(rs.getString("content"));

            return courseTeachingClassForumReply;
        }
    }
}
