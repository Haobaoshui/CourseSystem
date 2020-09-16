package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceFile;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassReferenceFileRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseTeachingClassReferenceFileRepositoryImpl implements CourseTeachingClassReferenceFileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassReferenceFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加用户 */
    @Override
    public String add(CourseTeachingClassReferenceFile file) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_reference_file( id,t_course_teaching_class_reference_id,filename,filepath) VALUES(?,?,?,?)", newid, file.getCourseTeachingClassReferenceId(), file.getFilename(), file.getFilepath());
        return newid;


    }

    @Override
    public String add(String t_course_teaching_class_reference_id, String filename, String filepath) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_reference_file( id,t_course_teaching_class_reference_id,filename,filepath) VALUES(?,?,?,?)", newid, t_course_teaching_class_reference_id, filename, filepath);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_reference_file where id=?", id);
    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_reference_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_reference_file WHERE t_course_teaching_class_reference_id=?", t_course_teaching_class_reference_id);

    }

    @Override
    public int update(String id, String t_course_teaching_class_reference_id, String filename, String filepath) {
        if (id == null || t_course_teaching_class_reference_id == null || filename == null || filepath == null)
            return 0;

        return jdbcTemplate.update("update t_course_teaching_class_reference_file set t_course_teaching_class_reference_id=?  filename=?,filepath=? WHERE id=?", t_course_teaching_class_reference_id, filename, filepath, id);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassReferenceFile getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_reference_file WHERE id=?", new Object[]{id}, new CourseTeachingClassReferenceFileMapper());


    }

    /**
     * 根据课程-作业基本信息得到对应的文件列表
     */
    @Override
    public List<CourseTeachingClassReferenceFile> getByCourseTeachingClassHomeworkBaseInfoID(String t_course_teaching_class_reference_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference_file WHERE t_course_teaching_class_reference_id=?", new
                Object[]{t_course_teaching_class_reference_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("select * from t_course_teaching_class_reference_file where t_course_teaching_class_reference_id=?", new Object[]{t_course_teaching_class_reference_id}, new CourseTeachingClassReferenceFileMapper());

    }

    @Override
    public long getCount(String t_group_id) {

        return jdbcTemplate.queryForObject("select count(*) from t_course_teaching_class_reference_file", Integer.class);
    }

    private List<String> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        List<String> list = new ArrayList<>();

        jdbcTemplate.query("SELECT id FROM t_course_teaching_class_reference_file WHERE t_course_teaching_class_reference_id=?   limit ?,?", new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize},
                new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {


                        list.add(rs.getString("id"));
                        // System.out.println(rs.getString("t_user_id"));
                    }

                });
        return list;
    }

    private static final class CourseTeachingClassReferenceFileMapper implements RowMapper<CourseTeachingClassReferenceFile> {

        @Override
        public CourseTeachingClassReferenceFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassReferenceFile courseTeachingClassReferenceFile = new CourseTeachingClassReferenceFile();

            courseTeachingClassReferenceFile.setId(rs.getString("id"));
            courseTeachingClassReferenceFile.setCourseTeachingClassReferenceId(rs.getString("t_course_teaching_class_reference_id"));
            courseTeachingClassReferenceFile.setFilename(rs.getString("filename"));
            courseTeachingClassReferenceFile.setFilepath(rs.getString("filepath"));

            return courseTeachingClassReferenceFile;
        }
    }


}
