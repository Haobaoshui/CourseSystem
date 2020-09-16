package com.haobaoshui.course.service.attendance.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceMode;
import com.haobaoshui.course.repository.attendance.AttendanceModeRepository;
import com.haobaoshui.course.service.attendance.AttendanceModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AttendanceModeServiceImpl implements AttendanceModeService {

    private final AttendanceModeRepository attendanceModeRepository;

    @Autowired
    public AttendanceModeServiceImpl(AttendanceModeRepository attendanceModeRepository) {
        this.attendanceModeRepository = attendanceModeRepository;
    }


    @Override
    public String add(AttendanceMode attendanceMode) {
        return attendanceModeRepository.add(attendanceMode);
    }

    @Override
    public String add(String name, String note) {
        return attendanceModeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return attendanceModeRepository.deleteById(id);
    }

    @Override
    public int delete(AttendanceMode attendanceMode) {
        if (attendanceMode == null) return 0;
        return deleteById(attendanceMode.getId());
    }

    @Override
    public int deleteAll() {
        return attendanceModeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return attendanceModeRepository.update(id, name, note);
    }

    @Override
    public int update(AttendanceMode attendanceMode) {
        if (attendanceMode == null || attendanceMode.getId() == null || attendanceMode.getName() == null || attendanceMode.getName().length() == 0)
            return 0;
        return attendanceModeRepository.update(attendanceMode);
    }

    @Override
    public int getCount() {
        return attendanceModeRepository.getCount();
    }

    @Override
    public List<AttendanceMode> getAllByLikeName(String name) {
        return attendanceModeRepository.getAllByLikeName(name);
    }

    @Override
    public AttendanceMode getByName(String name) {
        return attendanceModeRepository.getByName(name);
    }

    @Override
    public AttendanceMode getByID(String id) {
        return attendanceModeRepository.getByID(id);
    }

    @Override
    public List<AttendanceMode> getAll() {
        return attendanceModeRepository.getAll();
    }

    @Override
    public Page<AttendanceMode> getPage(int pageNo, int pageSize) {
        return attendanceModeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<AttendanceMode> getAllPage() {
        return attendanceModeRepository.getAllPage();
    }

    @Override
    public Page<AttendanceMode> getPageByLikeName(String name, int pageNo, int pageSize) {
        return attendanceModeRepository.getPageByLikeName(name, pageNo, pageSize);
    }
}
