package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClass;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassViewData;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.model.user.TeacherViewData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface CourseTeachingClassService {


    /**
     * 从Excel上传学生清单，然后添加到数据库中
     */
    void UploadFromExcel(String t_course_teaching_class_id, MultipartFile file) throws Exception;


    /**
     * 添加学生
     *
     * @param t_course_teaching_class_id 教学班id
     * @return User
     * @throws Exception
     */
    String AddStudent(String t_course_teaching_class_id, String school, String department, String naturalclass,
                      String name, String student_num) throws Exception;


    String add(String t_course_id, String name, String t_course_teaching_term_id, String[] teacherid,
               String[] teachingtypetypeId);

    /**
     * 将学生加入到教学班中
     *
     * @param teachingclassid
     * @param studentid
     */
    void add(String teachingclassid, String[] studentid);

    /**
     * 将教师加入到教学班中
     *
     * @param t_course_teaching_class_id
     */
    void add(String t_course_teaching_class_id, String[] teacherid, String[] teachingtypeid);

    /**
     * 根据t_course_id删除教学-课程信息
     */
    void deleteByCourseId(HttpServletRequest request, String t_course_id);

    /**
     * 根据t_teaching_class_id删除教学-课程信息
     */
    void deleteByCourseTeachingClassIdAndStudentId(HttpServletRequest request, String t_course_teaching_class_id,
                                                   String t_student_id);

    /**
     * 根据t_course_id、t_teaching_class_id删除教学-课程信息
     */
    void deleteByCourseIdAndTeachingClassId(HttpServletRequest request, String t_course_id,
                                            String t_teaching_class_id);

    /**
     * 根据t_course_teaching_class_id删除教学-课程信息
     */
    void deleteById(HttpServletRequest request, String t_course_teaching_class_id);

    String update(String t_course_teaching_class_id, String t_ourse_id, String name,
                  String t_course_teaching_term_id, String[] teacherid, String[] teachingtypetypeId);

    CourseTeachingClass getById(String t_course_teaching_class_id);

    Page<CourseTeachingClassViewData> getPage(int pageNo, int pageSize);

    /**
     * 得到课程全体教师视图
     */
    List<TeacherViewData> getTeacherPage(String t_course_teaching_class_id);

    CourseTeachingClassViewData getTeachingClassViewDataByCourseTeachingClassId(
            String t_course_teaching_class_id);

    /**
     * 得到课程全体教师视图 如果是学生的话，则会得到所有同他上课的授课教师 如果是教师的话，则会得到所有同他所教授课程相关的授课教师
     */
    Page<TeacherViewData> getTeacherViewPageByUserId(String t_user_id);

    Page<StudentViewData> getStudentViewPageByUserId(String t_user_id);

    void ShowIndexMoveUp(String t_course_teaching_class_id, String t_student_id);

    void ShowIndexMoveDown(String t_course_teaching_class_id, String t_student_id);


    List<CourseTeachingClassViewData> getTeacherViewBySchoolId();

    /**
     * 根据用户id得到教学班信息
     */
    List<CourseTeachingClassViewData> getCourseTeachingClassViewDataByUserId(String t_user_id);


}
