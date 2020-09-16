package com.haobaoshui.course.service.attendance;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceSpecificStatistics;
import com.haobaoshui.course.model.attendance.AttendanceStudentViewData;
import com.haobaoshui.course.model.chartDataValue;

import java.util.Date;
import java.util.List;


public interface AttendanceStudentService {

    String add(String t_attendance_id, String t_student_id, String a_ttendance_state_id, String a_ttendance_mode_id, Date checking_in_datetime);

    void deleteById(String id);

    void deleteByAttendanceIdStudentId(String t_attendance_id, String t_student_id);

    void deleteByAttendanceId(String t_attendance_id);

    void deleteByStudentId(String t_student_id);

    void deleteByCourseTeachingClassIdAndStudentId(String t_course_teaching_class_id, String t_student_id);

    void update(String id, String t_student_id, String a_ttendance_state_id, String a_ttendance_mode_id, Date checking_in_datetime);

    long getCount(String t_attendance_id);

    List<AttendanceSpecificStatistics> getAttendanceSpecificStatistics(String t_attendance_id);

    List<chartDataValue> getChartDataValueofAttendanceSpecificStatistics(String t_attendance_id);

    AttendanceStudentViewData getAttendanceStudentViewDataById(String id);

    Page<AttendanceStudentViewData> getPage(String t_attendance_id, int pageNo, int pageSize);


    Page<AttendanceStudentViewData> getPage(String t_course_teaching_class_id, String t_attendance_type_id, String t_student_id, int pageNo, int pageSize);


}
