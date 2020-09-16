package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.*;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkReplyRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfoRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassStudentRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkStatisticsService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkSubmitService;
import com.haobaoshui.course.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 课程相关信息统计服务
 */

@Service
public class CourseTeachingClassHomeworkStatisticsServiceImpl implements CourseTeachingClassHomeworkStatisticsService {

    private final CourseTeachingClassStudentRepository courseTeachingClassStudentRepository;
    private final CourseTeachingClassHomeworkSubmitBaseinfoRepository courseTeachingClassHomeworkSubmitBaseinfoRepository;
    private final CourseTeachingClassHomeworkService courseTeachingClassHomeworkService;
    private final CourseTeachingClassRepository courseTeachingClassRepository;
    private final CourseTeachingClassHomeworkReplyRepository courseTeachingClassHomeworkReplyRepository;
    private final StudentService studentService;
    private CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService;


    @Autowired
    public CourseTeachingClassHomeworkStatisticsServiceImpl(CourseTeachingClassStudentRepository courseTeachingClassStudentRepository,
                                                            CourseTeachingClassHomeworkSubmitBaseinfoRepository courseTeachingClassHomeworkSubmitBaseinfoRepository,
                                                            CourseTeachingClassHomeworkService courseTeachingClassHomeworkService,
                                                            CourseTeachingClassRepository courseTeachingClassRepository,
                                                            CourseTeachingClassHomeworkReplyRepository courseTeachingClassHomeworkReplyRepository, StudentService studentService
    ) {
        this.courseTeachingClassStudentRepository = courseTeachingClassStudentRepository;
        this.courseTeachingClassHomeworkSubmitBaseinfoRepository = courseTeachingClassHomeworkSubmitBaseinfoRepository;
        this.courseTeachingClassHomeworkService = courseTeachingClassHomeworkService;
        this.courseTeachingClassRepository = courseTeachingClassRepository;
        this.courseTeachingClassHomeworkReplyRepository = courseTeachingClassHomeworkReplyRepository;
        this.studentService = studentService;

    }

    @Autowired
    public void setCourseTeachingClassHomeworkSubmitService(CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService) {
        this.courseTeachingClassHomeworkSubmitService = courseTeachingClassHomeworkSubmitService;
    }


    private List<CourseTeachingClassHomeworkStatisticsViewData> PageQuery(CourseTeachingClassHomeworkBaseinfo baseinfo,
                                                                          CourseTeachingClass ctc, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;

        List<CourseTeachingClassHomeworkStatisticsViewData> list = new ArrayList<>();

        List<CourseTeachingClassStudent> liststudent = courseTeachingClassStudentRepository
                .getTeachingClassStudentByTeachingClassId(ctc.getId());

        for (CourseTeachingClassStudent stu : liststudent) {
            CourseTeachingClassHomeworkStatisticsViewData data = new CourseTeachingClassHomeworkStatisticsViewData();

            // 作业信息
            CourseTeachingClassHomeworkBaseinfoViewData homeworkbaseinfo = courseTeachingClassHomeworkService
                    .getCourseTeachingClassHomeworkBaseinfoViewDataByID(baseinfo.getId());
            data.setHomeworkbaseinfo(homeworkbaseinfo);

            // 作业提交情况
            List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> homeworksubmit = courseTeachingClassHomeworkSubmitService
                    .getViewDataByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(baseinfo.getId(),
                            stu.getStudentId());
            data.setHomeworksubmit(homeworksubmit);

            // 作业批复情况
            List<CourseTeachingClassHomeworkReply> homeworkreply = new ArrayList<>();
            for (CourseTeachingClassHomeworkSubmitBaseinfoViewData sub : homeworksubmit) {

                List<CourseTeachingClassHomeworkReply> r = courseTeachingClassHomeworkReplyRepository
                        .getByCourseTeachingClassHomeworkSubmitBaseInfoID(sub.getHomeworksubmitbaseinfo().getId());

                if (r != null) homeworkreply.addAll(r);
            }
            data.setHomeworkreply(homeworkreply);

            // 学生基本信息
            StudentViewData student = studentService.getStudentViewByStudentId(stu.getStudentId());
            data.setStudent(student);

            list.add(data);
        }

        return list;
    }

    /**
     * 作业的统计情况
     */
    @Override
    public Page<CourseTeachingClassHomeworkStatisticsViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize) {

        CourseTeachingClassHomeworkBaseinfo baseinfo = courseTeachingClassHomeworkService
                .getByID(t_course_teaching_class_homework_baseinfo_id);

        if (baseinfo == null) return null;

        List<CourseTeachingClassHomeworkSubmitBaseinfo> submitlist = courseTeachingClassHomeworkSubmitBaseinfoRepository
                .getByCourseTeachingClassHomeworkBaseinfoID(t_course_teaching_class_homework_baseinfo_id);
        if (submitlist == null) return null;

        CourseTeachingClass ctc = courseTeachingClassRepository
                .getCourseTeachingClassById(baseinfo.getCourseTeachingClassId());
        int totalCount = courseTeachingClassStudentRepository.getStudentCountByCourseTeachingClassId(ctc.getId());

        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkStatisticsViewData> data = PageQuery(baseinfo, ctc, pageNo - 1, totalCount);

        return new Page<>(startIndex, totalCount, totalCount, data);

    }

}
