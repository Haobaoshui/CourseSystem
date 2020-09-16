package com.haobaoshui.course.repository.attendance;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.Attendance;

import java.util.Date;
import java.util.List;


public interface AttendanceRepository {
    String add(Attendance attendance);


    String add(String t_course_teaching_class_id, String t_attendance_type_id, String t_teacher_id, String begin_datetime,
               String end_datetime);

    /**
     * @param id
     */
    void deleteById(String id);

    /**
     * @param t_course_teaching_class_id
     */
    void deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * @param t_teacher_id
     */
    void deleteByTeacherId(String t_teacher_id);

    /**
     * @param t_attendance_type_id
     */
    void deleteByAttendanceTypeId(String t_attendance_type_id);

    void update(String id, String t_teacher_id, Date begin_datetime, Date end_datetime);

    /*
     * 根据用户ID得到用户
     */
    Attendance getByID(String id);

    /**
     * 根据教学班得到通知
     */
    List<Attendance> getByCourseTeachingClassID(String t_course_teaching_class_id);

    /**
     * 根据教师得到考勤信息
     */
    List<Attendance> getByTeacherID(String t_teacher_id);

    /**
     * 根据考勤类型得到考勤信息
     */
    List<Attendance> getByAttendanceTypeId(String t_attendance_type_id);


    /**
     * @param t_course_teaching_class_id
     * @return
     */
    int getCount(String t_course_teaching_class_id);

    /**
     * @param t_course_teaching_class_id
     * @param t_attendance_type_id
     * @return
     */
    int getCount(String t_course_teaching_class_id, String t_attendance_type_id);


    /**
     * @param t_course_teaching_class_id
     * @param t_attendance_id
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Attendance> getPage(String t_course_teaching_class_id, String t_attendance_id, int pageNo, int pageSize);
}
