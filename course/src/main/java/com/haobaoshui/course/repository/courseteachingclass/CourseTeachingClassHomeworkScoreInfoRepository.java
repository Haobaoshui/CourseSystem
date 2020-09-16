package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkScoreInfo;

import java.util.List;


public interface CourseTeachingClassHomeworkScoreInfoRepository {


    void update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
                String t_score_show_type_id);

    void updateByCourseTeachingClassIdAndHomeworkTypeId(String t_course_teaching_class_id,
                                                        String t_course_teaching_class_homeworktype_id, String t_score_marking_type_id,
                                                        String t_score_show_type_id);

    /*
     * 根据ID得到用户
     */
    CourseTeachingClassHomeworkScoreInfo getByID(String id);

    /**
     * 根据
     */
    CourseTeachingClassHomeworkScoreInfo getByCourseTeachingClassHomeworkBaseinfoID(
            String t_course_teaching_class_homework_baseinfo_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkScoreInfo> getByScoreMarkingTypeID(String t_score_marking_type_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkScoreInfo> getByScoreShowTypeID(String t_score_show_type_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkScoreInfo> PageQuery(String t_course_teaching_class_id,
                                                         String t_course_teaching_class_homeworktype_id, int PageBegin, int PageSize);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkScoreInfo> getAllOrderByAsc(String t_course_teaching_class_id,
                                                                String t_course_teaching_class_homeworktype_id);

    /**
     * 根据
     */
    List<CourseTeachingClassHomeworkScoreInfo> getAllOrderByDesc(String t_course_teaching_class_id,
                                                                 String t_course_teaching_class_homeworktype_id);

    /* 增加用户 */
    String add(CourseTeachingClassHomeworkScoreInfo score);

    String add(String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
               String t_score_show_type_id);

    String add(String t_course_teaching_class_homework_baseinfo_id);

    void deleteById(String id);

    void deleteByCourseTeachingClassHomeworkInfoId(String t_course_teaching_class_homework_baseinfo_id);

    void deleteByScoreMarkingTypeId(String t_score_marking_type_id);

    void deleteByScoreShowTypeId(String t_score_show_type_id);

    long getCount(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id);

}
