package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReplyFile;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkReplyFileRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseTeachingClassHomeworkReplyFileRepositoryImpl implements CourseTeachingClassHomeworkReplyFileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassHomeworkReplyFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(CourseTeachingClassHomeworkReplyFile courseTeachingClassHomeworkReplyFile) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_reply_file( id,t_course_teaching_class_homework_reply_id,filename,filepath) VALUES(?,?,?,?)", newid, courseTeachingClassHomeworkReplyFile.getCourseTeachingClassHomeworkReplyId(), courseTeachingClassHomeworkReplyFile.getFilename(), courseTeachingClassHomeworkReplyFile.getFilepath());
        return newid;

    }

    @Override
    public String add(String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_reply_file( id,t_course_teaching_class_homework_reply_id,filename,filepath) VALUES(?,?,?,?)", newid, t_course_teaching_class_homework_baseinfo_id, filename, filepath);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_homework_reply_file where id=?", id);


    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_homework_baseinfo_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_homework_reply_file where t_course_teaching_class_homework_baseinfo_id=?", t_course_teaching_class_homework_baseinfo_id);

    }

    @Override
    public int update(String id, String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath) {
        if (id == null || t_course_teaching_class_homework_baseinfo_id == null || filename == null || filepath == null)
            return 0;
        return jdbcTemplate.update("update t_course_teaching_class_homework_reply_file set t_course_teaching_class_homework_reply_id=?,  filename=?,filepath=? WHERE id=?", t_course_teaching_class_homework_baseinfo_id, filename, filepath, id);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassHomeworkReplyFile getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_reply_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_course_teaching_class_homework_reply_file WHERE id=?", new Object[]{id}, new CourseTeachingClassHomeworkReplyFileMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkReplyFile> getByCourseTeachingClassHomeworkReplyID(String t_course_teaching_class_homework_reply_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_reply_file WHERE t_course_teaching_class_homework_reply_id=?", new
                Object[]{t_course_teaching_class_homework_reply_id}, Integer.class) == 0) return null;
        return jdbcTemplate.query("SELECT id,note FROM t_course_teaching_class_homework_reply_file WHERE t_course_teaching_class_homework_reply_id=?", new Object[]{t_course_teaching_class_homework_reply_id}, new CourseTeachingClassHomeworkReplyFileMapper());


    }

    @Override
    public long getCount(String t_group_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_reply_file WHERE t_course_teaching_class_homework_reply_id=?", new Object[]{t_group_id}, Long.class);
    }

    private List<CourseTeachingClassHomeworkReplyFile> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_reply_file WHERE t_course_teaching_class_homework_reply_id=?   limit ?,?", new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassHomeworkReplyFileMapper());

    }

    private static final class CourseTeachingClassHomeworkReplyFileMapper implements RowMapper<CourseTeachingClassHomeworkReplyFile> {

        @Override
        public CourseTeachingClassHomeworkReplyFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkReplyFile courseTeachingClassHomeworkReplyFile = new CourseTeachingClassHomeworkReplyFile();
            courseTeachingClassHomeworkReplyFile.setId(rs.getString("id"));
            courseTeachingClassHomeworkReplyFile.setCourseTeachingClassHomeworkReplyId(rs.getString("t_course_teaching_class_homework_reply_id"));
            courseTeachingClassHomeworkReplyFile.setFilename(rs.getString("filename"));
            courseTeachingClassHomeworkReplyFile.setFilepath(rs.getString("filepath"));

            return courseTeachingClassHomeworkReplyFile;
        }
    }


}
