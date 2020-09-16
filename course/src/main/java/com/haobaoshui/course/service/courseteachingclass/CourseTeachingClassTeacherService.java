package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.user.TeacherViewData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface CourseTeachingClassTeacherService {


    void deleteById(HttpServletRequest request, String t_course_teaching_class_teacher_id);

    /**
     * 根据t_course_teaching_class_id
     */
    void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id);

    /**
     * 根据t_teacher_id
     */
    void deleteByTeacherId(HttpServletRequest request, String t_teacher_id);

    /**
     * 根据t_teaching_type_id
     */
    void deleteByTeachingTypeId(HttpServletRequest request, String t_teaching_type_id);

    /**
     * 根据t_course_teaching_class_id、t_teacher_id
     */
    void deleteByCourseTeachingClassIdAndTeacherId(HttpServletRequest request, String t_course_teaching_class_id,
                                                   String t_teacher_id);

    /**
     * 根据t_course_teaching_class_id、t_teacher_id、 t_teaching_type_id
     */
    void deleteByCourseTeachingClassIdAndTeacherIdAndTeachingTypeId(HttpServletRequest request, String t_course_teaching_class_id,
                                                                    String t_teacher_id, String t_teaching_type_id);

    List<TeacherViewData> getTeacherList(String t_course_teaching_class_id);


}
