package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassTeacher;
import com.haobaoshui.course.model.user.TeacherViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassTeacherRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassTeacherService;
import com.haobaoshui.course.service.user.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
public class CourseTeachingClassTeacherServiceImpl implements CourseTeachingClassTeacherService {


    private final CourseTeachingClassTeacherRepository courseTeachingClassTeacherRepository;
    @Autowired
    TeacherService teacherService;

    @Autowired
    public CourseTeachingClassTeacherServiceImpl(CourseTeachingClassTeacherRepository courseTeachingClassTeacherRepository) {
        this.courseTeachingClassTeacherRepository = courseTeachingClassTeacherRepository;
    }

    @Override
    public void deleteById(HttpServletRequest request, String t_course_teaching_class_teacher_id) {

        // 删除自己
        courseTeachingClassTeacherRepository.deleteById(t_course_teaching_class_teacher_id);
    }

    /**
     * 根据t_course_teaching_class_id
     */
    @Override
    public void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id) {
        courseTeachingClassTeacherRepository.deleteByCourseTeachingClassId(t_course_teaching_class_id);
    }

    /**
     * 根据t_teacher_id
     */
    @Override
    public void deleteByTeacherId(HttpServletRequest request, String t_teacher_id) {
        courseTeachingClassTeacherRepository.deleteByTeacherId(t_teacher_id);
    }

    /**
     * 根据t_teaching_type_id
     */
    @Override
    public void deleteByTeachingTypeId(HttpServletRequest request, String t_teaching_type_id) {
        courseTeachingClassTeacherRepository.deleteByTeachingTypeId(
                t_teaching_type_id);

    }

    /**
     * 根据t_course_teaching_class_id、t_teacher_id
     */
    @Override
    public void deleteByCourseTeachingClassIdAndTeacherId(HttpServletRequest request, String t_course_teaching_class_id,
                                                          String t_teacher_id) {
        courseTeachingClassTeacherRepository.deleteByCourseTeachingClassIdAndTeacherId(t_course_teaching_class_id, t_teacher_id);

    }

    /**
     * 根据t_course_teaching_class_id、t_teacher_id、 t_teaching_type_id
     */
    @Override
    public void deleteByCourseTeachingClassIdAndTeacherIdAndTeachingTypeId(HttpServletRequest request, String t_course_teaching_class_id,
                                                                           String t_teacher_id, String t_teaching_type_id) {
        courseTeachingClassTeacherRepository.deleteByCourseTeachingClassIdAndTeacherIdAndTeachingTypeId(t_course_teaching_class_id, t_teacher_id,
                t_teaching_type_id);

    }

    /**
     * 得到课程全体教师视图
     */
    @Override
    public List<TeacherViewData> getTeacherList(String t_course_teaching_class_id) {
        List<CourseTeachingClassTeacher> listteacher = courseTeachingClassTeacherRepository.getTeachingClassTeacherByTeachingClassId(
                t_course_teaching_class_id);

        List<TeacherViewData> data = new ArrayList<>();
        for (CourseTeachingClassTeacher t : listteacher) {
            TeacherViewData tvd = teacherService.getTeacherViewById(t.getTeacherId());
            data.add(tvd);
        }
        return data;


    }
}
