package com.haobaoshui.course.service.attendance.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceState;
import com.haobaoshui.course.repository.attendance.AttendanceStateRepository;
import com.haobaoshui.course.service.attendance.AttendanceStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AttendanceStateServiceImpl implements AttendanceStateService {


    private final AttendanceStateRepository attendanceStateRepository;

    @Autowired
    public AttendanceStateServiceImpl(AttendanceStateRepository attendanceStateRepository) {
        this.attendanceStateRepository = attendanceStateRepository;
    }


    @Override
    public String add(AttendanceState attendanceState) {
        return attendanceStateRepository.add(attendanceState);
    }

    @Override
    public String add(String name, String note) {
        return attendanceStateRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return attendanceStateRepository.deleteById(id);
    }

    @Override
    public int delete(AttendanceState attendanceState) {
        if (attendanceState == null) return 0;
        return deleteById(attendanceState.getId());
    }

    @Override
    public int deleteAll() {
        return attendanceStateRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return attendanceStateRepository.update(id, name, note);
    }

    @Override
    public int update(AttendanceState attendanceState) {
        return attendanceStateRepository.update(attendanceState);
    }

    @Override
    public int getCount() {
        return attendanceStateRepository.getCount();
    }

    @Override
    public List<AttendanceState> getAllByLikeName(String name) {
        return attendanceStateRepository.getAllByLikeName(name);
    }

    @Override
    public AttendanceState getByName(String name) {
        return attendanceStateRepository.getByName(name);
    }

    @Override
    public AttendanceState getByID(String id) {
        return attendanceStateRepository.getByID(id);
    }

    @Override
    public List<AttendanceState> getAll() {
        return attendanceStateRepository.getAll();
    }

    @Override
    public Page<AttendanceState> getPage(int pageNo, int pageSize) {
        return attendanceStateRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<AttendanceState> getAllPage() {
        return attendanceStateRepository.getAllPage();
    }

    @Override
    public Page<AttendanceState> getPageByLikeName(String name, int pageNo, int pageSize) {
        return attendanceStateRepository.getPageByLikeName(name, pageNo, pageSize);
    }
}
