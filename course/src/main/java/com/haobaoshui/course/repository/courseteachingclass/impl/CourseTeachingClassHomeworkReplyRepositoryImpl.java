package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReply;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkReplyRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;


@Repository
public class CourseTeachingClassHomeworkReplyRepositoryImpl implements CourseTeachingClassHomeworkReplyRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassHomeworkReplyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(String id, String t_course_teaching_class_homework_submit_baseinfo_id, String t_teacher_id, String title,
                       String content, Date submitdate, Date modifieddate) {
        if (id == null || t_teacher_id == null) return;

        Object[] params = new Object[]{t_course_teaching_class_homework_submit_baseinfo_id, t_teacher_id, title, content, submitdate,
                modifieddate, id};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP,
                Types.VARCHAR};

        final String updateSQL = "update t_course_teaching_class_homework_reply set t_course_teaching_class_homework_submit_baseinfo_id=?, t_teacher_id=?,  title=?, content=?, submitdate=?, modifieddate=?  WHERE id=?";

        jdbcTemplate.update(updateSQL, params, types);
    }

    @Override
    public void update(String id, String title, String content, Date modifieddate) {
        if (id == null) return;

        Object[] params = new Object[]{title, content, modifieddate, id};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR};

        final String updateSQL = "update t_course_teaching_class_homework_reply set title=?, content=?,  modifieddate=?  WHERE id=?";

        jdbcTemplate.update(updateSQL, params, types);
    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassHomeworkReply getByID(String id) {

        final String querySQL = "SELECT * FROM t_course_teaching_class_homework_reply WHERE id=?";

        return jdbcTemplate.queryForObject(querySQL, new Object[]{id}, new CourseTeachingClassHomeworkReplyMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkReply> getByCourseTeachingClassHomeworkSubmitBaseInfoID(
            String t_course_teaching_class_homework_submit_baseinfo_id) {

        final String querySQL = "SELECT * FROM t_course_teaching_class_homework_reply WHERE t_course_teaching_class_homework_submit_baseinfo_id=?";
        return jdbcTemplate.query(querySQL,
                new Object[]{t_course_teaching_class_homework_submit_baseinfo_id}, new CourseTeachingClassHomeworkReplyMapper());

    }

    /**
     * 根据t_teacher_id得到回复
     */
    @Override
    public List<CourseTeachingClassHomeworkReply> getByTeacherID(
            String t_teacher_id) {

        final String querySQL = "SELECT * FROM t_course_teaching_class_homework_reply WHERE t_teacher_id=?";

        return jdbcTemplate.query(querySQL,
                new Object[]{t_teacher_id}, new CourseTeachingClassHomeworkReplyMapper());


    }

    /* 增加用户 */
    @Override
    public String add(CourseTeachingClassHomeworkReply expriment) {
        if (expriment == null) return null;

        return add(expriment.getCourseTeachingClassHomeworkSubmitBaseinfoId(),
                expriment.getTeacherId(), expriment.getTitle(), expriment.getContent(), expriment.getSubmitdate(),
                expriment.getModifieddate());
    }

    @Override
    public String add(String t_course_teaching_class_homework_submit_baseinfo_id, String t_teacher_id, String title, String content,
                      Date submitdate, Date modifieddate) {

        String id = GUID.getGUID();

        Object[] params = new Object[]{id, t_course_teaching_class_homework_submit_baseinfo_id, t_teacher_id, title, content, submitdate,
                modifieddate};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP,
                Types.TIMESTAMP};

        final String insertSQL = "INSERT INTO t_course_teaching_class_homework_reply( id,t_course_teaching_class_homework_submit_baseinfo_id, t_teacher_id, title, content, submitdate, modifieddate) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSQL, params, types);
        return id;
    }

    @Override
    public void deleteById(String id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.VARCHAR};

        final String deleteSQL = "DELETE FROM t_course_teaching_class_homework_reply WHERE id=?";

        jdbcTemplate.update(deleteSQL, params, types);
    }

    @Override
    public void deleteByCourseTeachingClassHomeworkSubmitId(String t_course_teaching_class_homework_submit_baseinfo_id) {
        Object[] params = new Object[]{t_course_teaching_class_homework_submit_baseinfo_id};
        int[] types = new int[]{Types.VARCHAR};

        final String DELETE_BY_COURSE_TEACHING_CLASS_HOMEWORK_SUBMIT_BASEINFO_ID = "DELETE FROM t_course_teaching_class_homework_reply WHERE t_course_teaching_class_homework_submit_baseinfo_id=?";

        jdbcTemplate.update(DELETE_BY_COURSE_TEACHING_CLASS_HOMEWORK_SUBMIT_BASEINFO_ID, params, types);
    }

    @Override
    public void deleteByTeacherId(String t_teacher_id) {
        Object[] params = new Object[]{t_teacher_id};
        int[] types = new int[]{Types.VARCHAR};

        final String DELETE_BY_TEACHER_ID = "DELETE FROM t_course_teaching_class_homework_reply WHERE t_teacher_id=?";

        jdbcTemplate.update(DELETE_BY_TEACHER_ID, params, types);
    }

    @Override
    public int getCount(String t_course_teaching_class_homework_submit_baseinfo_id, String t_student_id) {
        final String GET_COUNT_BY_COURSE_TEACHING_CALSS_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID = "SELECT count(*) FROM t_course_teaching_class_homework_reply right join t_course_teaching_class_homework_submit_baseinfo on t_course_teaching_class_homework_reply.t_course_teaching_class_homework_submit_baseinfo_id=t_course_teaching_class_homework_submit_baseinfo.id  WHERE t_course_teaching_class_homework_submit_baseinfo_id=? and t_student_id=?";

        return jdbcTemplate.queryForObject(GET_COUNT_BY_COURSE_TEACHING_CALSS_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID,
                new Object[]{t_course_teaching_class_homework_submit_baseinfo_id, t_student_id}, new int[]{Types.VARCHAR, Types.VARCHAR}, Integer.class);
    }


    private List<CourseTeachingClassHomeworkReply> PageQuery(String t_course_teaching_class_homework_submit_baseinfo_id,
                                                             String t_student_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;


        if (getCount(t_course_teaching_class_homework_submit_baseinfo_id, t_student_id) == 0) return null;

        final String querySQL = "SELECT t_course_teaching_class_homework_reply.* FROM t_course_teaching_class_homework_reply right join t_course_teaching_class_homework_submit_baseinfo on t_course_teaching_class_homework_reply.t_course_teaching_class_homework_submit_baseinfo_id=t_course_teaching_class_homework_submit_baseinfo.id WHERE t_course_teaching_class_homework_submit_baseinfo_id=? and t_student_id=?  limit ?,?";

        return jdbcTemplate.query(querySQL,
                new Object[]{t_course_teaching_class_homework_submit_baseinfo_id, t_student_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassHomeworkReplyMapper());

    }

    @Override
    public Page<CourseTeachingClassHomeworkReply> getPage(String t_course_teaching_class_homework_submit_baseinfo_id,
                                                          String t_student_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_homework_submit_baseinfo_id, t_student_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkReply> data = PageQuery(t_course_teaching_class_homework_submit_baseinfo_id, t_student_id,
                pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseTeachingClassHomeworkReplyMapper implements RowMapper<CourseTeachingClassHomeworkReply> {

        @Override
        public CourseTeachingClassHomeworkReply mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkReply reply = new CourseTeachingClassHomeworkReply();
            reply.setId(rs.getString("id"));
            reply.setCourseTeachingClassHomeworkSubmitBaseinfoId(rs.getString("t_course_teaching_class_homework_submit_baseinfo_id"));
            reply.setTeacherId(rs.getString("t_teacher_id"));
            reply.setSubmitdate(rs.getTimestamp("submitdate"));
            reply.setModifieddate(rs.getTimestamp("modifieddate"));
            reply.setTitle(rs.getString("title"));
            reply.setContent(rs.getString("content"));

            return reply;
        }
    }
}
