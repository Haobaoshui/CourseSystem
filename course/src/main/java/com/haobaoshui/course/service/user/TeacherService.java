package com.haobaoshui.course.service.user;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.Department;
import com.haobaoshui.course.model.user.*;

import java.util.List;


public interface TeacherService {


    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param department
     */
    String register(Department department, Teacher teacher) throws UserExistException;


    /**
     * 更新教师个人信息，更新内容较为简单，仅包括基本信息
     */
    void updateTeacherInfo(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex, String[] contacttypeId,
                           String[] user_contact_value);

    /**
     * 更新教师个人信息，更新内容全面
     */
    void updateTeacherInfo(String t_user_id, String user_contact_info_name, String user_basic_info_birthday,
                           String user_basic_info_sex, String[] contacttypeId, String[] user_contact_value);

    /**
     * 增加教师信息
     */
    void addTeacher(User user, Department department, Teacher teacher, UserBasicInfo userbasicinfo,
                    UserContactInfo[] usercontactinfos, String[] groupId);


    /**
     * 更新教师信息
     */
    void updateTeacher(User user, Teacher teacher, UserBasicInfo userbasicinfo,
                       UserContactInfo[] usercontactinfos, String[] groupId);

    /**
     * 根据教师工号得到教师视图
     */
    TeacherViewData getTeacherViewByTeacherNum(String teacherNum);

    TeacherViewData getTeacherViewById(String id);

    TeacherViewData getTeacherViewByUserId(String id);

    Teacher getTeacherByUserId(String t_user_id);

    /**
     * 得到学院全体教师视图
     */
    Page<TeacherViewData> getPage(String t_school_id, int pageNo, int pageSize);

    /**
     * 得到系部全体教师视图
     */
    List<TeacherViewData> getTeacherViewByDepartmentId(String deptid);

    int getTeacherCount();

    Department getDepartmentByTeacherId(String id);

    Department getDepartmentByUserId(String t_user_id);


}
