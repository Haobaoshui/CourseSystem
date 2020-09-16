package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReplyFile;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumReplyFileRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class CourseTeachingClassForumReplyFileRepositoryImpl implements CourseTeachingClassForumReplyFileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassForumReplyFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int update(String id, String t_forum_reply_id, String filename, String filepath) {
        if (id == null || t_forum_reply_id == null || filename == null || filepath == null) return 0;


        return jdbcTemplate.update("UPDATE t_course_teaching_class_forum_reply_file set t_course_teaching_class_forum_reply_id=?, filename=?,filepath=? WHERE id=?", t_forum_reply_id, filename, filepath, id);
    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassForumReplyFile getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_reply_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_forum_reply_file WHERE id=?", new Object[]{id}, new CourseTeachingClassForumReplyFileMapper());


    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<CourseTeachingClassForumReplyFile> getByForumReplyID(String t_forum_reply_id) {

        if (getCount(t_forum_reply_id) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_attendance_mode", new Object[]{t_forum_reply_id}, new CourseTeachingClassForumReplyFileMapper());


    }

    /* 增加用户 */
    @Override
    public String add(CourseTeachingClassForumReplyFile submitfile) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_forum_reply_file( id,t_course_teaching_class_forum_reply_id,filename,filepath) VALUES(?,?,?,?)", newid, submitfile.getForumReplyId(), submitfile.getFilename(),
                submitfile.getFilepath());
        return newid;


    }

    @Override
    public String add(String t_forum_reply_id, String filename, String filepath) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_forum_reply_file( id,t_course_teaching_class_forum_reply_id,filename,filepath) VALUES(?,?,?,?)", newid, t_forum_reply_id, filename, filepath);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_forum_reply_file where id=?", id);
    }

    @Override
    public int deleteByForumReplyId(String t_forum_reply_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_forum_reply_file where t_course_teaching_class_forum_reply_id=?", t_forum_reply_id);
    }

    @Override
    public long getCount(String t_forum_reply_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_reply_file WHERE t_course_teaching_class_forum_reply_id=?", new Object[]{t_forum_reply_id}, new int[]{Types.VARCHAR}, Long.class);
    }

    private List<CourseTeachingClassForumReplyFile> PageQuery(String t_forum_reply_id, int PageBegin, int PageSize) {


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_reply_file WHERE t_course_teaching_class_forum_reply_id=?   limit ?,?", new Object[]{t_forum_reply_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassForumReplyFileMapper());

    }

    private static final class CourseTeachingClassForumReplyFileMapper implements RowMapper<CourseTeachingClassForumReplyFile> {

        @Override
        public CourseTeachingClassForumReplyFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassForumReplyFile courseTeachingClassForumReplyFile = new CourseTeachingClassForumReplyFile();
            courseTeachingClassForumReplyFile.setId(rs.getString("id"));
            courseTeachingClassForumReplyFile.setForumReplyId(rs.getString("t_forum_reply_id"));
            courseTeachingClassForumReplyFile.setFilename(rs.getString("filename"));
            courseTeachingClassForumReplyFile.setFilepath(rs.getString("filepath"));

            return courseTeachingClassForumReplyFile;
        }
    }


}
