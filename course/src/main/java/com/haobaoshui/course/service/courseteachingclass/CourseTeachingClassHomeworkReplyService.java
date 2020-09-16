package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReply;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReplyFile;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReplyViewData;
import com.haobaoshui.course.model.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public interface CourseTeachingClassHomeworkReplyService {


    // 增加
     void add(HttpServletRequest request, String t_course_teaching_class_homework_submit_baseinfo_id, String t_teacher_id,
                    String title, String content, MultipartFile[] files);

    // 删除
     void deleteByID(HttpServletRequest request, String t_course_teaching_class_homework_reply_id);

    /**
     * 根据t_teacher_id删除回复
     */
    void deleteByTeacherID(HttpServletRequest request, String t_teacher_id);

    void deleteByCourseTeachingClassHomeworkSubmitBaseinfoID(HttpServletRequest request,
                                                             String t_course_teaching_class_homework_submit_baseinfo_id);

    void update(String id, String title, String content, Date modifieddate, String filename, String filepath);

    void update(HttpServletRequest request, String t_course_teaching_class_homework_reply_id, String t_teacher_id, String title,
                String content, MultipartFile[] files);

    CourseTeachingClassHomeworkReplyFile getFileByID(String id);

    CourseTeachingClassHomeworkReply getByID(String id);

    CourseTeachingClassHomeworkReplyViewData getCourseTeachingClassHomeworkReplyViewDataByID(String id);

    int getCount(String t_course_teaching_class_homework_submit_baseinfo_id, String t_student_id);

    Page<CourseTeachingClassHomeworkReplyViewData> getPage(String t_course_teaching_class_homework_baseinfo_id, String t_student_id,
                                                           int pageNo, int pageSize);


}
