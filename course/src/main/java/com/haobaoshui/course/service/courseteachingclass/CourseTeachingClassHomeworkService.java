package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.*;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkBaseinfo;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkBaseinfoStudentViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkBaseinfoViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


public interface CourseTeachingClassHomeworkService {


     CourseTeachingClassHomeworkFile getFileByID(String id);


    // 增加
     void add(HttpServletRequest request, String t_course_teaching_class_id,
                    String t_course_teaching_class_homeworktype_id, String t_teacher_id, Integer flag, String file_requirement,
                    String title, String content, String enddate, MultipartFile[] files);

    /**
     * 增加迟交作业
     */
     void addDelayed(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id,
                           String t_teacher_id, String enddate);

    /**
     * 删除id迟交作业
     */
     void deleteDelayedByID(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id);

    /**
     * 根据t_course_teaching_class_homework_baseinfo_id删除迟交作业
     */
     void deleteDelayedByCourseTeachingClassHomeworkBaseinfoId(HttpServletRequest request,
                                                                     String t_course_teaching_class_homework_baseinfo_id);

     void deleteDelayedByTeacherId(HttpServletRequest request, String t_teacher_id);

     void updateDelayed(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id,
                              String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, String enddate);

     void updateDelayed(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id,
                              String t_teacher_id, String enddate);

     void updateDelayed(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id,
                              String enddate);

    // 删除
     void deleteByID(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id);

     void deleteByTeacherId(HttpServletRequest request, String t_teacher_id);

     void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id);

     void deleteByCourseTeachingClassHomeworkTypeId(HttpServletRequest request,
                                                          String t_course_teaching_class_homework_type_id);


     void Update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id,
                       String t_teacher_id, Integer flag, String file_requirement, String title, String content, String enddate,
                       MultipartFile[] files);

     void update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id,
                       String t_teacher_id, String title, String content, String enddate);

     CourseTeachingClassHomeworkBaseinfo getByID(String id);

     List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassID(String t_course_teaching_class_id);

     List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassIDAndHomeworkTypeId(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id);

     Page<CourseTeachingClassHomeworkBaseinfoStudentViewData> getPage(String t_course_teaching_class_id,
                                                                      String t_course_teaching_class_homeworktype_id, String t_student_id, int pageNo, int pageSize);

     CourseTeachingClassHomeworkBaseinfoStudentViewData getCourseTeachingClassHomeworkBaseinfoStudentViewData(
             CourseTeachingClassHomeworkBaseinfoViewData data, String t_student_id);


     void update(String id, String t_course_teaching_class_id, String t_teacher_id, String t_item_kinds_id,
                       Integer flag, String file_requirement, String title, String content, Date pubdate, Date enddate,
                       String filename, String filepath);

     CourseTeachingClassHomeworkBaseinfoViewData getCourseTeachingClassHomeworkBaseinfoViewDataByID(String id);


     Page<CourseTeachingClassHomeworkBaseinfoViewData> getPage(String t_course_teaching_class_id,
                                                                     String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize);
}
