package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkFile;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkFileRepository;
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
public class CourseTeachingClassHomeworkFileRepositoryImpl implements CourseTeachingClassHomeworkFileRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassHomeworkFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(CourseTeachingClassHomeworkFile expriment) {
        String id = GUID.getGUID();

        Object[] params = new Object[]{id, expriment.getCourseTeachingClassHomeworkBaseinfoId(), expriment.getFilename(), expriment.getFilepath()};
		jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_file( id,t_course_teaching_class_homework_baseinfo_id,filename,filepath) VALUES(?,?,?,?)", params);
        return id;
    }

    @Override
    public String add(String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath) {

        String id = GUID.getGUID();

        Object[] params = new Object[]{id, t_course_teaching_class_homework_baseinfo_id, filename, filepath};
		jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_file( id,t_course_teaching_class_homework_baseinfo_id,filename,filepath) VALUES(?,?,?,?)", params);
        return id;
    }

    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_homework_file WHERE id=?", id);
    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_homework_baseinfo_id) {

        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_homework_file WHERE t_course_teaching_class_homework_baseinfo_id=?", t_course_teaching_class_homework_baseinfo_id);
    }

    @Override
    public int update(String id, String t_course_teaching_class_homework_baseinfo_id, String filename, String filepath) {
        if (id == null || t_course_teaching_class_homework_baseinfo_id == null || filename == null || filepath == null)
			return 0;

        Object[] params = new Object[]{t_course_teaching_class_homework_baseinfo_id, filename, filepath, id};

        return jdbcTemplate.update("update t_course_teaching_class_homework_file set t_course_teaching_class_homework_baseinfo_id=?  filename=?,filepath=? WHERE id=?", params);
    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassHomeworkFile getByID(String id) {


        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_homework_file WHERE id=?", new Object[]{id}, new CourseTeachingClassHomeworkFileMapper());


    }

    /**
     * 根据课程-作业基本信息得到对应的文件列表
     */
    @Override
    public List<CourseTeachingClassHomeworkFile> getByCourseTeachingClassHomeworkBaseInfoID(String t_course_teaching_class_homework_baseinfo_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_file WHERE t_course_teaching_class_homework_baseinfo_id=?", new
                Object[]{t_course_teaching_class_homework_baseinfo_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_file WHERE t_course_teaching_class_homework_baseinfo_id=?", new Object[]{t_course_teaching_class_homework_baseinfo_id}, new CourseTeachingClassHomeworkFileMapper());


    }

    @Override
    public long getCount(String t_course_teaching_class_homework_baseinfo_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_file WHERE t_course_teaching_class_homework_baseinfo_id=?", new Object[]{t_course_teaching_class_homework_baseinfo_id}, new int[]{Types.VARCHAR}, Long.class);
    }

    private List<CourseTeachingClassHomeworkFile> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        final String countSQL = "SELECT count (*)FROM t_course_teaching_class_homework_file WHERE t_course_teaching_class_homework_baseinfo_id=?";

        if (jdbcTemplate.queryForObject(countSQL, new
                Object[]{t_course_teaching_class_id}, Integer.class) != 1) return null;

        final String quertySQL = "SELECT * FROM t_course_teaching_class_homework_file WHERE t_course_teaching_class_homework_baseinfo_id=?   limit ?,?";

        return jdbcTemplate.query(quertySQL, new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassHomeworkFileMapper());

    }

    @Override
    public Page<CourseTeachingClassHomeworkFile> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkFile> data = PageQuery(t_course_teaching_class_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassHomeworkFileMapper implements RowMapper<CourseTeachingClassHomeworkFile> {

        @Override
        public CourseTeachingClassHomeworkFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkFile courseTeachingClassHomeworkFile = new CourseTeachingClassHomeworkFile();
            courseTeachingClassHomeworkFile.setId(rs.getString("id"));
            courseTeachingClassHomeworkFile.setCourseTeachingClassHomeworkBaseinfoId(rs.getString("t_course_teaching_class_homework_baseinfo_id"));

            courseTeachingClassHomeworkFile.setFilename(rs.getString("filename"));
            courseTeachingClassHomeworkFile.setFilepath(rs.getString("filepath"));

            return courseTeachingClassHomeworkFile;
        }
    }
}
