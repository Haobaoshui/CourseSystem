package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassReferenceType;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassReferenceTypeRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassReferenceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class CourseTeachingClassReferenceTypeServiceImpl implements CourseTeachingClassReferenceTypeService {


    private CourseTeachingClassReferenceTypeRepository courseTeachingClassReferenceTypeRepository;
    private CourseTeachingClassTeacherServiceImpl courseTeachingClassTeacherService;

    @Autowired
    public CourseTeachingClassReferenceTypeServiceImpl(CourseTeachingClassReferenceTypeRepository courseTeachingClassReferenceTypeRepository,
													   CourseTeachingClassTeacherServiceImpl courseTeachingClassTeacherService) {
        this.courseTeachingClassReferenceTypeRepository = courseTeachingClassReferenceTypeRepository;
        this.courseTeachingClassTeacherService = courseTeachingClassTeacherService;
    }

    @Override
	public CourseTeachingClassReferenceType getByID(String id) {
        return courseTeachingClassReferenceTypeRepository.getByID(id);
    }

    @Override
	public void deleteById(HttpServletRequest request, String t_teaching_type_id) {
		courseTeachingClassTeacherService.deleteByTeachingTypeId(request, t_teaching_type_id);
        // 删除自己
		courseTeachingClassReferenceTypeRepository.deleteById(t_teaching_type_id);
    }

    @Override
	public String add(String t_course_teaching_class_id, String name, String note) {
        return courseTeachingClassReferenceTypeRepository.add(t_course_teaching_class_id, name, note);
    }

    @Override
	public Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        return courseTeachingClassReferenceTypeRepository.getPage(t_course_teaching_class_id, pageNo, pageSize);
    }

    @Override
	public Page<CourseTeachingClassReferenceType> getPage(String t_course_teaching_class_id) {
        return courseTeachingClassReferenceTypeRepository.getPage(t_course_teaching_class_id);
    }

    @Override
	public void update(String id, String name, String note) {
		courseTeachingClassReferenceTypeRepository.update(id, name, note);
    }

}
