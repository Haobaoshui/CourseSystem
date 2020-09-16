package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkStudentScore;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassHomeworkStudentScoreRepository {


    String add(CourseTeachingClassHomeworkStudentScore score);

    String add(String t_course_teaching_class_homework_score_info_id, String t_student_id, String t_teacher_id,
               String score, String description, Date recordDate, String note);

    int deleteById(String id);

    int deleteByScoreInfoId(String t_course_teaching_class_homework_score_info_id);


    int deleteByStudentId(String t_student_id);

    int update(String id, String t_course_teaching_class_homework_score_info_id, String t_student_id,
               String t_teacher_id, String score, String description, Date recordDate, String note);

    int updateScore(String id, String score);

    int updateDescription(String id, String description, Date recordDate);

    int update(String id, String score, String description, Date recordDate);

    /*
     * 根据用户ID得到用户
     */
    CourseTeachingClassHomeworkStudentScore getByID(String id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkStudentScore> getByCourseTeachingClassHomeworkScoreInfoId(
            String t_course_teaching_class_homework_score_info_id);

    /**
     * 根据
     */
    CourseTeachingClassHomeworkStudentScore getByCourseTeachingClassHomeworkScoreInfoIdAndStudentId(
            String t_course_teaching_class_homework_score_info_id, String t_student_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkStudentScore> getByCourseTeachingClassId(String t_course_teaching_class_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkStudentScore> getByCourseTeachingClassIdAndHomeworkTypeId(
            String t_course_teaching_class_id, String t_course_teaching_class_homework_type_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkStudentScore> getByStudentId(String t_student_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkStudentScore> getByTeacherId(String t_teacher_id);


    boolean isStudentScoreExist(String t_course_teaching_class_homework_score_info_id, String t_student_id);

    long getCount(String t_course_teaching_class_homework_score_info_id);

    List<CourseTeachingClassHomeworkStudentScore> PageQuery(
            String t_course_teaching_class_homework_score_info_id, int PageBegin, int PageSize);

    Page<CourseTeachingClassHomeworkStudentScore> getPage(String t_course_teaching_class_homework_baseinfo_id,
                                                          int pageNo, int pageSize);

}
