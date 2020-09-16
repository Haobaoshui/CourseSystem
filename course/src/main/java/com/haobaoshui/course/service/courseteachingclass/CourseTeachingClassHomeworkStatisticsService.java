package com.haobaoshui.course.service.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkStatisticsViewData;
import com.haobaoshui.course.model.Page;


/**
 * 课程相关信息统计服务
 */


public interface CourseTeachingClassHomeworkStatisticsService {


    /**
     * 作业的统计情况
     */
     Page<CourseTeachingClassHomeworkStatisticsViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize);

}
