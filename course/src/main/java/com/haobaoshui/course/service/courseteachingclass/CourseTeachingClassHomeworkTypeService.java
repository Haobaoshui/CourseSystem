package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkType;
import com.haobaoshui.course.model.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface CourseTeachingClassHomeworkTypeService {


    CourseTeachingClassHomeworkType getByID(String id);

    /**
     * 根据id删除
     */
    void deleteById(HttpServletRequest request, String id);

    /**
     * 根据t_course_teaching_class_id删除
     */
    void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id);

    String add(String t_course_teaching_class_id, String name, String note);

    Page<CourseTeachingClassHomeworkType> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);

    List<CourseTeachingClassHomeworkType> getList(String t_course_teaching_class_id);

    void update(String id, String name, String note);


}
