package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkStudentScore;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkStudentScoreRepository;
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
public class CourseTeachingClassHomeworkStudentScoreRepositoryImpl implements CourseTeachingClassHomeworkStudentScoreRepository {


    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public CourseTeachingClassHomeworkStudentScoreRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(CourseTeachingClassHomeworkStudentScore score) {
        if (score == null) return null;

        return add(score.getCourseTeachingClassHomeworkScoreInfoId(),
                score.getStudentId(), score.getTeacherId(), score.getScore(), score.getDescription(),
                score.getRecordDate(), score.getNote());

    }

    @Override
    public String add(String t_course_teaching_class_homework_score_info_id, String t_student_id, String t_teacher_id,
                      String score, String description, Date recordDate, String note) {

        String id = GUID.getGUID();

        final String INSERT_STUDENT_SCORE = "INSERT INTO "
                + " t_course_teaching_class_homework_student_score( id,t_course_teaching_class_homework_score_info_id, t_student_id,t_teacher_id, score,description,record_date,note) "
                + " VALUES(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(INSERT_STUDENT_SCORE, id, t_course_teaching_class_homework_score_info_id, t_student_id, t_teacher_id,
                score, description, recordDate, note);
        return id;
    }

    @Override
    public int deleteById(String id) {
        final String DELETE_BY_ID = "DELETE FROM t_course_teaching_class_homework_student_score WHERE id=?";
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int deleteByScoreInfoId(String t_course_teaching_class_homework_score_info_id) {
        final String DELETE_BY_SCORE_INFO_ID = "DELETE FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=?";
        return jdbcTemplate.update(DELETE_BY_SCORE_INFO_ID, t_course_teaching_class_homework_score_info_id);
    }

    @Override
    public int deleteByStudentId(String t_student_id) {
        final String DELETE_BY_STUDENT_ID = "DELETE FROM t_course_teaching_class_homework_student_score WHERE t_student_id=?";
        return jdbcTemplate.update(DELETE_BY_STUDENT_ID, t_student_id);
    }

    @Override
    public int update(String id, String t_course_teaching_class_homework_score_info_id, String t_student_id,
                      String t_teacher_id, String score, String description, Date recordDate, String note) {
        if (id == null) return 0;
        final String UPDATE_BY_ID = "update t_course_teaching_class_homework_student_score set t_course_teaching_class_homework_score_info_id=?, t_student_id=?, t_teacher_id=?, score=? ,description=? ,record_date=?,note=? WHERE id=?";

        return jdbcTemplate.update(UPDATE_BY_ID, t_course_teaching_class_homework_score_info_id, t_student_id, t_teacher_id,
                score, description, recordDate, note, id);
    }

    @Override
    public int updateScore(String id, String score) {
        if (id == null) return 0;

        final String UPDATE_SCORE_BY_ID = "update t_course_teaching_class_homework_student_score set score=? ,record_date=?  WHERE id=?";
        return jdbcTemplate.update(UPDATE_SCORE_BY_ID, score, id);
    }

    @Override
    public int updateDescription(String id, String description, Date recordDate) {
        if (id == null) return 0;

        final String UPDATE_DESCRIPTION_BY_ID = "update t_course_teaching_class_homework_student_score set description=? ,record_date=?  WHERE id=?";
        return jdbcTemplate.update(UPDATE_DESCRIPTION_BY_ID, description, id);
    }

    @Override
    public int update(String id, String score, String description, Date recordDate) {
        if (id == null) return 0;
        final String UPDATE_SCORE_AND_DESCRIPTION_BY_ID = "update t_course_teaching_class_homework_student_score set score=?,description=?,record_date=?   WHERE id=?";
        return jdbcTemplate.update(UPDATE_SCORE_AND_DESCRIPTION_BY_ID, score, description, recordDate, id);
    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassHomeworkStudentScore getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_homework_student_score WHERE id=?", new Object[]{id}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    /**
     * 根据
     */
    @Override
    public CourseTeachingClassHomeworkStudentScore getByCourseTeachingClassHomeworkScoreInfoIdAndStudentId(
            String t_course_teaching_class_homework_score_info_id, String t_student_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=? and t_student_id=?", new
                Object[]{t_course_teaching_class_homework_score_info_id, t_student_id}, Integer.class) != 1)
            return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=? and t_student_id=?", new Object[]{t_course_teaching_class_homework_score_info_id, t_student_id}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkStudentScore> getByCourseTeachingClassHomeworkScoreInfoId(
            String t_course_teaching_class_homework_score_info_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=?", new
                Object[]{t_course_teaching_class_homework_score_info_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=?", new Object[]{t_course_teaching_class_homework_score_info_id}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkStudentScore> getByCourseTeachingClassId(String t_course_teaching_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkStudentScore> getByCourseTeachingClassIdAndHomeworkTypeId(
            String t_course_teaching_class_id, String t_course_teaching_class_homework_type_id) {

        final String GET_BY_COURSE_TEACHING_CLASS_ID_AND_HOMEWORK_TYPE_ID_COUNT = "SELECT count(*)  FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id in "
                + " (select id from t_course_teaching_class_homework_score_info "
                + " where t_course_teaching_class_homework_baseinfo_id in"
                + " (select id from t_course_teaching_class_homework_baseinfo "
                + " where t_course_teaching_class_id=? and t_course_teaching_class_homework_type_id=?))";


        final String GET_BY_COURSE_TEACHING_CLASS_ID_AND_HOMEWORK_TYPE_ID = "SELECT id,t_course_teaching_class_homework_score_info_id, t_student_id,t_teacher_id,score,description,record_date,note  FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id in "
                + " (select id from t_course_teaching_class_homework_score_info "
                + " where t_course_teaching_class_homework_baseinfo_id in"
                + " (select id from t_course_teaching_class_homework_baseinfo "
                + " where t_course_teaching_class_id=? and t_course_teaching_class_homework_type_id=?))";


        if (jdbcTemplate.queryForObject(GET_BY_COURSE_TEACHING_CLASS_ID_AND_HOMEWORK_TYPE_ID_COUNT, new
                Object[]{t_course_teaching_class_id, t_course_teaching_class_homework_type_id}, Integer.class) != 1)
            return null;

        return jdbcTemplate.query(GET_BY_COURSE_TEACHING_CLASS_ID_AND_HOMEWORK_TYPE_ID, new Object[]{t_course_teaching_class_id, t_course_teaching_class_homework_type_id}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkStudentScore> getByStudentId(String t_student_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_student_id=?", new
                Object[]{t_student_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_student_score WHERE t_student_id=?", new Object[]{t_student_id}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkStudentScore> getByTeacherId(String t_teacher_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_teacher_id=?", new
                Object[]{t_teacher_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_student_score WHERE t_teacher_id=?", new Object[]{t_teacher_id}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    @Override
    public boolean isStudentScoreExist(String t_course_teaching_class_homework_score_info_id, String t_student_id) {
        final String GET_COUNT_BY_COURSE_TEACHING_CLASS_SCORE_INFO_ID_AND_STUDENT_ID = "SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=? and t_student_id=?";

        return jdbcTemplate.queryForObject(GET_COUNT_BY_COURSE_TEACHING_CLASS_SCORE_INFO_ID_AND_STUDENT_ID,
                new Object[]{t_course_teaching_class_homework_score_info_id, t_student_id},
                Integer.class) > 0;
    }

    @Override
    public long getCount(String t_course_teaching_class_homework_score_info_id) {
        final String GET_COUNT_BY_COURSE_TEACHING_CLASS_SCORE_INFO_ID = "SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=?";
        return jdbcTemplate.queryForObject(GET_COUNT_BY_COURSE_TEACHING_CLASS_SCORE_INFO_ID,
                new Object[]{t_course_teaching_class_homework_score_info_id},
                Long.class);
    }

    @Override
    public List<CourseTeachingClassHomeworkStudentScore> PageQuery(
            String t_course_teaching_class_homework_score_info_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=?   limit ?,?", new
                Object[]{t_course_teaching_class_homework_score_info_id, PageBegin * PageSize, PageSize}, Integer.class) != 1)
            return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_student_score WHERE t_course_teaching_class_homework_score_info_id=?   limit ?,?", new Object[]{t_course_teaching_class_homework_score_info_id, PageBegin * PageSize, PageSize}, new CourseTeachingClassHomeworkStudentScoreMapper());


    }

    @Override
    public Page<CourseTeachingClassHomeworkStudentScore> getPage(String t_course_teaching_class_homework_baseinfo_id,
                                                                 int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_homework_baseinfo_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkStudentScore> data = PageQuery(t_course_teaching_class_homework_baseinfo_id,
                pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassHomeworkStudentScoreMapper implements RowMapper<CourseTeachingClassHomeworkStudentScore> {

        @Override
        public CourseTeachingClassHomeworkStudentScore mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkStudentScore courseTeachingClassHomeworkStudentScore = new CourseTeachingClassHomeworkStudentScore();
            courseTeachingClassHomeworkStudentScore.setId(rs.getString("id"));
            courseTeachingClassHomeworkStudentScore.setCourseTeachingClassHomeworkScoreInfoId(
                    rs.getString("t_course_teaching_class_homework_score_info_id"));
            courseTeachingClassHomeworkStudentScore.setStudentId(rs.getString("t_student_id"));
            courseTeachingClassHomeworkStudentScore.setTeacherId(rs.getString("t_teacher_id"));
            courseTeachingClassHomeworkStudentScore.setScore(rs.getString("score"));
            courseTeachingClassHomeworkStudentScore.setDescription(rs.getString("description"));
            courseTeachingClassHomeworkStudentScore.setRecordDate(rs.getTimestamp("record_date"));
            courseTeachingClassHomeworkStudentScore.setNote(rs.getString("note"));

            return courseTeachingClassHomeworkStudentScore;
        }
    }

}
