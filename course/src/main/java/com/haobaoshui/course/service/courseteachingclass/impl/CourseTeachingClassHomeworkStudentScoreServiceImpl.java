package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.*;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkStudentScoreRepository;
import com.haobaoshui.course.repository.database.ScoreMarkingTypeRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkScoreInfoService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkStudentScoreService;
import com.haobaoshui.course.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseTeachingClassHomeworkStudentScoreServiceImpl implements CourseTeachingClassHomeworkStudentScoreService {


    private final CourseTeachingClassHomeworkStudentScoreRepository courseTeachingClassHomeworkStudentScoreRepository;
    private final ScoreMarkingTypeRepository scoreMarkingTypeRepository;
    private final StudentService studentService;
    private final CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService;

    @Autowired
    public CourseTeachingClassHomeworkStudentScoreServiceImpl(CourseTeachingClassHomeworkStudentScoreRepository courseTeachingClassHomeworkStudentScoreRepository,
                                                              ScoreMarkingTypeRepository scoreMarkingTypeRepository,
                                                              StudentService studentService, CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService) {
        this.courseTeachingClassHomeworkStudentScoreRepository = courseTeachingClassHomeworkStudentScoreRepository;
        this.scoreMarkingTypeRepository = scoreMarkingTypeRepository;
        this.studentService = studentService;
        this.courseTeachingClassHomeworkScoreInfoService = courseTeachingClassHomeworkScoreInfoService;
    }


    @Override
    public CourseTeachingClassHomeworkStudentScore getByID(String id) {
        return courseTeachingClassHomeworkStudentScoreRepository.getByID(id);
    }

    /**
     * 根据id删除
     */
    @Override
    public void deleteById(HttpServletRequest request, String id) {
        courseTeachingClassHomeworkStudentScoreRepository.deleteById(id);
    }

    /**
     * 根据t_course_teaching_class_id删除
     */
    @Override
    public void deleteByScoreInfoId(HttpServletRequest request, String t_course_teaching_class_homework_score_info_id) {
        courseTeachingClassHomeworkStudentScoreRepository.deleteByScoreInfoId(t_course_teaching_class_homework_score_info_id);
    }

    @Override
    public String add(String t_course_teaching_class_homework_score_info_id, String t_student_id, String t_teacher_id,
                      String score, String description, String note) {
        if (courseTeachingClassHomeworkStudentScoreRepository.isStudentScoreExist(t_course_teaching_class_homework_score_info_id, t_student_id)) {
            CourseTeachingClassHomeworkStudentScore s = courseTeachingClassHomeworkStudentScoreRepository
                    .getByCourseTeachingClassHomeworkScoreInfoIdAndStudentId(
                            t_course_teaching_class_homework_score_info_id, t_student_id);
            if (s == null) return null;
            update(s.getId(), t_course_teaching_class_homework_score_info_id, t_student_id, t_teacher_id, score,
                    description, note);
            return s.getId();

        } else
            return courseTeachingClassHomeworkStudentScoreRepository.add(t_course_teaching_class_homework_score_info_id, t_student_id, t_teacher_id,
                    score, description, new Date(), note);
    }

    @Override
    public void deleteByStudentID(HttpServletRequest request, String t_student_id) {
        courseTeachingClassHomeworkStudentScoreRepository.deleteByStudentId(t_student_id);

    }

    /**
     * update
     */
    @Override
    public void update(String id, String t_course_teaching_class_homework_score_info_id, String t_student_id,
                       String t_teacher_id, String score, String description, String note) {

        courseTeachingClassHomeworkStudentScoreRepository.update(id, t_course_teaching_class_homework_score_info_id, t_student_id, t_teacher_id, score,
                description, new Date(), note);
    }

    @Override
    public void updateScore(String id, String score) {
        courseTeachingClassHomeworkStudentScoreRepository.updateScore(id, score);
    }

    @Override
    public void updateDescription(String id, String description) {
        courseTeachingClassHomeworkStudentScoreRepository.updateDescription(id, description, new Date());
    }

    @Override
    public void update(String id, String score, String description) {
        courseTeachingClassHomeworkStudentScoreRepository.update(id, score, description, new Date());
    }

    @Override
    public CourseTeachingClassHomeworkStudentScoreViewData getViewById(String id) {

        CourseTeachingClassHomeworkStudentScore score = courseTeachingClassHomeworkStudentScoreRepository.getByID(id);
        if (score == null) return null;

        CourseTeachingClassHomeworkStudentScoreViewData data = new CourseTeachingClassHomeworkStudentScoreViewData();

        data.setScore(score);

        data.setStudentView(studentService.getStudentViewByStudentId(score.getStudentId()));

        data.setScoreInfo(courseTeachingClassHomeworkScoreInfoService
                .getViewById(score.getCourseTeachingClassHomeworkScoreInfoId()));

        return data;

    }

    @Override
    public CourseTeachingClassHomeworkStudentScoresViewData getViewDataByCourseTeachingClassIdAndStudentId(
            String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id, String t_student_id, Integer homework_show_order) {

        CourseTeachingClassHomeworkStudentScoresViewData data = new CourseTeachingClassHomeworkStudentScoresViewData();

        data.setStudentView(studentService.getStudentViewByStudentId(t_student_id));

        List<CourseTeachingClassHomeworkScoreInfoViewData> lstCourseTeachingClassHomeworkScoreInfoViewData = courseTeachingClassHomeworkScoreInfoService
                .getAllByOrder(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, homework_show_order);

        if (lstCourseTeachingClassHomeworkScoreInfoViewData != null) {
            List<CourseTeachingClassHomeworkStudentScoreSimpleView> temp = new ArrayList<>();
            for (CourseTeachingClassHomeworkScoreInfoViewData info : lstCourseTeachingClassHomeworkScoreInfoViewData) {
                CourseTeachingClassHomeworkStudentScoreSimpleView v = new CourseTeachingClassHomeworkStudentScoreSimpleView();

                v.setScoreInfo(info);
                v.setScore(courseTeachingClassHomeworkStudentScoreRepository.getByCourseTeachingClassHomeworkScoreInfoIdAndStudentId(
                        info.getScoreInfo().getId(), t_student_id));
                temp.add(v);

            }
            data.setLstScore(temp);
        }

        return data;

    }

    @Override
    public CourseTeachingClassHomeworkStudentScoresViewData getViewDataByCourseTeachingClassIdAndStudentId(
            String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id,
            StudentViewData student, Integer homework_show_order) {

        if (student == null) return null;

        CourseTeachingClassHomeworkStudentScoresViewData data = new CourseTeachingClassHomeworkStudentScoresViewData();

        data.setStudentView(student);

        List<CourseTeachingClassHomeworkScoreInfoViewData> lstCourseTeachingClassHomeworkScoreInfoViewData =

                courseTeachingClassHomeworkScoreInfoService
                        .getAllByOrder(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, homework_show_order);


        if (lstCourseTeachingClassHomeworkScoreInfoViewData != null) {
            List<CourseTeachingClassHomeworkStudentScoreSimpleView> temp = new ArrayList<>();
            for (CourseTeachingClassHomeworkScoreInfoViewData info : lstCourseTeachingClassHomeworkScoreInfoViewData) {
                CourseTeachingClassHomeworkStudentScoreSimpleView v = new CourseTeachingClassHomeworkStudentScoreSimpleView();

                v.setScoreInfo(info);
                v.setScore(courseTeachingClassHomeworkStudentScoreRepository.getByCourseTeachingClassHomeworkScoreInfoIdAndStudentId(
                        info.getScoreInfo().getId(), student.getStudent().getId()));
                temp.add(v);

            }
            data.setLstScore(temp);
        }

        return data;

    }

    @Override
    public Page<CourseTeachingClassHomeworkStudentScoresViewData> getStudentScoresPage(
            String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id,
            Integer homework_show_order, int pageNo, int pageSize) {
        // 学生信息
        Page<StudentViewData> pagedStudentViewData = studentService
                .getPageByCourseTeachingClassId(t_course_teaching_class_id, pageNo, pageSize);

        if (pagedStudentViewData == null) return null;

        List<CourseTeachingClassHomeworkStudentScoresViewData> data = new ArrayList<>();
        for (StudentViewData stu : pagedStudentViewData.getResult()) {
            CourseTeachingClassHomeworkStudentScoresViewData d = getViewDataByCourseTeachingClassIdAndStudentId(
                    t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, stu, homework_show_order);
            data.add(d);
        }

        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        return new Page<>(startIndex,
                pagedStudentViewData.getTotalCount(), pageSize, data);

    }

    private List<CourseTeachingClassHomeworkStudentScoreViewData> PageQuery(
            String t_course_teaching_class_homework_score_info_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        List<CourseTeachingClassHomeworkStudentScore> listtemp = courseTeachingClassHomeworkStudentScoreRepository
                .PageQuery(t_course_teaching_class_homework_score_info_id, PageBegin, PageSize);
        if (listtemp == null) return null;

        List<CourseTeachingClassHomeworkStudentScoreViewData> list = new ArrayList<>();
        for (CourseTeachingClassHomeworkStudentScore score : listtemp) {
            CourseTeachingClassHomeworkStudentScoreViewData s = getViewById(score.getId());
            list.add(s);
        }

        return list;
    }

    @Override
    public Page<CourseTeachingClassHomeworkStudentScoreViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize) {
        long totalCount = courseTeachingClassHomeworkStudentScoreRepository.getCount(t_course_teaching_class_homework_baseinfo_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkStudentScoreViewData> data = PageQuery(
                t_course_teaching_class_homework_baseinfo_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

}
