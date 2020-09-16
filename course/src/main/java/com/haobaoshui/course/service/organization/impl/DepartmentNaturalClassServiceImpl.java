package com.haobaoshui.course.service.organization.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.DepartmentNaturalClass;
import com.haobaoshui.course.model.organization.DepartmentNaturalClassViewData;
import com.haobaoshui.course.model.organization.NaturalClass;
import com.haobaoshui.course.repository.organization.DepartmentNaturalClassRepository;
import com.haobaoshui.course.service.organization.DepartmentNaturalClassService;
import com.haobaoshui.course.service.organization.DepartmentService;
import com.haobaoshui.course.service.organization.NaturalClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DepartmentNaturalClassServiceImpl implements DepartmentNaturalClassService {


    private final DepartmentNaturalClassRepository departmentNaturalClassRepository;
    private final NaturalClassService naturalClassService;
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentNaturalClassServiceImpl(DepartmentNaturalClassRepository departmentNaturalClassRepository,
                                             NaturalClassService naturalClassService, DepartmentService departmentService) {
        this.departmentNaturalClassRepository = departmentNaturalClassRepository;
        this.naturalClassService = naturalClassService;
        this.departmentService = departmentService;
    }


    @Override
    public String addIds(String t_department_id, String t_natural_class_id) {
        Department department = departmentService.getByID(t_department_id);
        if (department == null) return null;
        NaturalClass naturalClass = naturalClassService.getById(t_natural_class_id);
        if (naturalClass == null) return null;
        return departmentNaturalClassRepository.add(t_department_id, t_natural_class_id);
    }

    @Override
    public void delete(String t_natural_class_id) {

    }

    @Override
    public DepartmentNaturalClass getByNaturalClassId(String t_natural_class_id) {
        return departmentNaturalClassRepository.getByNaturalclassId(t_natural_class_id);
    }

    @Override
    public Department getDepartmentByNaturalClassId(String t_natural_class_id) {
        DepartmentNaturalClass departmentNaturalClass = departmentNaturalClassRepository.getByNaturalclassId(t_natural_class_id);
        if (departmentNaturalClass == null || departmentNaturalClass.getDepartmentId() == null) return null;
        return departmentService.getByID(departmentNaturalClass.getDepartmentId());
    }


    @Override
    public Page<DepartmentNaturalClassViewData> getPage(String t_department_id, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public String getNaturalclassIdByNaturalClassName(String naturalclassname) {
        NaturalClass n = naturalClassService.getByName(naturalclassname);
        if (n != null) return n.getId();
        return null;
    }

    @Override
    public Department getDepartmentNaturalClassId(String t_natural_class_id) {
        DepartmentNaturalClass departmentNaturalClass = departmentNaturalClassRepository.getByNaturalclassId(t_natural_class_id);
        if (departmentNaturalClass == null) return null;

        return departmentService.getByID(departmentNaturalClass.getDepartmentId());
    }


    @Override
    public List<NaturalClass> getNaturalClassByDepartmentId(String t_department_id) {
        List<DepartmentNaturalClass> departmentNaturalClassList = departmentNaturalClassRepository.getByDepartmentId(t_department_id);
        if (departmentNaturalClassList == null || departmentNaturalClassList.size() == 0) return null;

        List<NaturalClass> naturalClassList = new ArrayList<>();
        for (DepartmentNaturalClass departmentNaturalClass : departmentNaturalClassList) {
            NaturalClass naturalClass = naturalClassService.getById(departmentNaturalClass.getNaturalClassId());
            if (naturalClass != null) naturalClassList.add(naturalClass);
        }
        return naturalClassList;
    }


    @Override
    public NaturalClass getNaturalClassById(String id) {
        DepartmentNaturalClass departmentNaturalClass = departmentNaturalClassRepository.getByNaturalclassId(id);
        if (departmentNaturalClass == null) return null;
        return naturalClassService.getById(departmentNaturalClass.getNaturalClassId());
    }

    @Override
    public String getIdByDepartmentNaturalclassId(String t_department_id, String t_natural_class_id) {
        return departmentNaturalClassRepository.getIdByDepartmentNaturalclassId(t_department_id, t_natural_class_id);
    }

    @Override
    public String getIdByDepartmentIdNaturalclassName(String t_department_id, String t_natural_class_name) {
        return departmentNaturalClassRepository.getIdByDepartmentIdNaturalclassName(t_department_id, t_natural_class_name);
    }

    @Override
    public DepartmentNaturalClass getByDepartmentNaturalclassId(String t_department_id, String t_natural_class_id) {
        return departmentNaturalClassRepository.getByDepartmentNaturalclassId(t_department_id, t_natural_class_id);
    }

    @Override
    public List<NaturalClass> getNaturalClassBySchoolId(String t_school_id) {
        return null;
    }

    @Override
    public List<DepartmentNaturalClass> getDepartmentNaturalClassByDepartmentId(String t_department_id) {
        return null;
    }

    @Override
    public DepartmentNaturalClassViewData getNaturalClassSchoolViewDataByID(String id) {
        return null;
    }

    @Override
    public Page<NaturalClass> getNaturalClassPage(String t_department_id, int pageNo, int pageSize) {
        return null;
    }

}
