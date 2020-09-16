package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudent;
import com.haobaoshui.course.model.user.Student;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassStudentRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class CourseTeachingClassStudentServiceImpl implements CourseTeachingClassStudentService {

    private final CourseTeachingClassStudentRepository courseTeachingClassStudentRepository;

    @Autowired
    public CourseTeachingClassStudentServiceImpl(CourseTeachingClassStudentRepository courseTeachingClassStudentRepository) {
        this.courseTeachingClassStudentRepository = courseTeachingClassStudentRepository;
    }

    @Override
    public void deleteById(HttpServletRequest request, String t_teaching_class_student_id) {
        courseTeachingClassStudentRepository.deleteById(t_teaching_class_student_id);

    }

    @Override
    public void deleteByTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id) {
        courseTeachingClassStudentRepository.deleteByCourseTeachingClassId(t_course_teaching_class_id);
    }

    @Override
    public void deleteByStudentId(HttpServletRequest request, String t_student_id) {
        courseTeachingClassStudentRepository.deleteByStudentId(t_student_id);
    }

    @Override
    public void deleteByTeachingClassIdAndStudentId(HttpServletRequest request, String t_course_teaching_class_id, String t_student_id) {
        courseTeachingClassStudentRepository.deleteByCourseTeachingClassIdAndStudentId(t_course_teaching_class_id, t_student_id);
    }

    @Override
    public String add(String t_course_teaching_class_id, String t_student_id, int show_index) {
        return courseTeachingClassStudentRepository.add(t_course_teaching_class_id, t_student_id, show_index);
    }

    @Override
    public int deleteById(String id) {
        return courseTeachingClassStudentRepository.deleteById(id);
    }

    @Override
    public int deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        return courseTeachingClassStudentRepository.deleteByCourseTeachingClassId(t_course_teaching_class_id);
    }

    @Override
    public int deleteByStudentId(String t_student_id) {
        return courseTeachingClassStudentRepository.deleteByStudentId(t_student_id);
    }

    @Override
    public int deleteByCourseTeachingClassIdAndStudentId(String t_course_teaching_class_id, String t_student_id) {
        return courseTeachingClassStudentRepository.deleteByCourseTeachingClassIdAndStudentId(t_course_teaching_class_id, t_student_id);
    }

    @Override
    public int update(String id, String t_course_teaching_class_id, String t_student_id, int show_index) {
        return courseTeachingClassStudentRepository.update(id, t_course_teaching_class_id, t_student_id, show_index);
    }

    @Override
    public int update(String id, int show_index) {
        return courseTeachingClassStudentRepository.update(id, show_index);
    }

    @Override
    public int update(String t_course_teaching_class_id, String t_student_id, int show_index) {
        return courseTeachingClassStudentRepository.update(t_course_teaching_class_id, t_student_id, show_index);
    }

    @Override
    public int getStudentCountByCourseTeachingClassId(String t_course_teaching_class_id) {
        return courseTeachingClassStudentRepository.getStudentCountByCourseTeachingClassId(t_course_teaching_class_id);
    }

    @Override
    public int getShowIndexMaxByCourseTeachingClassId(String t_course_teaching_class_id) {
        return courseTeachingClassStudentRepository.getShowIndexMaxByCourseTeachingClassId(t_course_teaching_class_id);
    }

    @Override
    public int getShowIndexMaxLessthanByCourseTeachingClassId(String t_course_teaching_class_id, String t_student_id) {
        return courseTeachingClassStudentRepository.getShowIndexMaxLessthanByCourseTeachingClassId(t_course_teaching_class_id, t_student_id);
    }

    @Override
    public int getShowIndexMinMorethanByCourseTeachingClassId(String t_course_teaching_class_id, String t_student_id) {
        return courseTeachingClassStudentRepository.getShowIndexMinMorethanByCourseTeachingClassId(t_course_teaching_class_id, t_student_id);
    }

    @Override
    public int getShowIndexMinByCourseTeachingClassId(String t_course_teaching_class_id) {
        return courseTeachingClassStudentRepository.getShowIndexMinByCourseTeachingClassId(t_course_teaching_class_id);
    }

    @Override
    public boolean IsStudentExist(String t_course_teaching_class_id, String t_student_id) {
        return courseTeachingClassStudentRepository.IsStudentExist(t_course_teaching_class_id, t_student_id);
    }

    @Override
    public List<CourseTeachingClassStudent> getTeachingClassStudentByStudentId(String t_student_id) {
        return courseTeachingClassStudentRepository.getTeachingClassStudentByStudentId(t_student_id);
    }

    @Override
    public List<CourseTeachingClassStudent> getTeachingClassStudentByStudentId(Student student) {
        return courseTeachingClassStudentRepository.getTeachingClassStudentByStudentId(student);
    }

    @Override
    public List<CourseTeachingClassStudent> getTeachingClassStudentByTeachingClassId(String t_course_teaching_class_id) {
        return courseTeachingClassStudentRepository.getTeachingClassStudentByTeachingClassId(t_course_teaching_class_id);
    }

    @Override
    public CourseTeachingClassStudent getTeachingClassStudentByTeachingClassIdAndStudentId(String t_course_teaching_class_id, String t_student_id) {
        return courseTeachingClassStudentRepository.getTeachingClassStudentByTeachingClassIdAndStudentId(t_course_teaching_class_id, t_student_id);
    }

    @Override
    public CourseTeachingClassStudent getTeachingClassStudentByTeachingClassIdAndShowIndex(String t_course_teaching_class_id, int show_index) {
        return courseTeachingClassStudentRepository.getTeachingClassStudentByTeachingClassIdAndShowIndex(t_course_teaching_class_id, show_index);
    }
}
