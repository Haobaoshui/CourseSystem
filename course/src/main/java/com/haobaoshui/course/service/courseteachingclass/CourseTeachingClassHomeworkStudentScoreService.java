package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkStudentScore;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkStudentScoreViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkStudentScoresViewData;
import com.haobaoshui.course.model.user.StudentViewData;

import javax.servlet.http.HttpServletRequest;


public interface CourseTeachingClassHomeworkStudentScoreService {


    CourseTeachingClassHomeworkStudentScore getByID(String id);

    /**
     * 根据id删除
     */
    void deleteById(HttpServletRequest request, String id);

    /**
     * 根据t_course_teaching_class_id删除
     */
    void deleteByScoreInfoId(HttpServletRequest request, String t_course_teaching_class_homework_score_info_id);

    String add(String t_course_teaching_class_homework_score_info_id, String t_student_id, String t_teacher_id,
               String score, String description, String note);

    void deleteByStudentID(HttpServletRequest request, String t_student_id);

    /**
     * update
     */
    void update(String id, String t_course_teaching_class_homework_score_info_id, String t_student_id,
                String t_teacher_id, String score, String description, String note);

    void updateScore(String id, String score);

    void updateDescription(String id, String description);

    void update(String id, String score, String description);

    CourseTeachingClassHomeworkStudentScoreViewData getViewById(String id);

    CourseTeachingClassHomeworkStudentScoresViewData getViewDataByCourseTeachingClassIdAndStudentId(
            String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id, String t_student_id, Integer homework_show_order);

    CourseTeachingClassHomeworkStudentScoresViewData getViewDataByCourseTeachingClassIdAndStudentId(
            String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id,
            StudentViewData student, Integer homework_show_order);

    Page<CourseTeachingClassHomeworkStudentScoresViewData> getStudentScoresPage(
            String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id,
            Integer homework_show_order, int pageNo, int pageSize);


    Page<CourseTeachingClassHomeworkStudentScoreViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize);

}
