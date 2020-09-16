package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitFile;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkSubmitFileRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseTeachingClassHomeworkSubmitFileRepositoryImpl implements CourseTeachingClassHomeworkSubmitFileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassHomeworkSubmitFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加用户 */
    @Override
    public String add(CourseTeachingClassHomeworkSubmitFile submitfile) {
        String id = GUID.getGUID();


        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_submit_file( id,t_course_teaching_class_homework_submit_baseinfo_id,file_node_id,filename,filepath) VALUES(?,?,?,?,?)", id, submitfile.getCourseTeachingClassHomeworkSubmitBaseinfoId(), submitfile.getFilename(),
                submitfile.getFilepath());
        return id;
    }

    @Override
    public String add(String t_course_teaching_class_homework_baseinfo_id, int fileNodeId, String filename, String filepath) {

        String id = GUID.getGUID();


        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_submit_file( id,t_course_teaching_class_homework_submit_baseinfo_id,file_node_id,filename,filepath) VALUES(?,?,?,?,?)", id, t_course_teaching_class_homework_baseinfo_id, fileNodeId, filename, filepath);
        return id;
    }

    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_homework_submit_file WHERE id=?", id);
    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_homework_baseinfo_id) {

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_homework_submit_file WHERE t_course_teaching_class_homework_submit_baseinfo_id=?", t_course_teaching_class_homework_baseinfo_id);
    }

    @Override
    public int update(String id, String t_course_teaching_class_homework_submit_baseinfo_id, int fileNodeId, String filename, String filepath) {
        if (id == null || t_course_teaching_class_homework_submit_baseinfo_id == null || filename == null || filepath == null)
            return 0;


        return jdbcTemplate.update("update t_course_teaching_class_homework_submit_file set t_course_teaching_class_homework_submit_baseinfo_id=?, file_node_id=?,filename=?,filepath=? WHERE id=?", t_course_teaching_class_homework_submit_baseinfo_id, fileNodeId, filename, filepath, id);
    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassHomeworkSubmitFile getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_homework_submit_file WHERE id=?", new Object[]{id}, new CourseTeachingClassHomeworkSubmitFileMapper());


    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<CourseTeachingClassHomeworkSubmitFile> getByCourseTeachingClassHomeworkBaseInfoID(String t_course_teaching_class_homework_submit_baseinfo_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_file WHERE t_course_teaching_class_homework_submit_baseinfo_id=?", new
                Object[]{t_course_teaching_class_homework_submit_baseinfo_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_submit_file WHERE t_course_teaching_class_homework_submit_baseinfo_id=?", new Object[]{t_course_teaching_class_homework_submit_baseinfo_id}, new CourseTeachingClassHomeworkSubmitFileMapper());


    }

    @Override
    public long getCount(String t_group_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_file WHERE t_course_teaching_class_homework_submit_baseinfo_id=?", new Object[]{t_group_id}, Long.class);
    }

    private List<CourseTeachingClassHomeworkSubmitFile> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        final String countSQL = "SELECT couint(*) FROM t_course_teaching_class_homework_submit_file WHERE t_course_teaching_class_homework_submit_baseinfo_id=?";

        if (jdbcTemplate.queryForObject(countSQL, new
                Object[]{t_course_teaching_class_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_submit_file WHERE t_course_teaching_class_homework_submit_baseinfo_id=?   limit ?,?", new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassHomeworkSubmitFileMapper());

    }

    @Override
    public Page<CourseTeachingClassHomeworkSubmitFile> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkSubmitFile> data = PageQuery(t_course_teaching_class_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassHomeworkSubmitFileMapper implements RowMapper<CourseTeachingClassHomeworkSubmitFile> {

        @Override
        public CourseTeachingClassHomeworkSubmitFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkSubmitFile courseTeachingClassHomeworkSubmitFile = new CourseTeachingClassHomeworkSubmitFile();

            courseTeachingClassHomeworkSubmitFile.setId(rs.getString("id"));
            courseTeachingClassHomeworkSubmitFile.setCourseTeachingClassHomeworkSubmitBaseinfoId(rs.getString("t_course_teaching_class_homework_submit_baseinfo_id"));
            courseTeachingClassHomeworkSubmitFile.setFilename(rs.getString("filename"));
            courseTeachingClassHomeworkSubmitFile.setFilepath(rs.getString("filepath"));
            courseTeachingClassHomeworkSubmitFile.setFileNodeId(rs.getInt("file_node_id"));

            return courseTeachingClassHomeworkSubmitFile;
        }
    }
}
