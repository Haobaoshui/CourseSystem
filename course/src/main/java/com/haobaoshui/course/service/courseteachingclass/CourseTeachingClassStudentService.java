package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudent;
import com.haobaoshui.course.model.user.Student;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface CourseTeachingClassStudentService {


    void deleteById(HttpServletRequest request, String t_teaching_class_student_id);

    void deleteByTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id);

    void deleteByStudentId(HttpServletRequest request, String t_student_id);

    void deleteByTeachingClassIdAndStudentId(HttpServletRequest request, String t_course_teaching_class_id, String t_student_id);

    /**
     * 增加学生
     */
    String add(String t_course_teaching_class_id, String t_student_id, int show_index);

    /**
     * 根据id删除
     */
    int deleteById(String id);

    /**
     * 根据课程删除所有的学生
     */
    int deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 删除学生
     */
    int deleteByStudentId(String t_student_id);

    /**
     * 删除指定教学班级中的学生
     */
    int deleteByCourseTeachingClassIdAndStudentId(String t_course_teaching_class_id, String t_student_id);


    /**
     * 修改
     */
    int update(String id, String t_course_teaching_class_id, String t_student_id, int show_index);

    /**
     * 修改show_index
     */
    int update(String id, int show_index);

    /**
     * 修改show_index
     */
    int update(String t_course_teaching_class_id, String t_student_id, int show_index);


    /**
     * 得到教学班人数
     */
    int getStudentCountByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     *
     */
    int getShowIndexMaxByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 得到比指定的学生小一个次序
     */
    int getShowIndexMaxLessthanByCourseTeachingClassId(String t_course_teaching_class_id, String t_student_id);

    /**
     * 得到比指定的学生大一个次序
     */
    int getShowIndexMinMorethanByCourseTeachingClassId(String t_course_teaching_class_id, String t_student_id);


    /**
     *
     */
    int getShowIndexMinByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 学生是否在指定教学班级中存在
     */
    boolean IsStudentExist(String t_course_teaching_class_id, String t_student_id);


    /**
     * 获得制定学生的教学班级信息
     */
    List<CourseTeachingClassStudent> getTeachingClassStudentByStudentId(String t_student_id);

    /**
     * 获得制定学生的教学班级信息
     */
    List<CourseTeachingClassStudent> getTeachingClassStudentByStudentId(Student student);

    /**
     * 获得教学班级学生信息
     */
    List<CourseTeachingClassStudent> getTeachingClassStudentByTeachingClassId(
            String t_course_teaching_class_id);

    CourseTeachingClassStudent getTeachingClassStudentByTeachingClassIdAndStudentId(
            String t_course_teaching_class_id, String t_student_id);

    CourseTeachingClassStudent getTeachingClassStudentByTeachingClassIdAndShowIndex(
            String t_course_teaching_class_id, int show_index);
}
