package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudentGroup;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassStudentGroupRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseTeachingClassStudentGroupRepositoryImpl implements CourseTeachingClassStudentGroupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassStudentGroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加
     */
    @Override
    public String add(String t_course_teaching_class_id, String t_group_id, int show_index) {


        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_student_group(id,t_group_id,t_course_teaching_class_id,show_index) VALUES(?,?,?,?)", newid, t_group_id, t_course_teaching_class_id, show_index);
        return newid;

    }

    /**
     * 根据id删除
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_student_group where id=?", id);


    }

    /**
     * 根据课程删除所有的学生
     */
    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_student_group WHERE t_course_teaching_class_id=?", t_course_teaching_class_id);

    }

    /**
     * 根据组删除
     */
    @Override
    public int deleteByGroupId(String t_group_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_student_group where t_group_id=?", t_group_id);

    }

    /**
     * 根据课程和组id删除
     */
    @Override
    public int deleteByCourseTeachingClassIdAndGroupId(String t_course_teaching_class_id, String t_group_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_student_group WHERE t_course_teaching_class_id=? and t_group_id=?", t_course_teaching_class_id, t_group_id);

    }

    /**
     * 修改
     */
    @Override
    public int update(String id, String t_course_teaching_class_id, String t_group_id, int show_index) {
        return jdbcTemplate.update("update t_course_teaching_class_student_group set t_course_teaching_class_id=?,t_group_id=?,show_index=? where id=?", t_course_teaching_class_id, t_group_id, show_index, id);


    }

    /**
     * 修改show_index
     */
    @Override
    public int update(String id, int show_index) {

        return jdbcTemplate.update("update t_course_teaching_class_student_group set show_index=? where id=?", show_index, id);


    }

    /**
     * 修改show_index
     */
    @Override
    public int update(String t_course_teaching_class_id, String t_group_id, int show_index) {
        return jdbcTemplate.update("update t_course_teaching_class_student"
                + " set show_index=? where t_course_teaching_class_student_group=?,t_group_id=?", show_index, t_course_teaching_class_id, t_group_id);


    }

    /**
     * 学生是否在指定教学班级中存在
     */
    @Override
    public boolean IsGroupExist(String t_course_teaching_class_id, String t_student_id) {

        return jdbcTemplate.queryForObject("select count(*) "
                        + "from t_course_teaching_class_student_group "
                        + "where t_course_teaching_class_id=? and t_group_id=?",
                new Object[]{t_course_teaching_class_id, t_student_id},
                Long.class) > 0;
    }

    /**
     * 得到教学班人数
     */
    @Override
    public int getGroupCountByCourseTeachingClassId(String t_course_teaching_class_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student_group WHERE t_course_teaching_class_id=?",
                new Object[]{t_course_teaching_class_id}, Integer.class);
    }

    /**
     *
     */
    @Override
    public int getShowIndexMaxByCourseTeachingClassId(String t_course_teaching_class_id) {

        final String GET_MAX_SHOW_INDEX_BY_COURSE_TEACHING_CLASS_ID = "SELECT max(show_index) "
                + "FROM t_course_teaching_class_student_group "
                + "WHERE t_course_teaching_class_id=?";

        Integer i = jdbcTemplate.queryForObject(GET_MAX_SHOW_INDEX_BY_COURSE_TEACHING_CLASS_ID,
                new Object[]{t_course_teaching_class_id}, Integer.class);
        if (i == null) return 0;
        return i;

    }

    /**
     * 得到比指定的学生小一个次序
     */
    @Override
    public int getShowIndexMaxLessthanByCourseTeachingClassId(String t_course_teaching_class_id, String t_group_id) {

        final String querySQL = "SELECT max(show_index) "
                + "FROM t_course_teaching_class_student_group "
                + "WHERE show_index<"
                + "(select show_index FROM t_course_teaching_class_student_group where t_course_teaching_class_id=? and t_group_id=?)";

        Integer i = jdbcTemplate.queryForObject(querySQL,
                new Object[]{t_course_teaching_class_id, t_group_id},
                Integer.class);
        if (i == null) return 0;
        return i;

    }

    /**
     * 得到比指定的学生大一个次序
     */
    @Override
    public int getShowIndexMinMorethanByCourseTeachingClassId(String t_course_teaching_class_id, String t_group_id) {

        final String querySQL = "SELECT min(show_index) "
                + "FROM t_course_teaching_class_student_group "
                + "WHERE show_index>(select show_index FROM t_course_teaching_class_student_group where t_course_teaching_class_id=? and t_group_id=?)";

        Integer i = jdbcTemplate.queryForObject(querySQL,
                new Object[]{t_course_teaching_class_id, t_group_id},
                Integer.class);
        if (i == null) return 0;
        return i;

    }

    /**
     *
     */
    @Override
    public int getShowIndexMinByCourseTeachingClassId(String t_course_teaching_class_id) {

        final String querySQL = "SELECT min(show_index) FROM t_course_teaching_class_student_group  WHERE t_course_teaching_class_id=?";

        Integer i = jdbcTemplate.queryForObject(querySQL,
                new Object[]{t_course_teaching_class_id}, Integer.class);
        if (i == null) return 0;
        return i;

    }

    @Override
    public CourseTeachingClassStudentGroup getById(
            String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_course_teaching_class_student WHERE id=?", new Object[]{id}, new CourseTeachingClassStudentGroupMapper());


    }

    @Override
    public CourseTeachingClassStudentGroup getByTeachingClassIdAndGroupId(
            String t_course_teaching_class_id, String t_group_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? and t_group_id=?", new
                Object[]{t_course_teaching_class_id, t_group_id}, Integer.class) == 0) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? and t_group_id=?", new Object[]{t_course_teaching_class_id, t_group_id}, new CourseTeachingClassStudentGroupMapper());


    }

    @Override
    public CourseTeachingClassStudentGroup getByTeachingClassIdAndUserId(
            String t_course_teaching_class_id, String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? and t_user_id=?", new
                Object[]{t_course_teaching_class_id, t_user_id}, Integer.class) == 0) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? and t_user_id=?", new Object[]{t_course_teaching_class_id, t_user_id}, new CourseTeachingClassStudentGroupMapper());


    }

    @Override
    public CourseTeachingClassStudentGroup getByTeachingClassIdAndShowIndex(
            String t_course_teaching_class_id, int show_index) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? and show_index=?", new
                Object[]{t_course_teaching_class_id, show_index}, Integer.class) == 0) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? and show_index=?", new Object[]{t_course_teaching_class_id, show_index}, new CourseTeachingClassStudentGroupMapper());


    }

    @Override
    public List<CourseTeachingClassStudentGroup> getByCourseTeachingClassId(
            String t_course_teaching_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? ", new
                Object[]{t_course_teaching_class_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT id,note FROM t_course_teaching_class_student WHERE t_course_teaching_class_id=? ", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassStudentGroupMapper());


    }

    private static final class CourseTeachingClassStudentGroupMapper implements RowMapper<CourseTeachingClassStudentGroup> {

        @Override
        public CourseTeachingClassStudentGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassStudentGroup courseTeachingClassStudentGroup = new CourseTeachingClassStudentGroup();
            courseTeachingClassStudentGroup.setId(rs.getString("id"));
            courseTeachingClassStudentGroup.setGroupId(rs.getString("t_group_id"));
            courseTeachingClassStudentGroup.setCourseTeachingClassId(rs.getString("t_course_teaching_class_id"));
            courseTeachingClassStudentGroup.setShowIndex(rs.getInt("show_index"));

            return courseTeachingClassStudentGroup;
        }
    }


}
