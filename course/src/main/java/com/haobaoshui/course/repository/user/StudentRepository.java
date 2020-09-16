package com.haobaoshui.course.repository.user;

import com.haobaoshui.course.model.user.Student;

import java.util.List;


public interface StudentRepository {


    /*增加*/
    String add(Student stu);

    /*删除
     * */
    int deleteById(String t_student_id);

    int UpdateStudentNumById(String t_student_id, String student_num);


    /*根据用户ID得到用户
     * */
    Student getStudentByID(String id);

    /*根据学号ID得到用户
     * */
    Student getStudentByStudentNum(String student_num);

    /*根据t_user_id得到用户
     * */
    Student getStudentByUserId(String t_user_id);


    long getCountByNaturalClassId(String t_natural_class_id);


    long getCountByCourseTeachingClassId(String t_course_teaching_class_id);

    List<Student> getByNaturalClassId(String t_natural_class_id);

    List<Student> getByNaturalClassId(String t_natural_class_id, int PageBegin, int PageSize);

    List<Student> getByDepartmentId(String t_department_id);

    List<Student> getByCourseTeachingClassId(String t_course_teaching_class_id, int PageBegin,
                                             int PageSize);

    List<Student> getByCourseTeachingClassId(String t_course_teaching_class_id);

    List<Student> getNotGroupedByCourseTeachingClassId(String t_course_teaching_class_id);


}
