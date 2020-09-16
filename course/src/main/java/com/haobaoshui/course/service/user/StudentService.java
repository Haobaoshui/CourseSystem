package com.haobaoshui.course.service.user;

import com.haobaoshui.course.exception.StudentExistException;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.user.Student;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.model.user.UserBasicInfo;
import com.haobaoshui.course.model.user.UserContactInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface StudentService {


    /**
     * 从Excel上传学生清单，然后添加到数据库中
     */
    void UploadFromExcel(String[] groupId, MultipartFile file) throws Exception;


    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param stu
     */
    String insert(Student stu) throws StudentExistException;

    /**
     * @param student_num 用户名
     * @return Student
     */
    Student getStudentByStudentNum(String student_num);

    Student getStudentByStudentId(String t_student_id);

    /**
     * 更新学生信息,更新内容较为简单，仅包括基本信息
     */
    void UpdateStudentInfo(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex,
                           String[] contacttypeId, String[] user_contact_value);

    /**
     * 更新学生信息，更新内容全面
     */
    void UpdateStudentInfo(String t_user_id, String user_contact_info_name, String user_basic_info_birthday,
                           String user_basic_info_sex, String[] contacttypeId, String[] user_contact_value);

    /**
     * 学生是否存在
     */
    String isExist(String t_natural_class_id, String student_name, String student_num);

    /**
     * 添加学生
     *
     * @param t_natural_class_id       自然班id
     * @param user_password            用户名密码
     * @param student_num              学号
     * @param userBasicInfoName        姓名
     * @param user_basic_info_birthday 生日
     * @param user_basic_info_sex      性别
     * @param contacttypeId            联系类型id
     * @param user_contact_value       联系类型值
     * @param groupId                  组
     * @return t_student_id
     */
    String AddStudent(String t_natural_class_id, String user_password, String student_num,
                      String userBasicInfoName, String user_basic_info_birthday, String user_basic_info_sex,
                      String[] contacttypeId, String[] user_contact_value, String[] groupId) throws Exception;

    /**
     * 添加学生
     *
     * @throws Exception
     */

    String AddStudent(String schoolName, String departmentName, String naturalclassname, String student_name,
                      String student_num) throws Exception;

    /**
     * 添加学生
     *
     * @throws Exception
     */
    String AddStudent(String schoolName, String departmentName, String naturalclassname, String name,
                      String student_num, String[] groupId) throws Exception;

    void updateStudent(Student student, UserBasicInfo userbasicinfo, UserContactInfo[] usercontactinfos,
                       String[] groupId);

    /**
     * 根据学号得到学生视图
     */
    StudentViewData getStudentViewByStudentNum(String student_num);

    /**
     * 根据学生id得到学生视图
     */
    StudentViewData getStudentViewByStudentId(String t_student_id);

    /**
     * 根据用户id得到学生视图
     */
    StudentViewData getStudentViewByUserId(String t_user_id);

    /**
     * 得到学院全体学生视图
     */
    Page<StudentViewData> getPageByNaturalClassId(String t_naturalclass_id, int pageNo, int pageSize);

    /**
     * 得到教学班全体学生视图
     */
    Page<StudentViewData> getPageByCourseTeachingClassId(String t_course_teaching_class_id, int pageNo,
                                                         int pageSize);


    /**
     * 得到系部全体学生视图
     */
    List<StudentViewData> getStudentViewByDepartmentId(String t_department_id);

    /**
     * 得到班级全体学生视图
     */
    List<StudentViewData> getStudentViewByNaturalClassId(String t_naturalclass_id);

    /**
     * 删除学生，需要把相关所有同学生有关信息全部删除，然后才能够删除
     */
    void deleteById(String t_student_id);

    /**
     * 得到所有未分组的学生
     */
    List<StudentViewData> getNotGroupedStudent(String t_course_teaching_class_id);


    /*
     * 根据教学班id得到用户
     */
    List<StudentViewData> getStudentViewByCourseTeachingClassId(String t_course_teaching_class_id);


    /**
     * 得到指定自然班学生人数
     */
    long getCountByNaturalClassId(String t_natural_class_id);

    /**
     * 得到指定教学班人数
     */
    long getCountByCourseTeachingClassId(String t_course_teaching_class_id);


}
