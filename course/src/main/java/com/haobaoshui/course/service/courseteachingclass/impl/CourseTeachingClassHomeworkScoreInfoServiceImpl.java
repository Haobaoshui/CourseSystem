package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkBaseinfo;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkScoreInfo;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkScoreInfoViewData;
import com.haobaoshui.course.model.database.ScoreMarkingType;
import com.haobaoshui.course.model.database.ScoreShowType;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkScoreInfoRepository;
import com.haobaoshui.course.repository.database.ScoreMarkingTypeRepository;
import com.haobaoshui.course.repository.database.ScoreShowTypeRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkScoreInfoService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkStudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
public class CourseTeachingClassHomeworkScoreInfoServiceImpl implements CourseTeachingClassHomeworkScoreInfoService {


    private final CourseTeachingClassHomeworkScoreInfoRepository courseTeachingClassHomeworkScoreInfoRepository;
    private final ScoreMarkingTypeRepository scoreMarkingTypeRepository;
    private final ScoreShowTypeRepository scoreShowTypeRepository;
    private CourseTeachingClassHomeworkStudentScoreService courseTeachingClassHomeworkStudentScoreService;
    private CourseTeachingClassHomeworkService courseTeachingClassHomeworkService;

    @Autowired
    public CourseTeachingClassHomeworkScoreInfoServiceImpl(CourseTeachingClassHomeworkScoreInfoRepository courseTeachingClassHomeworkScoreInfoRepository,
                                                           ScoreMarkingTypeRepository scoreMarkingTypeRepository,
                                                           ScoreShowTypeRepository scoreShowTypeRepository) {
        this.courseTeachingClassHomeworkScoreInfoRepository = courseTeachingClassHomeworkScoreInfoRepository;
        this.scoreMarkingTypeRepository = scoreMarkingTypeRepository;
        this.scoreShowTypeRepository = scoreShowTypeRepository;
    }

    @Autowired
    public void setCourseTeachingClassHomeworkStudentScoreService(CourseTeachingClassHomeworkStudentScoreService courseTeachingClassHomeworkStudentScoreService) {
        this.courseTeachingClassHomeworkStudentScoreService = courseTeachingClassHomeworkStudentScoreService;
    }

    @Autowired
    public void setCourseTeachingClassHomeworkService(CourseTeachingClassHomeworkService courseTeachingClassHomeworkService) {
        this.courseTeachingClassHomeworkService = courseTeachingClassHomeworkService;
    }

    @Override
    public CourseTeachingClassHomeworkScoreInfo getByID(String id) {
        return courseTeachingClassHomeworkScoreInfoRepository.getByID(id);
    }

    /**
     * 根据id删除
     */
    @Override
    public void deleteById(HttpServletRequest request, String id) {
        CourseTeachingClassHomeworkScoreInfo info = getByID(id);
        if (info == null) return;

        courseTeachingClassHomeworkStudentScoreService.deleteByScoreInfoId(request,
                info.getCourseTeachingClassHomeworkBaseinfoId());
        courseTeachingClassHomeworkScoreInfoRepository.deleteById(id);
    }


    /**
     * 根据t_course_teaching_class_id删除
     */
    @Override
    public void deleteByCourseTeachingClassHomeworkInfoId(HttpServletRequest request,
                                                          String t_course_teaching_class_homework_baseinfo_id) {
        CourseTeachingClassHomeworkScoreInfo info = courseTeachingClassHomeworkScoreInfoRepository
                .getByCourseTeachingClassHomeworkBaseinfoID(t_course_teaching_class_homework_baseinfo_id);
        if (info == null) return;
        courseTeachingClassHomeworkScoreInfoRepository.deleteByCourseTeachingClassHomeworkInfoId(t_course_teaching_class_homework_baseinfo_id);
    }

    @Override
    public void deleteByScoreMarkingTypeId(String t_score_marking_type_id) {
        List<CourseTeachingClassHomeworkScoreInfo> list = courseTeachingClassHomeworkScoreInfoRepository.getByScoreMarkingTypeID(t_score_marking_type_id);
        if (list == null || list.isEmpty()) return;
        for (CourseTeachingClassHomeworkScoreInfo info : list)
            courseTeachingClassHomeworkScoreInfoRepository.deleteById(info.getId());
        courseTeachingClassHomeworkScoreInfoRepository.deleteByScoreMarkingTypeId(t_score_marking_type_id);
    }

    @Override
    public void deleteByScoreShowTypeId(String t_score_show_type_id) {
        List<CourseTeachingClassHomeworkScoreInfo> list = courseTeachingClassHomeworkScoreInfoRepository.getByScoreShowTypeID(t_score_show_type_id);
        if (list == null || list.isEmpty()) return;
        for (CourseTeachingClassHomeworkScoreInfo info : list)
            courseTeachingClassHomeworkScoreInfoRepository.deleteById(info.getId());
        courseTeachingClassHomeworkScoreInfoRepository.deleteByScoreShowTypeId(t_score_show_type_id);
    }

    @Override
    public String add(String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
                      String t_score_show_type_id) {
        return courseTeachingClassHomeworkScoreInfoRepository.add(t_course_teaching_class_homework_baseinfo_id, t_score_marking_type_id,
                t_score_show_type_id);
    }

    /**
     * update
     */
    @Override
    public void update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
                       String t_score_show_type_id) {

        courseTeachingClassHomeworkScoreInfoRepository.update(id, t_course_teaching_class_homework_baseinfo_id, t_score_marking_type_id,
                t_score_show_type_id);
    }

    @Override
    public void updateByCourseTeachingClassIdAndHomeworkTypeId(String t_course_teaching_class_id,
                                                               String t_course_teaching_class_homeworktype_id, String t_score_marking_type_id,
                                                               String t_score_show_type_id) {
        courseTeachingClassHomeworkScoreInfoRepository.updateByCourseTeachingClassIdAndHomeworkTypeId(t_course_teaching_class_id,
                t_course_teaching_class_homeworktype_id, t_score_marking_type_id, t_score_show_type_id);
    }

    @Override
    public CourseTeachingClassHomeworkScoreInfoViewData getViewById(
            String t_course_teaching_class_homework_score_info_id) {
        CourseTeachingClassHomeworkScoreInfoViewData data = new CourseTeachingClassHomeworkScoreInfoViewData();

        CourseTeachingClassHomeworkScoreInfo scoreInfo = getByID(t_course_teaching_class_homework_score_info_id);
        data.setScoreInfo(scoreInfo);

        CourseTeachingClassHomeworkBaseinfo homeworkBaseinfo = courseTeachingClassHomeworkService
                .getByID(scoreInfo.getCourseTeachingClassHomeworkBaseinfoId());
        data.setHomeworkBaseinfo(homeworkBaseinfo);

        ScoreMarkingType markingType = scoreMarkingTypeRepository.getByID(scoreInfo.getScoreMarkingTypeId());
        data.setScoreMarkingType(markingType);

        ScoreShowType showType = scoreShowTypeRepository.getByID(scoreInfo.getScoreShowTypeId());
        data.setScoreShowType(showType);

        return data;
    }

    @Override
    public CourseTeachingClassHomeworkScoreInfoViewData getViewByHomeworkBaseinfoId(
            String t_course_teaching_class_homework_baseinfo_id) {
        CourseTeachingClassHomeworkScoreInfo scoreInfo = courseTeachingClassHomeworkScoreInfoRepository
                .getByCourseTeachingClassHomeworkBaseinfoID(t_course_teaching_class_homework_baseinfo_id);
        if (scoreInfo == null) return null;
        return getViewById(scoreInfo.getId());
    }

    private List<CourseTeachingClassHomeworkScoreInfoViewData> PageQuery(String t_course_teaching_class_id,
                                                                         String t_course_teaching_class_homeworktype_id, int PageBegin, int PageSize) {

        if (PageBegin < 0) PageBegin = 0;

        List<CourseTeachingClassHomeworkScoreInfo> list = courseTeachingClassHomeworkScoreInfoRepository.PageQuery(t_course_teaching_class_id,
                t_course_teaching_class_homeworktype_id, PageBegin, PageSize);

        if (list == null) return null;

        List<CourseTeachingClassHomeworkScoreInfoViewData> listResult = new ArrayList<>();

        for (CourseTeachingClassHomeworkScoreInfo info : list) {
            CourseTeachingClassHomeworkScoreInfoViewData data = getViewById(info.getId());
            listResult.add(data);
        }
        return listResult;
    }

    @Override
    public Page<CourseTeachingClassHomeworkScoreInfoViewData> getPage(String t_course_teaching_class_id,
                                                                      String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize) {
        long totalCount = courseTeachingClassHomeworkScoreInfoRepository.getCount(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkScoreInfoViewData> data = PageQuery(t_course_teaching_class_id,
                t_course_teaching_class_homeworktype_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }


    /**
     *
     */
    @Override
    public List<CourseTeachingClassHomeworkScoreInfoViewData> getAllByOrder(String t_course_teaching_class_id,
                                                                            String t_course_teaching_class_homeworktype_id, Integer homework_show_order) {

        List<CourseTeachingClassHomeworkScoreInfo> list = null;
        if (homework_show_order == 0)
            list = courseTeachingClassHomeworkScoreInfoRepository.getAllOrderByDesc(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id);
        else
            list = courseTeachingClassHomeworkScoreInfoRepository.getAllOrderByAsc(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id);

        List<CourseTeachingClassHomeworkScoreInfoViewData> listResult = new ArrayList<>();

        for (CourseTeachingClassHomeworkScoreInfo info : list) {
            CourseTeachingClassHomeworkScoreInfoViewData data = getViewById(info.getId());
            listResult.add(data);
        }
        return listResult;

    }

    @Override
    public void addHomeworkBaseInfo(String t_course_teaching_class_homework_baseinfo_id) {

        if (t_course_teaching_class_homework_baseinfo_id == null) return;

        if (null == courseTeachingClassHomeworkScoreInfoRepository
                .getByCourseTeachingClassHomeworkBaseinfoID(t_course_teaching_class_homework_baseinfo_id))
            courseTeachingClassHomeworkScoreInfoRepository.add(t_course_teaching_class_homework_baseinfo_id);

    }

    @Override
    public void addHomeworkBaseInfo(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id) {
        List<CourseTeachingClassHomeworkBaseinfo> list = courseTeachingClassHomeworkService.getByCourseTeachingClassIDAndHomeworkTypeId(
                t_course_teaching_class_id, t_course_teaching_class_homeworktype_id);
        if (list == null) return;

        for (CourseTeachingClassHomeworkBaseinfo info : list) addHomeworkBaseInfo(info.getId());

    }
}
