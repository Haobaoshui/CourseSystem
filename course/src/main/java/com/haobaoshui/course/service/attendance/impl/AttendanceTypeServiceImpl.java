package com.haobaoshui.course.service.attendance.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.attendance.AttendanceType;
import com.haobaoshui.course.repository.attendance.AttendanceTypeRepository;
import com.haobaoshui.course.service.attendance.AttendanceService;
import com.haobaoshui.course.service.attendance.AttendanceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AttendanceTypeServiceImpl implements AttendanceTypeService {
    private final AttendanceTypeRepository attendanceTypeRepository;
    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceTypeServiceImpl(AttendanceTypeRepository attendanceTypeRepository, AttendanceService attendanceService) {
        this.attendanceTypeRepository = attendanceTypeRepository;
        this.attendanceService = attendanceService;
    }


    @Override
    public String add(AttendanceType attendanceType) {
        return attendanceTypeRepository.add(attendanceType);
    }

    @Override
    public String add(String name, String note) {
        return attendanceTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return attendanceTypeRepository.deleteById(id);
    }

    @Override
    public int delete(AttendanceType attendanceType) {
        if (attendanceType == null) return 0;
        return deleteById(attendanceType.getId());
    }

    @Override
    public int deleteAll() {
        return attendanceTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return attendanceTypeRepository.update(id, name, note);
    }

    @Override
    public int update(AttendanceType attendanceType) {
        return attendanceTypeRepository.update(attendanceType);
    }

    @Override
    public int getCount() {
        return attendanceTypeRepository.getCount();
    }

    @Override
    public List<AttendanceType> getAllByLikeName(String name) {
        return attendanceTypeRepository.getAllByLikeName(name);
    }

    @Override
    public AttendanceType getByName(String name) {
        return attendanceTypeRepository.getByName(name);
    }

    @Override
    public AttendanceType getByID(String id) {
        return attendanceTypeRepository.getByID(id);
    }

    @Override
    public List<AttendanceType> getAll() {
        return attendanceTypeRepository.getAll();
    }

    @Override
    public Page<AttendanceType> getPage(int pageNo, int pageSize) {
        return attendanceTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<AttendanceType> getAllPage() {
        return attendanceTypeRepository.getAllPage();
    }

    @Override
    public Page<AttendanceType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return attendanceTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
