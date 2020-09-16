package com.haobaoshui.course.repository.attendance.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceStudent;
import com.haobaoshui.course.repository.attendance.AttendanceStudentRepository;
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
public class AttendanceStudentRepositoryImpl implements AttendanceStudentRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AttendanceStudentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int update(String id, String t_student_id, String a_ttendance_state_id, String t_attendance_mode_id, Date checking_in_datetime) {
        if (id == null || t_student_id == null || a_ttendance_state_id == null || t_attendance_mode_id == null)
            return 0;

        return jdbcTemplate.update("update t_attendance_student set t_student_id=?,a_ttendance_state_id=?,t_attendance_mode_id=?,checking_in_datetime=? WHERE id=?", t_student_id, a_ttendance_state_id, t_attendance_mode_id, checking_in_datetime, id);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public AttendanceStudent getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance_student WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_attendance_student WHERE id=?", new Object[]{id}, new AttendanceStudentMapper());
    }

    /**
     *
     */
    @Override
    public List<AttendanceStudent> getByCourseAttendanceID(String t_attendance_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance_student WHERE t_attendance_id=?", new
                Object[]{t_attendance_id}, Integer.class) != 1) return null;


        return jdbcTemplate.query("SELECT * FROM t_attendance_student WHERE t_attendance_id=?", new Object[]{t_attendance_id}, new AttendanceStudentMapper());
    }

    /* 增加用户 */
    @Override
    public String add(AttendanceStudent as) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_attendance_student(id,t_attendance_id,t_student_id ,t_attendance_state_id,t_attendance_mode_id,checking_in_datetime) VALUES(?,?,?,?,?,?)", newid, as.getAttendanceId(), as.getStudentId(), as.getAttendanceStateId(),
                as.getAttendanceModeId(), as.getCheckingInDatetime());
        return newid;
    }

    @Override
    public String add(String t_attendance_id, String t_student_id, String a_ttendance_state_id, String a_ttendance_mode_id, Date checking_in_datetime) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_attendance_student(id,t_attendance_id,t_student_id ,t_attendance_state_id,t_attendance_mode_id,checking_in_datetime) VALUES(?,?,?,?,?,?)", newid, t_attendance_id, t_student_id, a_ttendance_state_id, a_ttendance_mode_id, checking_in_datetime);
        return newid;
    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_attendance_student where id=?", id);
    }

    @Override
    public int deleteByAttendanceId(String t_attendance_id) {
        return jdbcTemplate.update("DELETE from  t_attendance_student where t_attendance_id=?", t_attendance_id);


    }

    @Override
    public int deleteByStudentId(String t_student_id) {
        return jdbcTemplate.update("DELETE from  t_attendance_student where t_student_id=?", t_student_id);
    }

    @Override
    public int deleteByAttendanceIdStudentId(String t_attendance_id, String t_student_id) {
        return jdbcTemplate.update("DELETE from  t_attendance_student where t_attendance_id=? and t_student_id=?", t_attendance_id, t_student_id);

    }

    /**
     * 根据考勤状况删除
     */
    @Override
    public int deleteByAttendanceStateId(String t_attendance_state_id) {
        return jdbcTemplate.update("DELETE from  t_attendance_student where t_attendance_state_id=?", t_attendance_state_id);


    }

    /**
     * 根据考勤方式删除
     */
    @Override
    public int deleteByAttendanceModeId(String t_attendance_mode_id) {
        return jdbcTemplate.update("DELETE from  t_attendance_student where t_attendance_mode_id=?", t_attendance_mode_id);


    }

    @Override
    public long getCount(String t_attendance_id) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance_student WHERE t_attendance_id=?", new Object[]{t_attendance_id}, Integer.class);
    }


    /**
     * 获得某次考勤的指定状态的人数
     */
    @Override
    public int getCountbyAttendanceIDAndStateId(String t_attendance_id, String t_attendance_state_id) {
        return jdbcTemplate.queryForObject("select count(*) from t_attendance_student where t_attendance_id=? and t_attendance_state_id=?", new Object[]{t_attendance_id, t_attendance_state_id}, Integer.class);

    }

    private List<AttendanceStudent> PageQuery(String t_attendance_id, int PageBegin, int PageSize) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance_student WHERE t_attendance_id=? limit ?,?", new
                Object[]{t_attendance_id, PageBegin * PageSize, PageSize}, Integer.class) != 1) return null;


        return jdbcTemplate.query("SELECT * FROM t_attendance_student WHERE t_attendance_id=? limit ?,?", new Object[]{t_attendance_id, PageBegin * PageSize, PageSize}, new AttendanceStudentMapper());

    }

    @Override
    public Page<AttendanceStudent> getPage(String t_attendance_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_attendance_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        final int startIndex = 0;

        List<AttendanceStudent> data = PageQuery(t_attendance_id, 0, (int) totalCount);

        return new Page<>(startIndex, totalCount, (int) totalCount, data);
    }

    private List<AttendanceStudent> PageQuery(String t_course_teaching_class_id, String t_attendance_type_id, String t_student_id, int PageBegin, int PageSize) {

        if (jdbcTemplate.queryForObject("SELECT count(*) from t_attendance_student   where t_attendance_id in   (select id from t_attendance where t_course_teaching_class_id=? and t_attendance_type_id=? order by begin_datetime ) and t_student_id=?  order by checking_in_datetime DESC limit ?,?", new
                Object[]{t_course_teaching_class_id, t_attendance_type_id, t_student_id, PageBegin * PageSize, PageSize}, Integer.class) != 1)
            return null;


        return jdbcTemplate.query("SELECT * from t_attendance_student   where t_attendance_id in   (select id from t_attendance where t_course_teaching_class_id=? and t_attendance_type_id=? order by begin_datetime ) and t_student_id=?  order by checking_in_datetime DESC limit ?,?", new Object[]{t_course_teaching_class_id, t_attendance_type_id, t_student_id, PageBegin * PageSize, PageSize}, new AttendanceStudentMapper());

    }

    @Override
    public Page<AttendanceStudent> getPage(String t_course_teaching_class_id, String t_attendance_type_id, String t_student_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id, t_attendance_type_id, t_student_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<AttendanceStudent> data = PageQuery(t_course_teaching_class_id, t_attendance_type_id, t_student_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);
    }

    public long getCount(String t_course_teaching_class_id, String t_attendance_type_id, String t_student_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) from t_attendance_student   where t_attendance_id in  (select id from t_attendance where t_course_teaching_class_id=? and t_attendance_type_id=? ) and t_student_id=?", new Object[]{t_course_teaching_class_id, t_attendance_type_id, t_student_id}, Integer.class);

    }


    private static final class AttendanceStudentMapper implements RowMapper<AttendanceStudent> {

        @Override
        public AttendanceStudent mapRow(ResultSet rs, int rowNum) throws SQLException {

            AttendanceStudent attendanceStudent = new AttendanceStudent();
            attendanceStudent.setId(rs.getString("id"));
            attendanceStudent.setAttendanceId(rs.getString("t_attendance_id"));
            attendanceStudent.setAttendanceStateId(rs.getString("t_attendance_state_id"));
            attendanceStudent.setAttendanceModeId(rs.getString("t_attendance_mode_id"));
            attendanceStudent.setStudentId(rs.getString("t_student_id"));
            attendanceStudent.setCheckingInDatetime(rs.getTimestamp("checking_in_datetime"));

            return attendanceStudent;
        }
    }


}
