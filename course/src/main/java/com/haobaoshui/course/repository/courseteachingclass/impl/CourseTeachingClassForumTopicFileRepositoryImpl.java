package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopicFile;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumTopicFileRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseTeachingClassForumTopicFileRepositoryImpl implements CourseTeachingClassForumTopicFileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassForumTopicFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(CourseTeachingClassForumTopicFile submitfile) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_forum_topic_file( id,t_course_teaching_class_forum_topic_id,filename,filepath) VALUES(?,?,?,?)", newid, submitfile.getCourseTeachingClassForumTopicId(), submitfile.getFilename(),
                submitfile.getFilepath());
        return newid;


    }

    @Override
    public String add(String t_course_teaching_class_forum_topic_id, String filename, String filepath) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_forum_topic_file( id,t_course_teaching_class_forum_topic_id,filename,filepath) VALUES(?,?,?,?)", newid, t_course_teaching_class_forum_topic_id, filename, filepath);
        return newid;
    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_forum_topic_file where id=?", id);
    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_forum_topic_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_forum_topic_file where t_course_teaching_class_forum_topic_id=?", t_course_teaching_class_forum_topic_id);

    }

    @Override
    public int update(String id, String t_forum_topic_id, String filename, String filepath) {
        if (id == null || t_forum_topic_id == null || filename == null || filepath == null) return 0;

        return jdbcTemplate.update("update t_course_teaching_class_forum_topic_file set t_course_teaching_class_forum_topic_id=?, filename=?,filepath=? WHERE id=?", t_forum_topic_id, filename, filepath, id);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassForumTopicFile getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_topic_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_forum_topic_file WHERE id=?", new Object[]{id}, new CourseTeachingClassForumTopicFileMapper());


    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<CourseTeachingClassForumTopicFile> getByForumTopicID(String t_course_teaching_class_forum_topic_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_topic_file where t_course_teaching_class_forum_topic_id=?", new
                Object[]{t_course_teaching_class_forum_topic_id}, Integer.class) != 1) return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_topic_file where t_course_teaching_class_forum_topic_id=?", new CourseTeachingClassForumTopicFileMapper());


    }

    @Override
    public long getCount(String t_course_teaching_class_forum_topic_id) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_forum_topic_file WHERE t_course_teaching_class_forum_topic_id=?", new Object[]{t_course_teaching_class_forum_topic_id}, Integer.class);


    }

    private List<CourseTeachingClassForumTopicFile> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_forum_topic_file WHERE t_course_teaching_class_forum_topic_id=?   limit ?,?",
                new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize}, new CourseTeachingClassForumTopicFileMapper());

    }

    private static final class CourseTeachingClassForumTopicFileMapper implements RowMapper<CourseTeachingClassForumTopicFile> {

        @Override
        public CourseTeachingClassForumTopicFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassForumTopicFile courseTeachingClassForumTopicFile = new CourseTeachingClassForumTopicFile();
            courseTeachingClassForumTopicFile.setId(rs.getString("id"));
            courseTeachingClassForumTopicFile.setCourseTeachingClassForumTopicId(rs.getString("t_course_teaching_class_forum_topic_id"));

            courseTeachingClassForumTopicFile.setFilename(rs.getString("filename"));
            courseTeachingClassForumTopicFile.setFilepath(rs.getString("filepath"));

            return courseTeachingClassForumTopicFile;
        }
    }


}
