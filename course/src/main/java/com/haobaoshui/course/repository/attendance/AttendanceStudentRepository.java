package com.haobaoshui.course.repository.attendance;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceStudent;

import java.util.Date;
import java.util.List;


public interface AttendanceStudentRepository {


    int update(String id, String t_student_id, String a_ttendance_state_id, String t_attendance_mode_id, Date checking_in_datetime);


    AttendanceStudent getByID(String id);


    List<AttendanceStudent> getByCourseAttendanceID(String t_attendance_id);


    String add(AttendanceStudent as);

    String add(String t_attendance_id, String t_student_id, String a_ttendance_state_id, String a_ttendance_mode_id, Date checking_in_datetime);


    int deleteById(String id);

    int deleteByAttendanceId(String t_attendance_id);

    int deleteByStudentId(String t_student_id);

    int deleteByAttendanceIdStudentId(String t_attendance_id, String t_student_id);

    /**
     * 根据考勤状况删除
     */
    int deleteByAttendanceStateId(String a_ttendance_state_id);

    /**
     * 根据考勤方式删除
     */
    int deleteByAttendanceModeId(String t_attendance_mode_id);

    long getCount(String t_attendance_id);


    /**
     * 获得某次考勤的指定状态的人数
     */
    int getCountbyAttendanceIDAndStateId(String t_attendance_id, String t_attendance_state_id);

    Page<AttendanceStudent> getPage(String t_attendance_id, int pageNo, int pageSize);


    Page<AttendanceStudent> getPage(String t_course_teaching_class_id, String t_attendance_type_id, String t_student_id, int pageNo, int pageSize);

}
