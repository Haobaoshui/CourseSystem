package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceType;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassReferenceTypeRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class CourseTeachingClassReferenceTypeRepositoryImpl implements CourseTeachingClassReferenceTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassReferenceTypeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加用户 */
    @Override
    public String add(CourseTeachingClassReferenceType courseTeachingClassReferenceType) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_reference_type( id,t_course_teaching_class_id,  name, note) VALUES(?,?,?,?)", newid, courseTeachingClassReferenceType.getCourseTeachingClassId(),
                courseTeachingClassReferenceType.getName(), courseTeachingClassReferenceType.getNote());
        return newid;

    }

    @Override
    public String add(String t_course_teaching_class_id, String name, String note) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_reference_type( id,t_course_teaching_class_id,  name, note) VALUES(?,?,?,?)", newid, t_course_teaching_class_id, name, note);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_reference_type where id=?", id);

    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_reference_type WHERE t_course_teaching_class_id=?", t_course_teaching_class_id);

    }

    @Override
    public int update(String id, String name, String note) {
        if (id == null) return 0;
        return jdbcTemplate.update("UPDATE t_course_teaching_class_reference_type set  name=?, note=?  WHERE id=?", name, note, id);


    }

    /*
     *
     */
    @Override
    public CourseTeachingClassReferenceType getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference_type WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_reference_type WHERE id=?", new Object[]{id}, new CourseTeachingClassReferenceTypeMapper());


    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<CourseTeachingClassReferenceType> getByCourseTeachingClassID(String t_course_teaching_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference_type WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference_type WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassReferenceTypeMapper());


    }

    @Override
    public long getCount(String t_course_teaching_class_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) t_course_teaching_class_reference_type WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, Long.class);
    }

    private List<CourseTeachingClassReferenceType> PageQuery(String t_course_teaching_class_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;


        if (jdbcTemplate.queryForObject("SELECT count(*) t_course_teaching_class_reference_type WHERE t_course_teaching_class_id=?   limit ?,?", new
                Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference_type WHERE t_course_teaching_class_id=?   limit ?,?", new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize}, new CourseTeachingClassReferenceTypeMapper());


    }

    @Override
    public Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassReferenceType> data = PageQuery(t_course_teaching_class_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    @Override
    public Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id) {
        long totalCount = getCount(t_course_teaching_class_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(1, (int) totalCount);

        List<CourseTeachingClassReferenceType> data = PageQuery(t_course_teaching_class_id, 1, (int) totalCount);

        return new Page<>(startIndex, totalCount, (int) totalCount, data);

    }

    private static final class CourseTeachingClassReferenceTypeMapper implements RowMapper<CourseTeachingClassReferenceType> {

        @Override
        public CourseTeachingClassReferenceType mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassReferenceType courseTeachingClassReferenceType = new CourseTeachingClassReferenceType();
            courseTeachingClassReferenceType.setId(rs.getString("id"));
            courseTeachingClassReferenceType.setName(rs.getString("name"));
            courseTeachingClassReferenceType.setNote(rs.getString("note"));
            courseTeachingClassReferenceType.setCourseTeachingClassId(rs.getString("t_course_teaching_class_id"));

            return courseTeachingClassReferenceType;
        }
    }
}
