package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfo;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfoRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CourseTeachingClassHomeworkSubmitBaseinfoRepositoryImpl implements CourseTeachingClassHomeworkSubmitBaseinfoRepository {

    // 自己提交作业信息，包括自己提交的和同组提交的。
    private final String WHERE_STATEMENT = "FROM t_course_teaching_class_homework_submit_baseinfo 																					"
            + "WHERE t_course_teaching_class_homework_baseinfo_id=? and 																					"
            + "	(																																		"
            + "		t_student_id=? or t_student_id in 																									"
            + "			( 																																"
            + "				select id from t_student where t_user_id in																					"
            + "					(																														"
            + "						select t_user_id from t_user_group where 																			"
            + "						   t_group_id in																									"
            + "							(																												"
            + "								select t_group_id from t_course_teaching_class_student_group where t_course_teaching_class_id in			"
            + "									(																										"
            + "										select t_course_teaching_class_id from t_course_teaching_class_homework_baseinfo where id=? and flag=2	"
            + "									)																										"
            + "									and 																								"
            + "									t_group_id in(																						"
            + "									select t_group_id from t_user_group where t_user_id in												"
            + "									(																									"
            + "										select t_user_id from t_student  where id=?														"
            + "									)																									"
            + "								)																										"
            + "							) 																											"
            + "																																		"
            + "					)																													"
            + "			)																															"
            + "	)																																	";
    private final String GET_VIEWDATA_BY_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID_WITHOUT_PAGED = "SELECT id  " + WHERE_STATEMENT;
    private final String GET_VIEWDATA_BY_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID = GET_VIEWDATA_BY_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID_WITHOUT_PAGED
            + "		limit ?,?";
    // 自己作业提交信息总数,包括自己提交的和同组提交的。
    private final String GET_COUNT_BY_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID = "SELECT count(*) 	"
            + WHERE_STATEMENT;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseTeachingClassHomeworkSubmitBaseinfoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加 */
    @Override
    public String add(CourseTeachingClassHomeworkSubmitBaseinfo submitinfo) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_submit_baseinfo( id,t_course_teaching_class_homework_baseinfo_id, t_student_id,  title, content, submitdate, modifieddate) VALUES(?,?,?,?,?,?,?)", newid, submitinfo.getCourseTeachingClassHomeworkBaseinfoId(),
                submitinfo.getStudentId(), submitinfo.getTitle(), submitinfo.getContent(), submitinfo.getSubmitdate(),
                submitinfo.getModifieddate());
        return newid;

    }

    @Override
    public String add(String t_course_teaching_class_homework_baseinfo_id, String t_student_id, String title,
                      String content, Date submitdate, Date modifieddate) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_submit_baseinfo( id,t_course_teaching_class_homework_baseinfo_id, t_student_id,  title, content, submitdate, modifieddate) VALUES(?,?,?,?,?,?,?)", newid, t_course_teaching_class_homework_baseinfo_id, t_student_id, title, content,
                submitdate, modifieddate);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_homework_submit_baseinfo where id=?", id);


    }

    @Override
    public int deleteByCourseTeachingClassHomeworkSumbitBaseInfoId(String t_course_teaching_class_homework_baseinfo_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_homework_submit_baseinfo where t_course_teaching_class_homework_baseinfo_id=?", t_course_teaching_class_homework_baseinfo_id);

    }

    @Override
    public int deleteByStudentId(String t_student_id) {
        return jdbcTemplate.update("DELETE from  t_course_teaching_class_homework_submit_baseinfo where t_student_id=?", t_student_id);


    }

    @Override
    public int update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_student_id,
                      String title, String content, Date submitdate, Date modifieddate) {
        if (id == null || t_student_id == null) return 0;

        return jdbcTemplate.update("update t_course_teaching_class_homework_submit_baseinfo set t_course_teaching_class_homework_baseinfo_id=?, t_student_id=?, title=?, content=?, submitdate=?, modifieddate=?  WHERE id=?", t_course_teaching_class_homework_baseinfo_id, t_student_id, title, content,
                submitdate, modifieddate, id);


    }

    @Override
    public int update(String id, String title, String content, Date modifieddate) {
        if (id == null) return 0;

        return jdbcTemplate.update("update t_course_teaching_class_homework_submit_baseinfo set title=?, content=?,  modifieddate=?  WHERE id=?", title, content, modifieddate, id);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassHomeworkSubmitBaseinfo getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_baseinfo WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_homework_submit_baseinfo WHERE id=?", new Object[]{id}, new CourseTeachingClassHomeworkSubmitBaseinfoMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkSubmitBaseinfo> getByCourseTeachingClassHomeworkBaseinfoID(
            String t_course_teaching_class_homework_baseinfo_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_course_teaching_class_homework_baseinfo_id=?", new
                Object[]{t_course_teaching_class_homework_baseinfo_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_course_teaching_class_homework_baseinfo_id=?", new Object[]{t_course_teaching_class_homework_baseinfo_id}, new CourseTeachingClassHomeworkSubmitBaseinfoMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkSubmitBaseinfo> getByStudentID(String t_student_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_student_id=?", new
                Object[]{t_student_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT *FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_student_id=?", new Object[]{t_student_id}, new CourseTeachingClassHomeworkSubmitBaseinfoMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkSubmitBaseinfo> getByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(
            String t_course_teaching_class_homework_baseinfo_id, String t_student_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_course_teaching_class_homework_baseinfo_id=? and t_student_id=?", new
                Object[]{t_course_teaching_class_homework_baseinfo_id, t_student_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_course_teaching_class_homework_baseinfo_id=? and t_student_id=?", new Object[]{t_course_teaching_class_homework_baseinfo_id, t_student_id}, new CourseTeachingClassHomeworkSubmitBaseinfoMapper());


    }

    @Override
    public int getCount(String t_course_teaching_class_homework_baseinfo_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_course_teaching_class_homework_baseinfo_id=?",
                new Object[]{t_course_teaching_class_homework_baseinfo_id},
                Integer.class);
    }

    @Override
    public int getRealCount(String t_course_teaching_class_homework_baseinfo_id) {

        return jdbcTemplate.queryForObject("SELECT count(distinct t_student_id) FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_course_teaching_class_homework_baseinfo_id=?",
                new Object[]{t_course_teaching_class_homework_baseinfo_id},
                Integer.class);
    }

    @Override
    public long getCount(String t_course_teaching_class_homework_baseinfo_id, String t_student_id) {

        return jdbcTemplate.queryForObject(GET_COUNT_BY_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID,
                new Object[]{t_course_teaching_class_homework_baseinfo_id, t_student_id,
                        t_course_teaching_class_homework_baseinfo_id, t_student_id},
                Long.class);
    }

    @Override
    public List<String> PageQueryIds(String t_course_teaching_class_homework_baseinfo_id, int PageBegin, int PageSize) {

        if (PageBegin < 0) PageBegin = 0;

        List<String> list = new ArrayList<>();

        jdbcTemplate.query("SELECT id FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_course_teaching_class_homework_baseinfo_id=?  order by modifieddate DESC limit ?,?",
                new Object[]{t_course_teaching_class_homework_baseinfo_id, PageBegin * PageSize, PageSize}, new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {

                        list.add(rs.getString("id"));
                        // System.out.println(rs.getString("t_user_id"));
                    }

                });
        return list;
    }

    @Override
    public List<String> PageQueryIds(String t_course_teaching_class_homework_baseinfo_id, String t_student_id,
                                     int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        List<String> list = new ArrayList<>();

        jdbcTemplate.query(GET_VIEWDATA_BY_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID,
                new Object[]{t_course_teaching_class_homework_baseinfo_id, t_student_id,
                        t_course_teaching_class_homework_baseinfo_id, t_student_id, PageBegin * PageSize, PageSize},
                new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {

                        list.add(rs.getString("id"));
                    }

                });
        return list;
    }

    @Override
    public List<String> getIdsByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(
            String t_course_teaching_class_homework_baseinfo_id, String t_student_id) {

        List<String> list = new ArrayList<>();

        jdbcTemplate.query(GET_VIEWDATA_BY_HOMEWORK_BASEINFO_ID_AND_STUDENT_ID_WITHOUT_PAGED,
                new Object[]{t_course_teaching_class_homework_baseinfo_id, t_student_id,
                        t_course_teaching_class_homework_baseinfo_id, t_student_id},
                new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {

                        list.add(rs.getString("id"));
                        // System.out.println(rs.getString("t_user_id"));
                    }

                });
        return list;
    }

    @Override
    public List<String> getIdsByStudentID(String t_student_id) {

        List<String> list = new ArrayList<>();

        jdbcTemplate.query("SELECT id FROM t_course_teaching_class_homework_submit_baseinfo WHERE t_student_id=?", new Object[]{t_student_id},
                new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {

                        list.add(rs.getString("id"));

                    }

                });
        return list;
    }

    private static final class CourseTeachingClassHomeworkSubmitBaseinfoMapper implements RowMapper<CourseTeachingClassHomeworkSubmitBaseinfo> {

        @Override
        public CourseTeachingClassHomeworkSubmitBaseinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkSubmitBaseinfo courseTeachingClassHomeworkSubmitBaseinfo = new CourseTeachingClassHomeworkSubmitBaseinfo();
            courseTeachingClassHomeworkSubmitBaseinfo.setId(rs.getString("id"));
            courseTeachingClassHomeworkSubmitBaseinfo.setCourseTeachingClassHomeworkBaseinfoId(
                    rs.getString("t_course_teaching_class_homework_baseinfo_id"));
            courseTeachingClassHomeworkSubmitBaseinfo.setStudentId(rs.getString("t_student_id"));
            courseTeachingClassHomeworkSubmitBaseinfo.setSubmitdate(rs.getTimestamp("submitdate"));
            courseTeachingClassHomeworkSubmitBaseinfo.setModifieddate(rs.getTimestamp("modifieddate"));
            courseTeachingClassHomeworkSubmitBaseinfo.setTitle(rs.getString("title"));
            courseTeachingClassHomeworkSubmitBaseinfo.setContent(rs.getString("content"));

            return courseTeachingClassHomeworkSubmitBaseinfo;
        }
    }

}
