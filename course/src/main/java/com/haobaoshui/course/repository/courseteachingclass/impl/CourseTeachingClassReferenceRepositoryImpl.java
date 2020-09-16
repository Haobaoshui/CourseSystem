package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReference;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassReferenceRepository;
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
public class CourseTeachingClassReferenceRepositoryImpl implements CourseTeachingClassReferenceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassReferenceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(CourseTeachingClassReference courseTeachingClassReference) {


        if (courseTeachingClassReference == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_reference( id,t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_reference_type_id,title, content, pubdate, modifieddate) VALUES(?,?,?,?,?,?,?,?)", newid, courseTeachingClassReference.getCourseTeachingClassId(), courseTeachingClassReference.getTeacherId(),
                courseTeachingClassReference.getCourseTeachingClassReferenceTypeId(), courseTeachingClassReference.getTitle(), courseTeachingClassReference.getContent(),
                courseTeachingClassReference.getPubdate(), courseTeachingClassReference.getModifieddate());
        return newid;

    }

    @Override
    public String add(String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_homework_type_id, String title,
                      String content, Date pubdate, Date enddate) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_reference( id,t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_reference_type_id,title, content, pubdate, modifieddate) VALUES(?,?,?,?,?,?,?,?)", newid, t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_homework_type_id, title,
                content, pubdate, enddate);
        return newid;
    }

    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("DELETE from  t_course_teaching_class_reference where id=?", id);
    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {

        return jdbcTemplate.update("DELETE from  T_user where t_course_teaching_class_id=?", t_course_teaching_class_id);
    }

    @Override
    public int deleteByTeacherId(String t_teacher_id) {

        return jdbcTemplate.update("DELETE from  T_user where t_teacher_id=?", t_teacher_id);
    }

    @Override
    public int update(String id, String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_reference_type_id,
                      String title, String content, Date pubdate, Date modifieddate) {
        if (id == null || t_teacher_id == null) return 0;


        return jdbcTemplate.update("update t_course_teaching_class_reference set t_course_teaching_class_id=?, t_teacher_id=?, t_course_teaching_class_reference_type_id=?, title=?, content=?, pubdate=?, modifieddate=?  WHERE id=?", t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_reference_type_id, title,
                content, pubdate, modifieddate, id);
    }

    @Override
    public int update(String id, String t_teacher_id, String title, String content, Date modifieddate) {
        if (id == null || t_teacher_id == null) return 0;


        return jdbcTemplate.update("update t_course_teaching_class_reference set t_teacher_id=?,  title=?, content=?,  modifieddate=?  WHERE id=?", t_teacher_id, title, content, modifieddate, id);
    }

    @Override
    public CourseTeachingClassReference getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_reference WHERE id=?", new Object[]{id}, new CourseTeachingClassReferenceMapper());


    }

    @Override
    public List<CourseTeachingClassReference> getByCourseTeachingClassID(String t_course_teaching_class_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) == 0) return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassReferenceMapper());


    }

    @Override
    public List<CourseTeachingClassReference> getByTeacherID(String t_teacher_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference WHERE t_teacher_id=?", new
                Object[]{t_teacher_id}, Integer.class) == 0) return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference WHERE t_teacher_id=?", new Object[]{t_teacher_id}, new CourseTeachingClassReferenceMapper());


    }

    @Override
    public List<CourseTeachingClassReference> getByReferenceTypeId(String t_course_teaching_class_reference_type_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference WHERE t_course_teaching_class_reference_type_id=?", new
                Object[]{t_course_teaching_class_reference_type_id}, Integer.class) == 0) return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference WHERE t_course_teaching_class_reference_type_id=?", new Object[]{t_course_teaching_class_reference_type_id}, new CourseTeachingClassReferenceMapper());


    }

    @Override
    public List<CourseTeachingClassReference> getByCourseTeachingClassIDAndTeacherId(String t_course_teaching_class_id,
                                                                                     String t_teacher_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=? and t_teacher_id=?", new
                Object[]{t_course_teaching_class_id, t_teacher_id}, Integer.class) == 0) return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=? and t_teacher_id=?", new Object[]{t_course_teaching_class_id, t_teacher_id}, new CourseTeachingClassReferenceMapper());


    }

    @Override
    public List<CourseTeachingClassReference> getByCourseTeachingClassIDAndTeacherIdAndReferenceTypeId(String t_course_teaching_class_id,
                                                                                                       String t_teacher_id, String t_course_teaching_class_reference_type_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=? and t_teacher_id=? and t_course_teaching_class_reference_type_id=?", new
                Object[]{t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_reference_type_id}, Integer.class) == 0)
            return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=? and t_teacher_id=? and t_course_teaching_class_reference_type_id=?", new Object[]{t_course_teaching_class_id, t_teacher_id}, new CourseTeachingClassReferenceMapper());


    }

    @Override
    public long getCount(String t_course_teaching_class_id, String t_course_teaching_class_reference_type_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=? and t_course_teaching_class_reference_type_id=?", new
                Object[]{t_course_teaching_class_id, t_course_teaching_class_reference_type_id}, Integer.class);


    }


    private List<CourseTeachingClassReference> PageQuery(String t_course_teaching_class_id,
                                                         String t_course_teaching_class_reference_type_id, int PageBegin, int PageSize) {
        if (getCount(t_course_teaching_class_id, t_course_teaching_class_reference_type_id) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_reference WHERE t_course_teaching_class_id=? and t_course_teaching_class_reference_type_id=? order by pubdate desc limit ?,?",
                new Object[]{t_course_teaching_class_id, t_course_teaching_class_reference_type_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassReferenceMapper());

    }

    @Override
    public Page<CourseTeachingClassReference> getPage(String t_course_teaching_class_id,
                                                      String t_course_teaching_class_reference_type_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id, t_course_teaching_class_reference_type_id);

        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassReference> data = PageQuery(t_course_teaching_class_id, t_course_teaching_class_reference_type_id,
                pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassReferenceMapper implements RowMapper<CourseTeachingClassReference> {

        @Override
        public CourseTeachingClassReference mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassReference courseTeachingClassReference = new CourseTeachingClassReference();
            courseTeachingClassReference.setId(rs.getString("id"));
            courseTeachingClassReference.setCourseTeachingClassId(rs.getString("t_course_teaching_class_id"));
            courseTeachingClassReference.setTeacherId(rs.getString("t_teacher_id"));
            courseTeachingClassReference.setCourseTeachingClassReferenceTypeId(rs.getString("t_course_teaching_class_reference_type_id"));
            courseTeachingClassReference.setModifieddate(rs.getTimestamp("modifieddate"));
            courseTeachingClassReference.setPubdate(rs.getTimestamp("pubdate"));
            courseTeachingClassReference.setTitle(rs.getString("title"));
            courseTeachingClassReference.setContent(rs.getString("content"));

            return courseTeachingClassReference;
        }
    }
}
