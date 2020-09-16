package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkBaseinfo;
import com.haobaoshui.course.model.FileRequirementManager;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkBaseinfoRepository;
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
public class CourseTeachingClassHomeworkBaseinfoRepositoryImpl implements CourseTeachingClassHomeworkBaseinfoRepository {


    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public CourseTeachingClassHomeworkBaseinfoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加用户 */
    @Override
    public String add(CourseTeachingClassHomeworkBaseinfo homeworkbaseinfo) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_baseinfo( id,t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_homework_type_id, flag,file_requirement,title, content, pubdate, enddate) VALUES(?,?,?,?,?,?,?,?,?,?)", newid, homeworkbaseinfo.getCourseTeachingClassId(),
                homeworkbaseinfo.getTeacherId(), homeworkbaseinfo.getCourseTeachingClassHomeworkTypeId(),
                homeworkbaseinfo.getFlag(), homeworkbaseinfo.getFileRequirement(),
                homeworkbaseinfo.getTitle(), homeworkbaseinfo.getContent(), homeworkbaseinfo.getPubdate(), homeworkbaseinfo.getEnddate());
        return newid;


    }

    @Override
    public String add(String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_homework_type_id,
                      Integer flag, String file_requirement, String title, String content, Date pubdate, Date enddate) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course_teaching_class_homework_baseinfo( id,t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_homework_type_id, flag,file_requirement,title, content, pubdate, enddate) VALUES(?,?,?,?,?,?,?,?,?,?)", t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_homework_type_id,
                flag, file_requirement, title, content, pubdate, enddate);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_homework_baseinfo WHERE id=?", id);

    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_id=?", t_course_teaching_class_id);


    }

    @Override
    public int deleteByTeacherId(String t_teacher_id) {
        return jdbcTemplate.update("DELETE FROM t_course_teaching_class_homework_baseinfo WHERE t_teacher_id=?", t_teacher_id);


    }

    @Override
    public int update(String id, String t_course_teaching_class_id, String t_teacher_id, String t_course_teaching_class_homework_type_id,
                      Integer flag, String file_requirement, String title, String content, Date pubdate, Date enddate) {
        if (id == null || t_teacher_id == null) return 0;
        return jdbcTemplate.update("update t_course_teaching_class_homework_baseinfo set t_course_teaching_class_id=?, t_teacher_id=?, t_course_teaching_class_homework_type_id=?, flag=?,file_requirement=?,title=?, content=?, pubdate=?, enddate=?  WHERE id=?", t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_homework_type_id,
                flag, file_requirement, title, content, pubdate, enddate, id);


    }

    @Override
    public int update(String id, String t_teacher_id, Integer flag, String file_requirement, String title,
                      String content, Date enddate) {
        if (id == null || t_teacher_id == null) return 0;

        return jdbcTemplate.update("update t_course_teaching_class_homework_baseinfo set t_teacher_id=?,  flag=?,file_requirement=?,title=?, content=?,  enddate=?  WHERE id=?", t_teacher_id, flag, file_requirement, title, content, enddate, id);


    }

    @Override
    public int update(String id, String t_teacher_id, String title, String content, Date enddate) {
        if (id == null || t_teacher_id == null) return 0;

        return jdbcTemplate.update("update t_course_teaching_class_homework_baseinfo set t_teacher_id=?,  title=?, content=?,  enddate=?  WHERE id=?", t_teacher_id, title, content, enddate, id);


    }

    @Override
    public int getCount(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_id=? and t_course_teaching_class_homework_type_id=?", new Object[]{t_course_teaching_class_id, t_course_teaching_class_homeworktype_id}, Integer.class);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public CourseTeachingClassHomeworkBaseinfo getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_baseinfo WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_course_teaching_class_homework_baseinfo WHERE id=?", new Object[]{id}, new CourseTeachingClassHomeworkBaseinfoMapper());


    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<CourseTeachingClassHomeworkBaseinfo> getByHomeWorkTypeID(String t_course_teaching_class_homework_type_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_homework_type_id=?", new
                Object[]{t_course_teaching_class_homework_type_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_homework_type_id=?", new Object[]{t_course_teaching_class_homework_type_id}, new CourseTeachingClassHomeworkBaseinfoMapper());


    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassID(String t_course_teaching_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new CourseTeachingClassHomeworkBaseinfoMapper());


    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassIDAndHomeworkTypeId(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id) {

        if (getCount(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id) == 0) return null;


        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_id=? and t_course_teaching_class_homework_type_id=?", new Object[]{t_course_teaching_class_id, t_course_teaching_class_homeworktype_id}, new CourseTeachingClassHomeworkBaseinfoMapper());


    }

    /**
     * 根据
     */
    @Override
    public List<CourseTeachingClassHomeworkBaseinfo> getByTeacherID(String t_teacher_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course_teaching_class_homework_baseinfo WHERE t_teacher_id=? ", new
                Object[]{t_teacher_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_baseinfo WHERE t_teacher_id=?", new Object[]{t_teacher_id}, new CourseTeachingClassHomeworkBaseinfoMapper());


    }

    private List<CourseTeachingClassHomeworkBaseinfo> PageQuery(String t_course_teaching_class_id,
                                                                String t_course_teaching_class_homeworktype_id, int PageBegin, int PageSize) {
        if (getCount(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_course_teaching_class_homework_baseinfo WHERE t_course_teaching_class_id=? and t_course_teaching_class_homework_type_id=? order by pubdate desc limit ?,?",
                new Object[]{t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, PageBegin * PageSize, PageSize},
                new CourseTeachingClassHomeworkBaseinfoMapper());

    }

    @Override
    public Page<CourseTeachingClassHomeworkBaseinfo> getPage(String t_course_teaching_class_id,
                                                             String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize) {

        long totalCount = getCount(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id);
        if (totalCount < 1) return new Page<>();

        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkBaseinfo> data = PageQuery(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, pageNo, pageSize);

        return new Page<>(startIndex, totalCount, (int) totalCount, data);


    }

    private static final class CourseTeachingClassHomeworkBaseinfoMapper implements RowMapper<CourseTeachingClassHomeworkBaseinfo> {

        @Override
        public CourseTeachingClassHomeworkBaseinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseTeachingClassHomeworkBaseinfo courseTeachingClassHomeworkBaseinfo = new CourseTeachingClassHomeworkBaseinfo();
            courseTeachingClassHomeworkBaseinfo.setId(rs.getString("id"));
            courseTeachingClassHomeworkBaseinfo.setCourseTeachingClassId(rs.getString("t_course_teaching_class_id"));
            courseTeachingClassHomeworkBaseinfo.setTeacherId(rs.getString("t_teacher_id"));
            courseTeachingClassHomeworkBaseinfo.setCourseTeachingClassHomeworkTypeId(rs.getString("t_course_teaching_class_homework_type_id"));
            courseTeachingClassHomeworkBaseinfo.setEnddate(rs.getTimestamp("enddate"));
            courseTeachingClassHomeworkBaseinfo.setPubdate(rs.getTimestamp("pubdate"));
            courseTeachingClassHomeworkBaseinfo.setFlag(rs.getInt("flag"));

            FileRequirementManager fileRequirementManager = new FileRequirementManager();
            fileRequirementManager.ParseJson(rs.getString("file_requirement"));
            courseTeachingClassHomeworkBaseinfo.setFileRequirement(fileRequirementManager);


            courseTeachingClassHomeworkBaseinfo.setTitle(rs.getString("title"));
            courseTeachingClassHomeworkBaseinfo.setContent(rs.getString("content"));

            return courseTeachingClassHomeworkBaseinfo;
        }
    }


}
