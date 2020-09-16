package com.haobaoshui.course.service.attendance;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.Attendance;
import com.haobaoshui.course.model.attendance.AttendanceViewData;

import java.util.Date;
import java.util.List;


public interface AttendanceService {


    String add(String t_course_teaching_class_id, String t_attendance_type_id, String t_teacher_id, String begin_datetime, String end_datetime);

    /**
     * 根据id删除考勤信息
     */
    void deleteById(String id);


    /**
     * 根据教师id删除考勤信息
     */
    void deleteByTeacherId(String t_teacher_id);

    /**
     * 根据教师id删除考勤信息
     */
    void deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 根据考勤类型删除考勤信息
     */
    void deleteByAttendanceTypeId(String t_attendance_type_id);

    Attendance getById(String id);

    List<Attendance> getByCourseTeachingClassID(String t_course_teaching_class_id);


    AttendanceViewData getAttendanceViewDataByAttendanceId(String t_attendance_id);

    Page<AttendanceViewData> getPage(String t_course_teaching_class_id, String t_attendance_type_id, int pageNo, int pageSize);

    void update(String id, String t_teacher_id, Date begin_datetime, Date end_datetime);


}
