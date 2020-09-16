package com.haobaoshui.course.service.organization.impl;

import com.haobaoshui.course.configure.CommonConstant;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.model.organization.SchoolDepartment;
import com.haobaoshui.course.repository.organization.DepartmentRepository;
import com.haobaoshui.course.repository.organization.SchoolDepartmentRepository;
import com.haobaoshui.course.service.organization.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final SchoolDepartmentRepository schoolDepartmentRepository;
    private final SchoolServiceImpl schoolService;


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 SchoolDepartmentRepository schoolDepartmentRepository,
                                 SchoolServiceImpl schoolService) {
        this.departmentRepository = departmentRepository;
        this.schoolDepartmentRepository = schoolDepartmentRepository;
        this.schoolService = schoolService;
    }


    @Override
    public String add(Department department) {
        return null;
    }

    @Override
    public String addDepartment(String name, String note) {
        return null;
    }

    @Override
    public String add(String t_school_id, String departmentName, String departmentNote) {
        Department department = new Department();
        department.setName(departmentName);
        department.setNote(departmentNote);
        String t_department_id = departmentRepository.add(department);
        if (t_department_id == null) return null;

        //
        if (schoolDepartmentRepository.add(t_school_id, t_department_id) != null) return t_department_id;
        return null;
    }

    @Override
    public String add(String t_school_id, String departmentName) {
        // 没有找到，创建默认系部
        Department department = new Department();
        department.setName(departmentName);
        String t_department_id = departmentRepository.add(department);
        if (t_department_id == null) return null;

        //
        if (schoolDepartmentRepository.add(t_school_id, t_department_id) != null) return t_department_id;
        return null;

    }

    @Override
    public void delete(String t_school_id, String t_department_id) {
        schoolDepartmentRepository.delete(t_school_id, t_department_id);
    }

    @Override
    public int deleteById(String id) {
        return departmentRepository.deleteById(id);
    }

    @Override
    public int deleteByName(String name) {
        return departmentRepository.deleteByName(name);
    }

    @Override
    public int deleteByLikeName(String name) {
        return departmentRepository.deleteByLikeName(name);
    }

    @Override
    public int deleteAll() {
        return departmentRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return departmentRepository.update(id, name, note);
    }

    @Override
    public int updateName(String id, String name) {
        return departmentRepository.updateName(id, name);
    }

    @Override
    public int updateNote(String id, String note) {
        return departmentRepository.updateNote(id, note);
    }

    @Override
    public int update(Department department) {
        return departmentRepository.update(department);
    }

    @Override
    public String getSchoolIdByDepartmentId(String t_department_id) {
        return schoolDepartmentRepository.getSchoolIdByDepartmentId(t_department_id);
    }

    /**
     * 得到默认学院下的默认系部
     */
    @Override
    public String getDefaultDepartmentIdInDefaultSchoolId() {
        String t_school_id = schoolService.getDefaultSchoolId();
        if (t_school_id == null) return null;


        // 在默认学院下面查找默认系部
        return getDefaultDepartmentIdInSchoolId(t_school_id);

    }

    /**
     * 得到指定学院下的默认系部
     */
    @Override
    public String getDefaultDepartmentIdInSchoolId(String t_school_id) {

        if (t_school_id == null) return null;

        // 在学院下面查找默认系部
        List<Department> list = getAll(t_school_id);
        for (Department d : list) if (d.getName().equals(CommonConstant.DEFAULT_DEPARTMENT_NAME)) return d.getId();

        return add(t_school_id, CommonConstant.DEFAULT_DEPARTMENT_NAME);

    }


    @Override
    public Page<Department> getPage(String t_school_id) {
        List<SchoolDepartment> schools = schoolDepartmentRepository.getDepartmentIdBySchoolId(t_school_id);
        List<Department> data = getAll(t_school_id);
        return new Page<>(0, data.size(), data.size(), data);
    }


    @Override
    public List<Department> getAll(String t_school_id) {
        List<SchoolDepartment> schools = schoolDepartmentRepository.getDepartmentIdBySchoolId(t_school_id);
        List<Department> data = new ArrayList<>();

        for (SchoolDepartment schoolDepartment : schools) {
            Department d = departmentRepository.getByID(schoolDepartment.getDepartmentId());

            data.add(d);
        }

        return data;
    }

    /**
     *
     */
    @Override
    public long getDepartmentCount(String t_school_id, String t_department_id) {
        return schoolDepartmentRepository.getDepartmentCount(t_school_id, t_department_id);
    }

    @Override
    public int getCount() {
        return departmentRepository.getCount();
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.getAll();
    }

    @Override
    public List<Department> getAllByLikeName(String name) {
        return departmentRepository.getAllByLikeName(name);
    }

    @Override
    public Department getByName(String name) {
        return departmentRepository.getByName(name);
    }

    @Override
    public Department getByID(String id) {
        return departmentRepository.getByID(id);
    }

    @Override
    public School getSchoolByDepartmentId(String t_department_id) {
        return schoolService.getByDepartmentId(t_department_id);
    }

    @Override
    public Page<Department> getPage(int pageNo, int pageSize) {
        return departmentRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<Department> getAllPage() {
        return departmentRepository.getAllPage();
    }

    @Override
    public Page<Department> getPageByLikeName(String name, int pageNo, int pageSize) {
        return departmentRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
