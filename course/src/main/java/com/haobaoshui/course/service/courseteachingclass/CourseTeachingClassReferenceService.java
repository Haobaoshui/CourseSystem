package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReference;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceFile;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceViewData;
import com.haobaoshui.course.model.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


public interface CourseTeachingClassReferenceService {


     CourseTeachingClassReferenceFile getFileByID(String id);


    // 增加
     void add(HttpServletRequest request, String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id,
                    String t_teacher_id, String title, String content, MultipartFile[] files);

    // 删除
     void deleteByID(HttpServletRequest request, String t_course_teaching_class_reference_id);


    /**
     * 根据t_course_teaching_class_id删除
     */
     void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id);

    /**
     * 根据t_teacher_id删除
     */
     void deleteByTeacherId(HttpServletRequest request, String t_teacher_id);

    /**
     * 根据t_course_teaching_class_id、t_teacher_id删除
     */
     void deleteByCourseTeachingClassIdAndTeacherId(HttpServletRequest request, String t_course_teaching_class_id,
                                                          String t_teacher_id);

    /**
     * 根据t_course_teaching_class_reference_type_id删除
     */
     void deleteByReferenceTypeId(HttpServletRequest request, String t_course_teaching_class_reference_type_id);

    /**
     * 根据t_course_teaching_class_id、t_teacher_id、t_course_teaching_class_reference_type_id删除
     */
     void deleteByCourseTeachingClassIdAndTeacherIdAndReferenceTypeId(HttpServletRequest request, String t_course_teaching_class_id,
                                                                            String t_teacher_id, String t_course_teaching_class_reference_type_id);


     void update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, String title,
                       String content, MultipartFile[] files);

     void Update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, String title,
                       String content);

     CourseTeachingClassReference getByID(String id);

     CourseTeachingClassReferenceViewData getCourseTeachingClassHomeworkBaseinfoViewDataByID(String id);


     Page<CourseTeachingClassReferenceViewData> getPage(HttpServletRequest request, String t_course_teaching_class_id,
                                                              String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize);

}
