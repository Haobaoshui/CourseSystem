package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.FileNodePropertyManager;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.StatisticsData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkStatisticsViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfo;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfoViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


public interface CourseTeachingClassHomeworkSubmitService {


    CourseTeachingClassHomeworkSubmitFile getFileByID(String id);


    /**
     * 增加作业
     */
    void add(HttpServletRequest request, FileNodePropertyManager fileNodePropertyManager,
             String t_course_teaching_class_homework_baseinfo_id, String t_student_id, String title, String content,
             MultipartFile[] files);

    // 删除
    void deleteByID(HttpServletRequest request, String t_course_teaching_class_homework_submit_baseinfo_id);

    void deleteByStudentID(HttpServletRequest request, String t_student_id);

    /**
     * 根据t_course_teaching_class_id和t_student_id删除学生该课程中的提交作业
     */
    void deleteByCourseTeachingClassIdAndStudentID(HttpServletRequest request, String t_course_teaching_class_id,
                                                   String t_student_id);

    void deleteByCourseTeachingClassHomeworkBaseinfoID(HttpServletRequest request,
                                                       String t_course_teaching_class_homework_baseinfo_id);


    void update(HttpServletRequest request, String t_course_teaching_class_homework_submit_baseinfo_id,
                FileNodePropertyManager fileNodePropertyManager, String t_student_id, String title, String content,
                MultipartFile[] files);

    CourseTeachingClassHomeworkSubmitBaseinfo getByID(String id);

    long getCount(String t_course_teaching_class_homework_baseinfo_id, String t_student_id);

    String add(String t_course_teaching_class_homework_baseinfo_id, int fileNodeId, String t_student_id,
               String title, String content, Date submitdate, Date modifieddate, String filename, String filepath);

    Page<CourseTeachingClassHomeworkStatisticsViewData> getPageofStatisticsViewData(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize);

    void update(String id, String title, String content, Date modifieddate, String filename, String filepath);

    /**
     * 将指定作业进行压缩
     */
    void zipByHomeworkBaseInfoId(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id);

    /**
     * 学生是否允许提交作业
     */
    boolean canSubmit(String t_course_teaching_class_homework_baseinfo_id, String t_student_id);

    /**
     * 统计数据
     */
    StatisticsData getStatisticsData(String t_course_teaching_class_homework_baseinfo_id);

    /**
     * 图表数据
     */
    List<com.haobaoshui.course.model.chartDataValue> getSubmitChartDataValue(String t_course_teaching_class_homework_baseinfo_id);


    /**
     * 得到指定提交情况的详细信息
     */
    CourseTeachingClassHomeworkSubmitBaseinfoViewData getCourseTeachingClassHomeworkSubmitBaseinfoViewDataById(
            String t_course_teaching_class_homework_submit_baseinfo_id);


    Page<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize);

    Page<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getPageAll(
            String t_course_teaching_class_homework_baseinfo_id);

    Page<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, String t_student_id, int pageNo, int pageSize);

    /**
     * 得到指定学生、指定作业的提交情况
     */
    List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getViewDataByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(
            String t_course_teaching_class_homework_baseinfo_id, String t_student_id);

    /**
     * 得到指定学生提交情况
     */
    List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getCourseTeachingClassHomeworkSubmitBaseinfoViewDataByStudentID(
            String t_student_id);
}
