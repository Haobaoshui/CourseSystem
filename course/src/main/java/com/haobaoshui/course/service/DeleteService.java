package com.haobaoshui.course.service;

import javax.servlet.http.HttpServletRequest;


/**
 * 删除服务，需要注意数据之间存在关联，为了保证数据完整，必须注意删除次序
 */


public interface DeleteService {


    /**
     * 删除学院
     */
    public void deleteSchoolById(HttpServletRequest request, String t_school_id);

    /**
     * 删除系部
     */
    public void deleteDepartmentById(HttpServletRequest request, String t_department_id);

    /**
     * 删除自然班
     */
    public void deleteNaturalClassById(HttpServletRequest request, String t_natural_class_id);


    /**
     * 根据t_user_id删除用户,将该用户从系统中彻底删除
     */
    public void deleteByUserId(HttpServletRequest request, String t_user_id);

    /**
     * 删除教师
     */
    public void deleteTeacherById(HttpServletRequest request, String t_teacher_id);

    /**
     * 根据学生学号删除学生
     */
    public void deleteStudentByStudentNum(HttpServletRequest request, String t_student_num);


    /**
     * 删除学生
     */
    public void deleteStudentById(HttpServletRequest request, String t_student_id);

    /**
     * 删除用户基本信息
     */
    public void deleteUserBasicInfoByID(String t_user_basic_info_id);

    /**
     * 删除联系方式
     */
    public void deleteUserContactInfoById(String t_user_contact_info_id);

    /**
     * 删除联系类型
     */
    public void deleteUserContactTypeById(String t_user_contact_type_id);

    /**
     * 删除教学班级-教师
     */
    public void deleteCourseTeachingClassTeacher(String t_course_teaching_class_teacher_id);


    /**
     * 删除教学班
     */
    public void deleteTeachingClassById(HttpServletRequest request, String t_teaching_class_id);

    /**
     * 删除课程-教学班
     */
    public void deleteCourseTeachingClassById(HttpServletRequest request, String t_course_teaching_class_id);


    /**
     * 从课程教学班中删除一个学生
     */
    public void deleteStudentFromCourseTeachingClass(HttpServletRequest request, String t_course_teaching_class_id, String t_student_id);

    /**
     * 删除考勤类型
     */
    public void deleteAttanceTypeById(String t_attendance_type_id);

    public void deleteTeachingTypeById(HttpServletRequest request, String t_teaching_type_id);
}
