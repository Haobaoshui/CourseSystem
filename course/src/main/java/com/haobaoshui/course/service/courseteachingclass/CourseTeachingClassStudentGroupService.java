package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudentGroupStatisticsViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudentGroupViewData;
import com.haobaoshui.course.model.user.StudentViewData;

import java.util.List;


public interface CourseTeachingClassStudentGroupService {


    /**
     * 添加
     */
    void add(String t_course_teaching_class_id, String t_group_id);

    /**
     * 添加
     */
    void addGroup(String t_course_teaching_class_id, String group_name, String group_note);

    void deleteById(String id);

    void deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 根据组删除
     */
    void deleteByGroupId(String t_group_id);

    /**
     * 删除
     */
    void deleteGroup(String t_course_teaching_class_id, String t_course_teaching_class_student_group_id);

    void ShowIndexMoveUp(String t_course_teaching_class_id, String t_group_id);

    void ShowIndexMoveDown(String t_course_teaching_class_id, String t_group_id);


    CourseTeachingClassStudentGroupViewData getViewDataByStudentId(String t_course_teaching_class_id, String t_student_id);

    List<CourseTeachingClassStudentGroupViewData> getViewData(String t_course_teaching_class_id);

    /**
     * 得到所有未分组的学生
     */
    List<StudentViewData> getNotGroupedStudent(String t_course_teaching_class_id);

    /**
     * 得到特定分组的学生
     */
    List<StudentViewData> getGroupedStudent(String t_course_teaching_class_id, String t_group_id);

    void AddStudent2Group(String t_course_teaching_class_id, String t_group_id, String[] userids);

    void DeleteStudentFromGroup(String t_course_teaching_class_id, String t_group_id, String t_user_id);

    /**
     * 获得分组数量
     */
    int getGroupCountByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 是否分组
     */
    boolean isGrouped(String t_course_teaching_class_id);

    CourseTeachingClassStudentGroupStatisticsViewData getGroupInfo(String t_course_teaching_class_id);
}
