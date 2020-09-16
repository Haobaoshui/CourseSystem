package com.haobaoshui.course.repository.attendance.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.Attendance;
import com.haobaoshui.course.repository.attendance.AttendanceRepository;
import com.haobaoshui.course.utility.DateTimeSql;
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
public class AttendanceRepositoryImpl implements AttendanceRepository {


    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public AttendanceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(String id, String t_teacher_id, Date begin_datetime, Date end_datetime) {
        if (id == null || t_teacher_id == null || begin_datetime == null || end_datetime == null) return;

        Object[] params = new Object[]{t_teacher_id, begin_datetime, end_datetime, id};
        int[] types = new int[]{Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.VARCHAR};
        jdbcTemplate.update("update t_attendance set t_teacher_id=?,begin_datetime=?,end_datetime=? WHERE id=?", params, types);
    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public Attendance getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_attendance WHERE id=?", new Object[]{id}, new AttendanceMapper());

    }

    /**
     * 根据教学班得到通知
     */
    @Override
    public List<Attendance> getByCourseTeachingClassID(String t_course_teaching_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_attendance WHERE t_course_teaching_class_id=?", new Object[]{t_course_teaching_class_id}, new AttendanceMapper());

    }

    /**
     * 根据教师得到考勤信息
     */
    @Override
    public List<Attendance> getByTeacherID(String t_teacher_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance WHERE t_teacher_id=?", new
                Object[]{t_teacher_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT count(*) FROM t_attendance WHERE t_teacher_id=?", new Object[]{t_teacher_id}, new AttendanceMapper());

    }

    /**
     * 根据考勤类型得到考勤信息
     */
    @Override
    public List<Attendance> getByAttendanceTypeId(String t_attendance_type_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance WHERE t_attendance_type_id=?", new
                Object[]{t_attendance_type_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT count(*) FROM t_attendance WHERE t_attendance_type_id=?", new Object[]{t_attendance_type_id}, new AttendanceMapper());

    }

    /**
     * 增加 用户
     *
     * @param attendance
     * @return
     */
    @Override
    public String add(Attendance attendance) {
        String id = GUID.getGUID();
        attendance.setId(id);
        Object[] params = new Object[]{attendance.getId(), attendance.getCourseTeacingClassId(), attendance.getAttendanceTypeId(),
                attendance.getTeacherId(), attendance.getBeginDatetime(), attendance.getEndDatetime()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP};

        final String INSERT_ATTENDANCE = "INSERT INTO t_attendance(id,t_course_teaching_class_id,t_attendance_type_id,t_teacher_id ,begin_datetime,end_datetime) VALUES(?,?,?,?,?,?)";


        jdbcTemplate.update(INSERT_ATTENDANCE, params, types);
        return id;
    }

    /**
     * 增加用户
     *
     * @param t_course_teaching_class_id
     * @param t_attendance_type_id
     * @param t_teacher_id
     * @param begin_datetime
     * @param end_datetime
     * @return
     */
    @Override
    public String add(String t_course_teaching_class_id, String t_attendance_type_id, String t_teacher_id, String begin_datetime,
                      String end_datetime) {

        String id = GUID.getGUID();

        final String INSERT_ATTENDANCE = "INSERT INTO t_attendance(id,t_course_teaching_class_id,t_attendance_type_id,t_teacher_id ,begin_datetime,end_datetime) VALUES(?,?,?,?,?,?)";


        Object[] params = new Object[]{id, t_course_teaching_class_id, t_attendance_type_id, t_teacher_id,
                DateTimeSql.GetDateTimeNotIncludingSecond(begin_datetime), DateTimeSql.GetDateTimeNotIncludingSecond(end_datetime)};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP};


        jdbcTemplate.update(INSERT_ATTENDANCE, params, types);
        return id;
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.VARCHAR};

        final String DELETE_BY_ID = "DELETE FROM t_attendance WHERE id=?";

        jdbcTemplate.update(DELETE_BY_ID, params, types);
    }

    /**
     * @param t_course_teaching_class_id
     */
    @Override
    public void deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        Object[] params = new Object[]{t_course_teaching_class_id};
        int[] types = new int[]{Types.VARCHAR};

        final String DELETE_BY_COURSE_TEACHING_CLASS_ID = "DELETE FROM t_attendance WHERE t_course_teaching_class_id=?";

        jdbcTemplate.update(DELETE_BY_COURSE_TEACHING_CLASS_ID, params, types);
    }

    /**
     * @param t_teacher_id
     */
    @Override
    public void deleteByTeacherId(String t_teacher_id) {
        Object[] params = new Object[]{t_teacher_id};
        int[] types = new int[]{Types.VARCHAR};

        final String DELETE_BY_TEACHER_ID = "DELETE FROM t_attendance WHERE t_teacher_id=?";
        jdbcTemplate.update(DELETE_BY_TEACHER_ID, params, types);
    }

    /**
     * @param t_attendance_type_id
     */
    @Override
    public void deleteByAttendanceTypeId(String t_attendance_type_id) {
        Object[] params = new Object[]{t_attendance_type_id};
        int[] types = new int[]{Types.VARCHAR};

        final String DELETE_BY_ATTENDANCE_TYPE_ID = "DELETE FROM t_attendance WHERE t_attendance_type_id=?";

        jdbcTemplate.update(DELETE_BY_ATTENDANCE_TYPE_ID, params, types);
    }

    /**
     * @param t_course_teaching_class_id
     * @return
     */
    @Override
    public int getCount(String t_course_teaching_class_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance WHERE t_course_teaching_class_id=?", new
                Object[]{t_course_teaching_class_id}, Integer.class);
    }

    /**
     * @param t_course_teaching_class_id
     * @param t_attendance_type_id
     * @return
     */
    @Override
    public int getCount(String t_course_teaching_class_id, String t_attendance_type_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_attendance WHERE t_course_teaching_class_id=? and t_attendance_type_id=?", new Object[]{t_course_teaching_class_id, t_attendance_type_id},
                Integer.class);
    }

    /**
     * @param t_course_teaching_class_id
     * @param t_attendance_id
     * @param PageBegin
     * @param PageSize
     * @return
     */
    private List<Attendance> PageQuery(String t_course_teaching_class_id, String t_attendance_id, int PageBegin, int PageSize) {

        if (jdbcTemplate.queryForObject("SELECT count (*) FROM t_attendance WHERE t_course_teaching_class_id=? and t_attendance_type_id=? order by begin_datetime desc limit ?,?", new
                Object[]{t_course_teaching_class_id, t_attendance_id, PageBegin * PageSize, PageSize}, Integer.class) != 1)
            return null;

        return jdbcTemplate.query("SELECT * FROM t_attendance WHERE t_course_teaching_class_id=? and t_attendance_type_id=? order by begin_datetime desc limit ?,?", new Object[]{t_course_teaching_class_id, t_attendance_id, PageBegin * PageSize, PageSize}, new AttendanceMapper());


    }

    /**
     * @param t_course_teaching_class_id
     * @param t_attendance_id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<Attendance> getPage(String t_course_teaching_class_id, String t_attendance_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_id, t_attendance_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<Attendance> data = PageQuery(t_course_teaching_class_id, t_attendance_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class AttendanceMapper implements RowMapper<Attendance> {

        public static Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
            Attendance attendance = new Attendance();
            attendance.setId(rs.getString("id"));
            attendance.setCourseTeacingClassId(rs.getString("t_course_teaching_class_id"));
            attendance.setAttendanceTypeId(rs.getString("t_attendance_type_id"));
            attendance.setTeacherId(rs.getString("t_teacher_id"));
            attendance.setBeginDatetime(rs.getTimestamp("begin_datetime"));
            attendance.setEndDatetime(rs.getTimestamp("end_datetime"));


            return attendance;
        }
    }
}
