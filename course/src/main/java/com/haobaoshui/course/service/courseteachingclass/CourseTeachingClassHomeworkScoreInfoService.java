package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkScoreInfo;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkScoreInfoViewData;
import com.haobaoshui.course.model.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface CourseTeachingClassHomeworkScoreInfoService {


     CourseTeachingClassHomeworkScoreInfo getByID(String id);

    /**
     * 根据id删除
     */
     void deleteById(HttpServletRequest request, String id);


    /**
     * 根据t_course_teaching_class_id删除
     */
     void deleteByCourseTeachingClassHomeworkInfoId(HttpServletRequest request,
                                                          String t_course_teaching_class_homework_baseinfo_id);

     void deleteByScoreMarkingTypeId(String t_score_marking_type_id);

     void deleteByScoreShowTypeId(String t_score_show_type_id);

     String add(String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
                      String t_score_show_type_id);

    /**
     * update
     */
     void update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
                       String t_score_show_type_id);

     void updateByCourseTeachingClassIdAndHomeworkTypeId(String t_course_teaching_class_id,
                                                               String t_course_teaching_class_homeworktype_id, String t_score_marking_type_id,
                                                               String t_score_show_type_id);

     CourseTeachingClassHomeworkScoreInfoViewData getViewById(
            String t_course_teaching_class_homework_score_info_id);

     CourseTeachingClassHomeworkScoreInfoViewData getViewByHomeworkBaseinfoId(
            String t_course_teaching_class_homework_baseinfo_id);


     Page<CourseTeachingClassHomeworkScoreInfoViewData> getPage(String t_course_teaching_class_id,
                                                                      String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize);

    /**
     *
     * */
     List<CourseTeachingClassHomeworkScoreInfoViewData> getAllByOrder(String t_course_teaching_class_id,
                                                                            String t_course_teaching_class_homeworktype_id, Integer homework_show_order);

     void addHomeworkBaseInfo(String t_course_teaching_class_homework_baseinfo_id);

     void addHomeworkBaseInfo(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id);
}
