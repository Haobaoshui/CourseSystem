package com.haobaoshui.course.repository.courseteachingclass;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkDelayed;
import com.haobaoshui.course.model.Page;

import java.util.Date;
import java.util.List;


public interface CourseTeachingClassHomeworkDelayedRepository {
	String add(CourseTeachingClassHomeworkDelayed expriment);

	String add(String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, Date pubdate, Date enddate);

	void deleteById(String id);

	void deleteByCourseTeachingClassHomeworkBaseInfoId(String t_course_teaching_class_homework_baseinfo_id);

	void deleteByTeacherId(String t_teacher_id);

	void update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, Date pubdate, Date enddate);

	void update(String id, String t_teacher_id, Date pubdate, Date enddate);

	void update(String id, Date enddate);

	CourseTeachingClassHomeworkDelayed getByID(String id);


	long getCount(String t_group_id);


	List<CourseTeachingClassHomeworkDelayed> getByCourseTeachingClassHomeworkBaseInfoID(String t_course_teaching_class_homework_baseinfo_id);

	Page<CourseTeachingClassHomeworkDelayed> getPage(String t_course_teaching_class_id, int pageNo, int pageSize);
}
