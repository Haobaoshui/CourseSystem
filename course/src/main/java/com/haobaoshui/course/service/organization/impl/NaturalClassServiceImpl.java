package com.haobaoshui.course.service.organization.impl;

import com.haobaoshui.course.configure.CommonConstant;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.NaturalClass;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.repository.organization.NaturalClassRepository;
import com.haobaoshui.course.service.organization.DepartmentNaturalClassService;
import com.haobaoshui.course.service.organization.DepartmentService;
import com.haobaoshui.course.service.organization.NaturalClassService;
import com.haobaoshui.course.service.organization.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NaturalClassServiceImpl implements NaturalClassService {


    private final NaturalClassRepository naturalClassRepository;
    private final DepartmentService departmentService;
    private final SchoolService schoolService;
    @Autowired
    DepartmentNaturalClassService departmentNaturalClassService;

    @Autowired
    public NaturalClassServiceImpl(NaturalClassRepository naturalClassRepository,
                                   DepartmentService departmentService,
                                   SchoolService schoolService) {
        this.naturalClassRepository = naturalClassRepository;

        this.departmentService = departmentService;
        this.schoolService = schoolService;
    }

    /**
     * 在指定学院增加班级
     */
    @Override
    public String Add(String t_department_id, String strNaturalClassName) {
        Department department = departmentService.getByID(t_department_id);
        if (department == null) return null;

        NaturalClass n = new NaturalClass();
        n.setName(strNaturalClassName);

        String t_natural_class_id = naturalClassRepository.add(n);

        if (t_natural_class_id != null)
            if (departmentNaturalClassService.addIds(t_department_id, t_natural_class_id) == null) return null;


        return t_natural_class_id;
    }

    /**
     * 在指定学院增加班级
     */
    @Override
    public String Add(String t_department_id, String strNaturalClassName, String strNaturalClassNote) {
        NaturalClass n = new NaturalClass();
        n.setName(strNaturalClassName);
        n.setNote(strNaturalClassNote);

        String t_natural_class_id = naturalClassRepository.add(n);
        if (t_natural_class_id != null)
            if (departmentNaturalClassService.addIds(t_department_id, t_natural_class_id) == null) return null;
        return t_natural_class_id;
    }

    /**
     * 在默认学院默认系部中增加班级
     */

    @Override
    public String Add2DefaultSchoolAndDefaultDepartment(String strNaturalClassName, String strNaturalClassNote) {
        String t_department_id = departmentService.getDefaultDepartmentIdInDefaultSchoolId();
        return Add(t_department_id, strNaturalClassName, strNaturalClassNote);
    }

    /**
     * 在默认学院默认系部中增加班级
     */

    @Override
    public String Add2DefaultSchoolAndDefaultDepartment(String strNaturalClassName) {
        String t_department_id = departmentService.getDefaultDepartmentIdInDefaultSchoolId();

        return Add(t_department_id, strNaturalClassName);
    }

    /**
     * 在学院默认系部中增加班级
     */

    @Override
    public String Add2SchoolDefaultDepartment(String t_school_id, String strNaturalClassName) {
        String t_department_id = departmentService.getDefaultDepartmentIdInSchoolId(t_school_id);
        return Add(t_department_id, strNaturalClassName);
    }

    /**
     * 得到默认学院默认系部下的班级,如果没有则创建该班级
     */
    @Override
    public String getDefaultNaturalClassId(String strNaturalClassName) {


        String t_department_id = departmentService.getDefaultDepartmentIdInDefaultSchoolId();
        if (t_department_id == null) return null;
        //	System.out.println("t_department_id B :"+t_department_id);

        List<NaturalClass> list = departmentNaturalClassService.getNaturalClassByDepartmentId(t_department_id);

        if (list != null) for (NaturalClass n : list)
            if (n.equals(strNaturalClassName))
                if (departmentNaturalClassService.getIdByDepartmentNaturalclassId(t_department_id, n.getId()) == null)
                    return null;
                else return n.getId();


        return Add2DefaultSchoolAndDefaultDepartment(strNaturalClassName);
    }

    /**
     * 得到指定系部下的班级,如果没有则创建该班级
     */
    @Override
    public String getDefaultNaturalClassIdByDepartmentId(String t_department_id, String strNaturalClassName) {
        if (t_department_id == null) return null;

        List<NaturalClass> list = departmentNaturalClassService.getNaturalClassByDepartmentId(t_department_id);

        if (list != null) for (NaturalClass n : list)
            if (n.equals(strNaturalClassName))
                if (departmentNaturalClassService.getIdByDepartmentNaturalclassId(t_department_id, n.getId()) == null)
                    return null;
                else return n.getId();

        return Add(t_department_id, strNaturalClassName);
    }

    /**
     * 得到指定系学院的班级,如果没有则创建该班级
     */
    @Override
    public String getDefaultNaturalClassIdBySchoolId(String t_school_id, String strNaturalClassName) {
        if (t_school_id == null) return null;

        List<NaturalClass> list = departmentNaturalClassService.getNaturalClassBySchoolId(t_school_id);

        if (list != null) for (NaturalClass n : list) if (n.equals(strNaturalClassName)) return n.getId();

        return Add2SchoolDefaultDepartment(t_school_id, strNaturalClassName);
    }

    /**
     * 得到指定系部下的默认班级
     */
    @Override
    public String getDefaultNaturalClassIdByDepartmentId(String t_department_id) {

        if (t_department_id == null) return null;

        List<NaturalClass> list = departmentNaturalClassService.getNaturalClassByDepartmentId(t_department_id);

        if (list != null) for (NaturalClass n : list)
            if (n.equals(CommonConstant.DEFAULT_NATURALCLASS_NAME))
                if (departmentNaturalClassService.getIdByDepartmentNaturalclassId(t_department_id, n.getId()) == null)
                    return null;
                else return n.getId();

        return Add(t_department_id, CommonConstant.DEFAULT_NATURALCLASS_NAME);

    }

    /**
     * 得到默认学院下的默认系部下的默认班级
     */
    @Override
    public String getDefaultNaturalClassId() {
        String t_department_id = departmentService.getDefaultDepartmentIdInDefaultSchoolId();
        return getDefaultNaturalClassIdByDepartmentId(t_department_id);
    }

    /**
     * 得到指定学院下的默认系部下的默认班级
     */
    @Override
    public String getDefaultNaturalClassIdBySchoolId(String t_school_id) {
        if (schoolService.getByID(t_school_id) == null) return null;

        String t_department_id = departmentService.getDefaultDepartmentIdInSchoolId(t_school_id);

        return getDefaultNaturalClassIdByDepartmentId(t_department_id);

    }

    /**
     * 得到指定学院下的指定系部下的默认班级
     */
    @Override
    public String getDefaultNaturalClassIdBySchoolIdAndDepartmentId(String t_school_id, String t_department_id) {

        // 系部位于学院下
        if (!t_school_id.equals(departmentService.getSchoolIdByDepartmentId(t_department_id))) return null;

        return getDefaultNaturalClassIdByDepartmentId(t_department_id);

    }

    /**
     * 得到指定学院下的指定系部下的默认班级
     */
    @Override
    public String getDefaultNaturalClassIdBySchoolNameAndDepartmentName(String school_name, String department_name) {

        School s = schoolService.getByName(school_name);
        if (s == null) return null;

        List<Department> list = departmentService.getAll(s.getId());
        if (list == null) return null;

        for (Department d : list)
            if (d.getName().equals(department_name))
                return getDefaultNaturalClassIdBySchoolIdAndDepartmentId(s.getId(), d.getId());

        return null;

    }

    /**
     * 得到指定学院下的指定系部下的指定班级
     */
    @Override
    public String getDefaultNaturalClassIdBySchoolNameAndDepartmentNameAndNaturallClassName(String school_name,
                                                                                            String department_name, String natural_class_name) {


        String t_school_id = null;
        School s = schoolService.getByName(school_name);
        if (s == null) t_school_id = schoolService.add(school_name, "");
        else t_school_id = s.getId();

        if (t_school_id == null) return null;


        List<Department> list = departmentService.getAll(t_school_id);
        if (list == null) return null;


        for (Department d : list)
            if (d.getName().equals(department_name)) {
                String t_natural_class_id = departmentNaturalClassService.getIdByDepartmentIdNaturalclassName(d.getId(), natural_class_name);
                if (t_natural_class_id == null) return Add(d.getId(), natural_class_name);
                else
                    return t_natural_class_id;
            }


        String t_department_id = departmentService.add(s.getId(), department_name);


        return Add(t_department_id, natural_class_name);
    }


    /**
     * 添加学生
     */
    @Override
    public String getNaturalClassId(String schoolName, String departmentName, String naturalclassname) {


        if (schoolName != null && schoolName.trim().length() == 0) schoolName = null;

        if (departmentName != null && departmentName.trim().length() == 0) departmentName = null;

        if (naturalclassname != null && naturalclassname.trim().length() == 0) naturalclassname = null;

        String t_natural_class_id = null;
        String t_department_id = null;
        String t_school_id = null;

        // 000
        // 放到默认学院的默认系部的默认班级中
        if (schoolName == null && departmentName == null && naturalclassname == null)
            t_natural_class_id = getDefaultNaturalClassId();
        else if (schoolName == null && departmentName == null && naturalclassname != null) {
            // 001


            // 查找班级，如果存在，则放到该班级中
            NaturalClass naturalclass = naturalClassRepository.getByName(naturalclassname);
            // 不存在班级，则放到默认学院的默认系部的指定班级中
            if (naturalclass != null) t_natural_class_id = naturalclass.getId();
            else
                t_natural_class_id = getDefaultNaturalClassId(naturalclassname);
        } else if (schoolName == null && departmentName != null && naturalclassname == null) {
            // 010
            // 放到默认学院的默认系部的默认班级中
            Department d = departmentService.getByName(departmentName);
            if (d != null) {
                t_department_id = d.getId();
                t_natural_class_id = getDefaultNaturalClassIdByDepartmentId(t_department_id);
            }

        } else if (schoolName == null && departmentName != null && naturalclassname != null) {
            // 011
            // 放到默认学院的指定系部的指定班级中
            Department d = departmentService.getByName(departmentName);
            if (d != null) {
                t_department_id = d.getId();
                t_natural_class_id = getDefaultNaturalClassIdByDepartmentId(t_department_id,
                        naturalclassname);
            }

        } else if (schoolName != null && departmentName == null && naturalclassname == null) {
            // 100
            // 放到指定学院的默认系部的默认班级中
            School s = schoolService.getByName(schoolName);
            if (s != null) {
                t_school_id = s.getId();
                t_natural_class_id = getDefaultNaturalClassIdBySchoolId(t_school_id);
            }

        } else if (schoolName != null && departmentName == null && naturalclassname != null) {
            // 101
            // 放到指定学院的默认系部的指定班级中
            School s = schoolService.getByName(schoolName);
            if (s != null) {
                t_school_id = s.getId();
                t_natural_class_id = getDefaultNaturalClassIdBySchoolId(t_school_id,
                        naturalclassname);
            }

        } else // 110
            // 放到指定学院的指定系部的默认班级中
            if (schoolName != null && departmentName != null && naturalclassname == null)
                t_natural_class_id = getDefaultNaturalClassIdBySchoolNameAndDepartmentName(schoolName,
                        departmentName);
            else // 111
                // 放到指定学院的指定系部的指定班级中
                if (schoolName != null && departmentName != null && naturalclassname != null)
                    t_natural_class_id = getDefaultNaturalClassIdBySchoolNameAndDepartmentNameAndNaturallClassName(schoolName,
                            departmentName, naturalclassname);

        return t_natural_class_id;
    }

    @Override
    public NaturalClass getById(String id) {
        return naturalClassRepository.getByID(id);

    }

    @Override
    public NaturalClass getByName(String name) {
        return naturalClassRepository.getByName(name);
    }


}
