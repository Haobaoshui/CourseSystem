package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudent;
import com.haobaoshui.course.model.user.Student;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassStudentRepository;
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
public class CourseTeachingClassStudentRepositoryImpl implements CourseTeachingClassStudentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassStudentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加学生
     */
    @Override
    public String add(String t_course_teaching_class_id, String t_student_id, int show_index) {
        if (IsStudentExist(t_course_teaching_class_id, t_student_id)) return null;


        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_course_teaching_class_student(id,t_course_teaching_class_id,t_student_id,show_index) VALUES(?,?,?,?)", newid, t_course_teaching_class_id, t_student_id, show_index);
        return newid;

    }

    /**
     * 根据id删除
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_student where id=?", id);

    }

    /**
     * 根据课程删除所有的学生
     */
    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=?", t_course_teaching_class_id);

    }

    /**
     * 删除学生
     */
    @Override
    public int deleteByStudentId(String t_student_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_student WHERE t_student_id=?", t_student_id);

    }

    /**
     * 删除指定教学班级中的学生
     */
    @Override
    public int deleteByCourseTeachingClassIdAndStudentId(String t_course_teaching_class_id, String t_student_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? and t_student_id=?", t_course_teaching_class_id, t_student_id);

    }

    /**
     * 修改
     */
    @Override
    public int update(String id, String t_course_teaching_class_id, String t_student_id, int show_index) {
        return jdbcTemplate.update("update t_course_teaching_class_student"
                + " set t_course_teaching_class_id=?,t_student_id=?,show_index=? where id=?", t_course_teaching_class_id, t_student_id, show_index, id);


    }

    /**
     * 修改show_index
     */
    @Override
    public int update(String id, int show_index) {
        return jdbcTemplate.update("update t_course_teaching_class_student set show_index=? where id=?", show_index, id);


    }

    /**
     * 修改show_index
     */
    @Override
    public int update(String t_course_teaching_class_id, String t_student_id, int show_index) {
        return jdbcTemplate.update("update t_course_teaching_class_student set show_index=? where t_course_teaching_class_id=?,t_student_id=?", show_index, t_course_teaching_class_id, t_student_id);


    }

    /**
     * 得到教学班人数
     */
    @Override
    public int getStudentCountByCourseTeachingClassId(String t_course_teaching_class_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=?",
                new Object[]{t_course_teaching_class_id}, Integer.class);
    }

    /**
     *
     */
    @Override
    public int getShowIndexMaxByCourseTeachingClassId(String t_course_teaching_class_id) {

        Integer i = jdbcTemplate.queryForObject("SELECT max(show_index) FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=?",
                new Object[]{t_course_teaching_class_id}, Integer.class);
        if (i == null) return 0;
        return i;

    }

    /**
     * 得到比指定的学生小一个次序
     */
    @Override
    public int getShowIndexMaxLessthanByCourseTeachingClassId(String t_course_teaching_class_id, String t_student_id) {

        Integer i = jdbcTemplate.queryForObject("SELECT max(show_index) FROM t_course_teaching_class_student "
                        + "WHERE show_index<(select show_index FROM t_course_teaching_class_student where t_course_teaching_class_id=? and t_student_id=?)",
                new Object[]{t_course_teaching_class_id, t_student_id},
                Integer.class);
        if (i == null) return 0;
        return i;

    }

    /**
     * 得到比指定的学生大一个次序
     */
    @Override
    public int getShowIndexMinMorethanByCourseTeachingClassId(String t_course_teaching_class_id, String t_student_id) {

        Integer i = jdbcTemplate.queryForObject("SELECT min(show_index) FROM t_course_teaching_class_student "
                        + "WHERE show_index>(select show_index FROM t_course_teaching_class_student where t_course_teaching_class_id=? and t_student_id=?)",
                new Object[]{t_course_teaching_class_id, t_student_id},
                Integer.class);
        if (i == null) return 0;
        return i;

    }

    /**
     *
     */
    @Override
    public int getShowIndexMinByCourseTeachingClassId(String t_course_teaching_class_id) {

        Integer i = jdbcTemplate.queryForObject("SELECT min(show_index) FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=?",
                new Object[]{t_course_teaching_class_id}, new int[]{Types.VARCHAR}, Integer.class);
        if (i == null) return 0;
        return i;

    }

    /**
     * 学生是否在指定教学班级中存在
     */
    @Override
    public boolean IsStudentExist(String t_course_teaching_class_id, String t_student_id) {


        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student   WHERE t_course_teaching_class_id=? and t_student_id=?",
                new Object[]{t_course_teaching_class_id, t_student_id},
                Long.class) > 0;
    }

    @Override
    public CourseTeachingClassStudent getTeachingClassStudentByTeachingClassIdAndStudentId(
            String t_course_teaching_class_id, String t_student_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student   WHERE t_course_teaching_class_id=? and t_student_id=?", new
                Object[]{t_course_teaching_class_id, t_student_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT *  FROM t_course_teaching_class_student   WHERE t_course_teaching_class_id=? and t_student_id=?", new Object[]{t_course_teaching_class_id, t_student_id}, new CourseTeachingClassStudentMapper());


    }

    @Override
    public CourseTeachingClassStudent getTeachingClassStudentByTeachingClassIdAndShowIndex(
            String t_course_teaching_class_id, int show_index) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student  WHERE t_course_teaching_class_id=?  and show_index=?", new
                Object[]{t_course_teaching_class_id, show_index}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT *  FROM t_course_teaching_class_student  WHERE t_course_teaching_class_id=?  and show_index=?", new Object[]{t_course_teaching_class_id, show_index}, new CourseTeachingClassStudentMapper());

    }

    /**
     * 获得制定学生的教学班级信息
     */
    @Override
    public List<CourseTeachingClassStudent> getTeachingClassStudentByStudentId(String t_student_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student WHERE t_student_id=?", new
                Object[]{t_student_id}, Integer.class) > 0) return null;
        return jdbcTemplate.query("SELECT *  FROM t_course_teaching_class_student  WHERE t_student_id=?", new Object[]{t_student_id}, new CourseTeachingClassStudentMapper());


    }

    /**
     * 获得制定学生的教学班级信息
     */
    @Override
    public List<CourseTeachingClassStudent> getTeachingClassStudentByStudentId(Student student) {

        if (student == null || student.getId() == null) return null;
        return getTeachingClassStudentByStudentId(student.getId());

    }

    /**
     * 获得教学班级学生信息
     */
    @Override
    public List<CourseTeachingClassStudent> getTeachingClassStudentByTeachingClassId(
            String t_course_teaching_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student  WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT *  FROM t_course_teaching_class_student  WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassStudentMapper());


    }

    private static final class CourseTeachingClassStudentMapper implements RowMapper<CourseTeachingClassStudent> {

        @Override
        public CourseTeachingClassStudent mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassStudent courseTeachingClassStudent = new CourseTeachingClassStudent();
            courseTeachingClassStudent.setId(rs.getString("id"));
            courseTeachingClassStudent.setStudentId(rs.getString("t_student_id"));
            courseTeachingClassStudent.setCourseTeachingClassId(rs.getString("t_course_teaching_class_id"));
            courseTeachingClassStudent.setShowIndex(rs.getInt("show_index"));

            return courseTeachingClassStudent;
        }
    }


}
