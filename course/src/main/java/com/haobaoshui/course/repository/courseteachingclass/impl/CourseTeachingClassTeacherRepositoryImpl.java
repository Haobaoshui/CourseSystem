package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassTeacher;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassTeacherRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseTeachingClassTeacherRepositoryImpl implements CourseTeachingClassTeacherRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassTeacherRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 向授课班增加教师
     */
    @Override
    public String add(String t_course_teaching_class_id, String t_teacher_id, String t_teaching_type_id) {

        if (IsTeacherExist(t_course_teaching_class_id, t_teacher_id, t_teaching_type_id)) return null;

        deleteByCourseTeachingClassIdAndTeacherIdAndTeachingTypeId(t_course_teaching_class_id, t_teacher_id,
                t_teaching_type_id);

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_teacher(id,t_course_teaching_class_id,t_teacher_id,t_teaching_type_id) VALUES(?,?,?,?)", newid, t_course_teaching_class_id, t_teacher_id, t_teaching_type_id);
        return newid;

    }

    /**
     * 根据id删除
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_teacher where id=?", id);


    }

    /**
     * 根据t_course_teaching_class_id删除
     */
    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_teacher where t_course_teaching_class_id=?", t_course_teaching_class_id);


    }

    /**
     * 根据t_teaching_type_id删除
     */
    @Override
    public int deleteByTeachingTypeId(String t_teaching_type_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_teacher where t_teaching_type_id=?", t_teaching_type_id);


    }

    /**
     * 根据t_teacher_id删除
     */
    @Override
    public int deleteByTeacherId(String t_teacher_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_teacher where t_teacher_id=?", t_teacher_id);

    }

    /**
     * 根据t_course_teaching_class_id、t_teacher_id
     */
    @Override
    public int deleteByCourseTeachingClassIdAndTeacherId(String t_course_teaching_class_id, String t_teacher_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_teacher WHERE t_course_teaching_class_id=? and t_teacher_id=?", t_course_teaching_class_id, t_teacher_id);


    }

    /**
     * 根据t_course_teaching_class_id、t_teacher_id、 t_teaching_type_id
     */
    @Override
    public int deleteByCourseTeachingClassIdAndTeacherIdAndTeachingTypeId(String t_course_teaching_class_id,
                                                                          String t_teacher_id, String t_teaching_type_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_teacher WHERE t_course_teaching_class_id=? and t_teacher_id=? and t_teaching_type_id=?", t_course_teaching_class_id, t_teacher_id, t_teaching_type_id);


    }

    @Override
    public List<CourseTeachingClassTeacher> getTeachingClassTeacherById(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_teacher WHERE id=?", new
                Object[]{id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_teacher WHERE id=?", new Object[]{id}, new CourseTeachingClassTeacherMapper());

    }

    @Override
    public List<CourseTeachingClassTeacher> getTeachingClassTeacherByTeachingClassId(String t_course_teaching_class_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_teacher WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_teacher WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassTeacherMapper());

    }

    @Override
    public List<CourseTeachingClassTeacher> getTeachingClassTeacherByTeacherId(String t_teacher_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_teacher WHERE t_teacher_id=?", new
                Object[]{t_teacher_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_teacher WHERE t_teacher_id=?", new Object[]{t_teacher_id}, new CourseTeachingClassTeacherMapper());

    }


    @Override
    public boolean IsTeacherExist(String t_course_teaching_class_id, String t_teacher_id, String t_teachingtype_id) {

        return jdbcTemplate.queryForObject("select count(*) from t_course_teaching_class_teacher where t_course_teaching_class_id=? and t_teacher_id=? and t_teaching_type_id=?",
                new Object[]{t_course_teaching_class_id, t_teacher_id, t_teachingtype_id},
                Long.class) > 0;
    }

    private static final class CourseTeachingClassTeacherMapper implements RowMapper<CourseTeachingClassTeacher> {

        @Override
        public CourseTeachingClassTeacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassTeacher courseTeachingClassTeacher = new CourseTeachingClassTeacher();
            courseTeachingClassTeacher.setId(rs.getString("id"));
            courseTeachingClassTeacher.setTeacherId(rs.getString("t_teacher_id"));
            courseTeachingClassTeacher.setCourseTeachingClassId(rs.getString("t_course_teaching_class_id"));
            courseTeachingClassTeacher.setTeachingTypeId(rs.getString("t_teaching_type_id"));

            return courseTeachingClassTeacher;
        }
    }


}
