package com.haobaoshui.course.service.user.impl;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.organization.DepartmentTeacher;
import com.haobaoshui.course.model.organization.School;
import com.haobaoshui.course.model.user.*;
import com.haobaoshui.course.repository.organization.DepartmentTeacherRepository;
import com.haobaoshui.course.repository.authority.UserGroupRepository;
import com.haobaoshui.course.repository.user.TeacherRepository;
import com.haobaoshui.course.service.organization.DepartmentService;
import com.haobaoshui.course.service.organization.SchoolService;
import com.haobaoshui.course.service.user.TeacherService;
import com.haobaoshui.course.service.user.UserBasicInfoService;
import com.haobaoshui.course.service.user.UserContactInfoService;
import com.haobaoshui.course.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeacherServiceImpl implements TeacherService {


    private final TeacherRepository teacherRepository;


    private final UserService userService;


    private final UserContactInfoService userContactInfoService;


    private final UserBasicInfoService userBasicInfoService;


    private final UserGroupRepository userGroupRepository;

    private final DepartmentTeacherRepository departmentTeacherRepository;
    private final DepartmentService departmentService;
    @Autowired
    SchoolService schoolService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              UserService userService,
                              UserContactInfoService userContactInfoService,
                              UserBasicInfoService userBasicInfoService,
                              DepartmentTeacherRepository departmentTeacherRepository,

                              UserGroupRepository userGroupRepository, DepartmentService departmentService) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.userContactInfoService = userContactInfoService;
        this.userBasicInfoService = userBasicInfoService;
        this.departmentTeacherRepository = departmentTeacherRepository;


        this.userGroupRepository = userGroupRepository;
        this.departmentService = departmentService;
    }

    /**
     * 得到系部全体教师视图
     *
     * @param deptid
     * @return
     */
    public static List<TeacherViewData> getTeacherViewByt_department_id(String deptid) {
        List<TeacherViewData> list = new ArrayList<>();
        return list;
    }

    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param department
     * @param teacher
     * @return
     * @throws UserExistException
     */
    @Override
    public String register(Department department, Teacher teacher) throws UserExistException {
        Teacher u = teacherRepository.getTeacherByTeacherNum(teacher.getTeacherNum());
        if (u != null) throw new UserExistException("教师工号已经存在");
        else return teacherRepository.add(department, teacher);
    }

    /**
     * 更新教师个人信息，更新内容较为简单，仅包括基本信息
     *
     * @param t_user_id
     * @param user_basic_info_birthday
     * @param user_basic_info_sex
     * @param contacttypeId
     * @param user_contact_value
     */
    @Override
    public void updateTeacherInfo(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex, String[] contacttypeId,
                                  String[] user_contact_value) {

        userBasicInfoService.updateByUserId(t_user_id, user_basic_info_birthday, user_basic_info_sex);

        userContactInfoService.update(t_user_id, contacttypeId, user_contact_value);

    }

    /**
     * 更新教师个人信息，更新内容全面
     *
     * @param t_user_id
     * @param user_contact_info_name
     * @param user_basic_info_birthday
     * @param user_basic_info_sex
     * @param contacttypeId
     * @param user_contact_value
     */
    @Override
    public void updateTeacherInfo(String t_user_id, String user_contact_info_name, String user_basic_info_birthday,
                                  String user_basic_info_sex, String[] contacttypeId, String[] user_contact_value) {

        userBasicInfoService.updateByUserId(t_user_id, user_contact_info_name, user_basic_info_birthday, user_basic_info_sex);

        userContactInfoService.update(t_user_id, contacttypeId, user_contact_value);
    }

    /**
     * 增加教师信息
     *
     * @param user
     * @param department
     * @param teacher
     * @param userbasicinfo
     * @param usercontactinfos
     * @param groupId
     */
    @Override
    public void addTeacher(User user, Department department, Teacher teacher, UserBasicInfo userbasicinfo,
                           UserContactInfo[] usercontactinfos, String[] groupId) {
        try {

            // 添加用户
            user.EncoderPassword();
            String t_user_id = userService.add(user);

            // 添加教师
            teacher.setUserId(t_user_id);
            register(department, teacher);

            // 添加用户基本信息
            userbasicinfo.setUserId(t_user_id);
            userBasicInfoService.add(userbasicinfo);

            // 添加联系方式
            if (usercontactinfos != null) {
                for (UserContactInfo u : usercontactinfos) u.setUserId(t_user_id);

                for (UserContactInfo u : usercontactinfos) userContactInfoService.add(u);
            }

            // groupid
            if (groupId != null) for (String u : groupId) userGroupRepository.add(u, t_user_id);

        } catch (UserExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 更新教师信息
     *
     * @param user
     * @param teacher
     * @param userbasicinfo
     * @param usercontactinfos
     * @param groupId
     */
    @Override
    public void updateTeacher(User user, Teacher teacher, UserBasicInfo userbasicinfo,
                              UserContactInfo[] usercontactinfos, String[] groupId) {

        if (user == null) return;
        String t_user_id = user.getId();
        if (t_user_id == null) return;
        String teacherNum = teacher.getTeacherNum();

        teacher = teacherRepository.getTeacherByUserId(t_user_id);
        try {

            // 修改教师
            teacherRepository.UpdateTeacherNumById(teacher.getId(), teacherNum);


            //  修改用户基本信息
            userbasicinfo.setUserId(t_user_id);
            userBasicInfoService.updateByUserId(t_user_id, userbasicinfo);

            //  修改联系方式

            userContactInfoService.update(t_user_id, usercontactinfos);


            // groupid
            userGroupRepository.update(t_user_id, groupId);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据教师工号得到教师视图
     *
     * @param teacherNum
     * @return
     */
    @Override
    public TeacherViewData getTeacherViewByTeacherNum(String teacherNum) {
        Teacher teacher = teacherRepository.getTeacherByTeacherNum(teacherNum);
        if (teacher != null) return getTeacherViewByUserId(teacher.getUserId());
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public TeacherViewData getTeacherViewById(String id) {
        Teacher teacher = teacherRepository.getTeacherByID(id);
        if (teacher != null) return getTeacherViewByUserId(teacher.getUserId());
        return null;
    }


    @Override
    public TeacherViewData getTeacherViewByUserId(String t_user_id) {

        Teacher teacher = teacherRepository.getTeacherByUserId(t_user_id);


        if (teacher != null) {
            Department department = getDepartmentByTeacherId(teacher.getId());
            if (department != null) {
                School school = schoolService.getByDepartmentId(department.getId());

                TeacherViewData view = new TeacherViewData();

                view.setTeacher(teacher);
                view.setSchool(school);
                view.setDepartment(department);
                view.setUserInfoViewData(userService.getUserInfoViewDataById(t_user_id));
                return view;
            }
        }

        return null;
    }

    @Override
    public Teacher getTeacherByUserId(String t_user_id) {
        return teacherRepository.getTeacherByUserId(t_user_id);
    }

    /**
     * 得到学院全体教师视图
     *
     * @param t_school_id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<TeacherViewData> getPage(String t_school_id, int pageNo, int pageSize) {
        Page<Teacher> teacherPage = teacherRepository.getPage(t_school_id, pageNo, pageSize);
        if (teacherPage == null) return null;
        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<TeacherViewData> list = new ArrayList<>();


        for (Teacher teacher : teacherPage.getResult()) {
            TeacherViewData teacherViewData = getTeacherViewById(teacher.getId());
            if (teacherViewData != null) list.add(teacherViewData);
        }


        return new Page<>(startIndex, teacherPage.getTotalCount(), pageSize, list);

    }

    @Override
    public List<TeacherViewData> getTeacherViewByDepartmentId(String deptid) {
        List<DepartmentTeacher> teacherList = departmentTeacherRepository.getByDepartmentId(deptid);
        if (teacherList == null || teacherList.size() == 0) return null;

        List<TeacherViewData> list = new ArrayList<>();


        for (DepartmentTeacher departmentTeacher : teacherList) {
            TeacherViewData teacherViewData = getTeacherViewById(departmentTeacher.getTeacherId());
            if (teacherViewData != null) list.add(teacherViewData);
        }
        return list;
    }

    /**
     * @return
     */
    @Override
    public int getTeacherCount() {
        return teacherRepository.getCount();
    }

    @Override
    public Department getDepartmentByTeacherId(String id) {
        String departmentId = departmentTeacherRepository.getDepartmentIdByTeacherId(id);
        return departmentService.getByID(departmentId);
    }

    @Override
    public Department getDepartmentByUserId(String t_user_id) {
        Teacher teacher = teacherRepository.getTeacherByUserId(t_user_id);
        if (teacher == null || teacher.getId() == null) return null;
        return getDepartmentByTeacherId(teacher.getId());
    }


}
