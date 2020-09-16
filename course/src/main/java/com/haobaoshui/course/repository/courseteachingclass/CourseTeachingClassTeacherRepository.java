package com.haobaoshui.course.repository.courseteachingclass;


import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassTeacher;

import java.util.List;


public interface CourseTeachingClassTeacherRepository {


    /**
     * 向授课班增加教师
     */
    String add(String t_course_teaching_class_id, String t_teacher_id, String t_teaching_type_id);

    /**
     * 根据id删除
     */
    int deleteById(String id);


    /**
     * 根据t_course_teaching_class_id删除
     */
    int deleteByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 根据t_teaching_type_id删除
     */
    int deleteByTeachingTypeId(String t_teaching_type_id);

    /**
     * 根据t_teacher_id删除
     */
    int deleteByTeacherId(String t_teacher_id);

    /**
     * 根据t_course_teaching_class_id、t_teacher_id
     */
    int deleteByCourseTeachingClassIdAndTeacherId(String t_course_teaching_class_id, String t_teacher_id);

    /**
     * 根据t_course_teaching_class_id、t_teacher_id、 t_teaching_type_id
     */
    int deleteByCourseTeachingClassIdAndTeacherIdAndTeachingTypeId(String t_course_teaching_class_id,
                                                                   String t_teacher_id, String t_teaching_type_id);

    List<CourseTeachingClassTeacher> getTeachingClassTeacherById(String id);

    List<CourseTeachingClassTeacher> getTeachingClassTeacherByTeachingClassId(String t_course_teaching_class_id);

    List<CourseTeachingClassTeacher> getTeachingClassTeacherByTeacherId(String t_teacher_id);


    boolean IsTeacherExist(String t_course_teaching_class_id, String t_teacher_id, String t_teachingtype_id);


}
