package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkType;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkTypeRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class CourseTeachingClassHomeworkTypeServiceImpl implements CourseTeachingClassHomeworkTypeService {
    private final CourseTeachingClassHomeworkTypeRepository courseTeachingClassHomeworkTypeRepository;
    private final CourseTeachingClassReferenceServiceImpl courseTeachingClassReferenceService;

    @Autowired
    public CourseTeachingClassHomeworkTypeServiceImpl(CourseTeachingClassHomeworkTypeRepository courseTeachingClassHomeworkTypeRepository,
                                                      CourseTeachingClassReferenceServiceImpl courseTeachingClassReferenceService) {
        this.courseTeachingClassHomeworkTypeRepository = courseTeachingClassHomeworkTypeRepository;
        this.courseTeachingClassReferenceService = courseTeachingClassReferenceService;
    }

    @Override
    public CourseTeachingClassHomeworkType getByID(String id) {
        return courseTeachingClassHomeworkTypeRepository.getByID(id);
    }

    /**
     * 根据id删除
     */
    @Override
    public void deleteById(HttpServletRequest request, String id) {
        courseTeachingClassReferenceService.deleteByReferenceTypeId(request, id);
        courseTeachingClassHomeworkTypeRepository.deleteById(id);
    }

    /**
     * 根据t_course_teaching_class_id删除
     */
    @Override
    public void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id) {
        List<CourseTeachingClassHomeworkType> list = courseTeachingClassHomeworkTypeRepository.getByCourseTeachingClassID(t_course_teaching_class_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkType t : list) deleteById(request, t.getId());

    }

    @Override
    public String add(String t_course_teaching_class_id, String name, String note) {
        return courseTeachingClassHomeworkTypeRepository.add(t_course_teaching_class_id, name, note);
    }

    @Override
    public Page<CourseTeachingClassHomeworkType> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        return courseTeachingClassHomeworkTypeRepository.getPage(t_course_teaching_class_id, pageNo, pageSize);
    }

    @Override
    public List<CourseTeachingClassHomeworkType> getList(String t_course_teaching_class_id) {
        return courseTeachingClassHomeworkTypeRepository.getByCourseTeachingClassID(t_course_teaching_class_id);
    }

    @Override
    public void update(String id, String name, String note) {
        courseTeachingClassHomeworkTypeRepository.update(id, name, note);
    }


}
