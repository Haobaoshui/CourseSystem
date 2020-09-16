package com.haobaoshui.course.service.attendance.impl;

import com.haobaoshui.course.model.HTMLColors;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.*;
import com.haobaoshui.course.model.chartDataValue;
import com.haobaoshui.course.repository.attendance.AttendanceStudentRepository;
import com.haobaoshui.course.service.attendance.AttendanceModeService;
import com.haobaoshui.course.service.attendance.AttendanceService;
import com.haobaoshui.course.service.attendance.AttendanceStateService;
import com.haobaoshui.course.service.attendance.AttendanceStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AttendanceStudentServiceImpl implements AttendanceStudentService {


    private final AttendanceStudentRepository attendanceStudentRepository;
    private final AttendanceStateService attendanceStateService;
    private final AttendanceModeService attendanceModeService;
    @Autowired
    AttendanceService attendanceService;

    @Autowired
    public AttendanceStudentServiceImpl(AttendanceStudentRepository attendanceStudentRepository,
                                        AttendanceStateService attendanceStateService, AttendanceModeService attendanceModeService) {
        this.attendanceStudentRepository = attendanceStudentRepository;


        this.attendanceStateService = attendanceStateService;
        this.attendanceModeService = attendanceModeService;


    }

    @Override
    public void deleteById(String id) {
        attendanceStudentRepository.deleteById(id);
    }

    @Override
    public void deleteByAttendanceIdStudentId(String t_attendance_id, String t_student_id) {
        attendanceStudentRepository.deleteByAttendanceIdStudentId(t_attendance_id, t_student_id);
    }

    @Override
    public void deleteByAttendanceId(String t_attendance_id) {
        attendanceStudentRepository.deleteByAttendanceId(t_attendance_id);
    }

    @Override
    public void deleteByStudentId(String t_student_id) {
        attendanceStudentRepository.deleteByStudentId(t_student_id);
    }

    /**
     * 删除指定学生、指定课程的考勤
     */
    @Override
    public void deleteByCourseTeachingClassIdAndStudentId(String t_course_teaching_class_id, String t_student_id) {
        List<Attendance> listAttendance = attendanceService.getByCourseTeachingClassID(t_course_teaching_class_id);
        if (listAttendance == null) return;
        for (Attendance a : listAttendance)
            attendanceStudentRepository.deleteByAttendanceIdStudentId(a.getId(), t_student_id);

    }

    @Override
    public String add(String t_attendance_id, String t_student_id, String a_ttendance_state_id, String a_ttendance_mode_id, Date checking_in_datetime) {
        return attendanceStudentRepository.add(t_attendance_id, t_student_id, a_ttendance_state_id, a_ttendance_mode_id, checking_in_datetime);
    }

    private Page<AttendanceStudentViewData> convert(Page<AttendanceStudent> attendanceStudentPage) {
        if (attendanceStudentPage == null) return null;

        long totalCount = attendanceStudentPage.getTotalCount();
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        final int startIndex = 0;

        List<AttendanceStudentViewData> data = new ArrayList<>();

        for (AttendanceStudent attendanceStudent : attendanceStudentPage.getResult()) {
            AttendanceStudentViewData attendanceStudentViewData = getAttendanceStudentViewDataById(attendanceStudent.getAttendanceId());
            if (attendanceStudentViewData != null) data.add(attendanceStudentViewData);

        }

        return new Page<>(startIndex, totalCount, (int) totalCount, data);
    }

    @Override
    public Page<AttendanceStudentViewData> getPage(String t_attendance_id, int pageNo, int pageSize) {

        Page<AttendanceStudent> attendanceStudentPage = attendanceStudentRepository.getPage(t_attendance_id, pageNo, pageSize);
        return convert(attendanceStudentPage);
    }


    @Override
    public void update(String id, String t_student_id, String a_ttendance_state_id, String a_ttendance_mode_id, Date checking_in_datetime) {
        attendanceStudentRepository.update(id, t_student_id, a_ttendance_state_id, a_ttendance_mode_id, checking_in_datetime);
    }

    @Override
    public long getCount(String t_attendance_id) {
        return attendanceStudentRepository.getCount(t_attendance_id);
    }


    @Override
    public Page<AttendanceStudentViewData> getPage(String t_course_teaching_class_id, String t_attendance_type_id, String t_student_id, int pageNo, int pageSize) {

        Page<AttendanceStudent> attendanceStudentPage = attendanceStudentRepository.getPage(t_course_teaching_class_id, t_attendance_type_id, t_student_id, pageNo, pageSize);


        return convert(attendanceStudentPage);
    }


    /**
     * 取得单次统计数据
     */
    @Override
    public List<AttendanceSpecificStatistics> getAttendanceSpecificStatistics(String t_attendance_id) {
        List<AttendanceSpecificStatistics> list = new ArrayList<>();

        List<AttendanceState> listState = attendanceStateService.getAll();
        for (AttendanceState s : listState) {
            AttendanceSpecificStatistics spe = new AttendanceSpecificStatistics();
            spe.setnCount(attendanceStudentRepository.getCountbyAttendanceIDAndStateId(t_attendance_id, s.getId()));
            spe.setState(s);
            list.add(spe);
        }
        return list;
    }

    @Override
    public List<com.haobaoshui.course.model.chartDataValue> getChartDataValueofAttendanceSpecificStatistics(String t_attendance_id) {
        List<com.haobaoshui.course.model.chartDataValue> list = new ArrayList<>();

        List<AttendanceSpecificStatistics> datas = getAttendanceSpecificStatistics(t_attendance_id);
        HTMLColors color = new HTMLColors();
        for (AttendanceSpecificStatistics d : datas) {
            com.haobaoshui.course.model.chartDataValue c = new chartDataValue();
            c.setValue(d.getnCount());
            c.setColor(color.getColor());
            list.add(c);
        }

        return list;
    }

    @Override
    public AttendanceStudentViewData getAttendanceStudentViewDataById(String id) {
        AttendanceStudentViewData data = new AttendanceStudentViewData();

        AttendanceStudent as = attendanceStudentRepository.getByID(id);
        data.setAttendancestudent(as);

        Attendance attendance = attendanceService.getById(as.getAttendanceId());
        data.setAttendance(attendance);

        AttendanceState state = attendanceStateService.getByID(as.getAttendanceStateId());
        data.setState(state);


        AttendanceMode mode = attendanceModeService.getByID(as.getAttendanceModeId());
        data.setMode(mode);

        return data;
    }

}
