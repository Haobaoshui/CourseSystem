package com.haobaoshui.course.service.attendance.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.Attendance;
import com.haobaoshui.course.model.attendance.AttendanceViewData;
import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClass;
import com.haobaoshui.course.model.user.TeacherViewData;
import com.haobaoshui.course.repository.attendance.AttendanceRepository;
import com.haobaoshui.course.service.attendance.AttendanceService;
import com.haobaoshui.course.service.attendance.AttendanceStudentService;
import com.haobaoshui.course.service.course.CourseService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassService;
import com.haobaoshui.course.service.user.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Autowired
    AttendanceStudentService attendanceStudentService;

    @Autowired
    private CourseTeachingClassService courseTeachingClassService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;


    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;


    }


    @Override
    public String add(String t_course_teaching_class_id, String t_attendance_type_id, String t_teacher_id, String begin_datetime, String end_datetime) {
        return attendanceRepository.add(t_course_teaching_class_id, t_attendance_type_id, t_teacher_id, begin_datetime, end_datetime);
    }


    /**
     * 根据id删除考勤信息
     */
    @Override
    public void deleteById(String id) {
        attendanceStudentService.deleteByAttendanceId(id);
        attendanceRepository.deleteById(id);
    }


    /**
     * 根据教师id删除考勤信息
     */
    @Override
    public void deleteByTeacherId(String t_teacher_id) {
        List<Attendance> list = attendanceRepository.getByTeacherID(t_teacher_id);
        if (list == null) return;
        for (Attendance a : list) attendanceStudentService.deleteByAttendanceId(a.getId());
        attendanceRepository.deleteByTeacherId(t_teacher_id);
    }

    /**
     * 根据教师id删除考勤信息
     */
    @Override
    public void deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        List<Attendance> list = attendanceRepository.getByCourseTeachingClassID(t_course_teaching_class_id);
        if (list == null) return;
        for (Attendance a : list) attendanceStudentService.deleteByAttendanceId(a.getId());
        attendanceRepository.deleteByCourseTeachingClassId(t_course_teaching_class_id);
    }

    /**
     * 根据考勤类型删除考勤信息
     */
    @Override
    public void deleteByAttendanceTypeId(String t_attendance_type_id) {
        List<Attendance> list = attendanceRepository.getByAttendanceTypeId(t_attendance_type_id);
        if (list == null) return;
        for (Attendance a : list) attendanceStudentService.deleteByAttendanceId(a.getId());
        attendanceRepository.deleteByAttendanceTypeId(t_attendance_type_id);
    }

    @Override
    public Attendance getById(String id) {
        return attendanceRepository.getByID(id);
    }

    @Override
    public List<Attendance> getByCourseTeachingClassID(String t_course_teaching_class_id) {
        return attendanceRepository.getByCourseTeachingClassID(t_course_teaching_class_id);
    }


    @Override
    public Page<AttendanceViewData> getPage(String t_course_teaching_class_id, String t_attendance_type_id, int pageNo, int pageSize) {
        Page<Attendance> attendancePage = attendanceRepository.getPage(t_course_teaching_class_id, t_attendance_type_id, pageNo, pageSize);
        if (attendancePage == null) return null;


        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<AttendanceViewData> list = new ArrayList<>();


        for (Attendance attendance : attendancePage.getResult()) {
            AttendanceViewData attendanceViewData = getAttendanceViewDataByAttendanceId(attendance.getId());
            if (attendanceViewData != null) list.add(attendanceViewData);
        }


        return new Page<>(startIndex, attendancePage.getTotalCount(), pageSize, list);
    }

    @Override
    public void update(String id, String t_teacher_id, Date begin_datetime, Date end_datetime) {
        attendanceRepository.update(id, t_teacher_id, begin_datetime, end_datetime);
    }


    /**
     * @param t_attendance_id
     * @return
     */
    @Override
    public AttendanceViewData getAttendanceViewDataByAttendanceId(String t_attendance_id) {

        Attendance attendance = attendanceRepository.getByID(t_attendance_id);

        if (attendance == null) return null;

        AttendanceViewData data = new AttendanceViewData();
        data.setAttendance(attendance);

        CourseTeachingClass courseteachingclass = courseTeachingClassService.getById(attendance
                .getCourseTeacingClassId());
        data.setCourseteachingclass(courseteachingclass);


        Course course = courseService.getById(courseteachingclass.getCourseId());
        data.setCourse(course);

        TeacherViewData teacherviewdata = teacherService.getTeacherViewById(attendance.getTeacherId());
        data.setTeacherviewdata(teacherviewdata);

        data.setHasCheckin(attendanceStudentService.getCount(t_attendance_id) > 0);

        return data;
    }


}
