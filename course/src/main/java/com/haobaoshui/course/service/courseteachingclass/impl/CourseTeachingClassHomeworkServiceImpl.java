package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.*;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.model.user.TeacherViewData;
import com.haobaoshui.course.repository.courseteachingclass.*;
import com.haobaoshui.course.service.courseteachingclass.*;
import com.haobaoshui.course.service.user.TeacherService;
import com.haobaoshui.course.utility.DateTimeSql;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CourseTeachingClassHomeworkServiceImpl implements CourseTeachingClassHomeworkService {


    private final CourseTeachingClassHomeworkTypeRepository courseTeachingClassHomeworkTypeRepository;
    private final CourseTeachingClassHomeworkBaseinfoRepository courseTeachingClassHomeworkBaseinfoRepository;
    private final CourseTeachingClassHomeworkFileRepository courseTeachingClassHomeworkFileRepository;
    private final CourseTeachingClassHomeworkSubmitBaseinfoRepository courseTeachingClassHomeworkSubmitBaseinfoRepository;
    private final CourseTeachingClassHomeworkDelayedRepository courseTeachingClassHomeworkDelayedRepository;
    @Autowired
    CourseTeachingClassService courseTeachingClassService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService;
    @Autowired
    CourseTeachingClassStudentGroupService courseTeachingClassStudentGroupService;
    @Autowired
    CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService;

    @Autowired
    public CourseTeachingClassHomeworkServiceImpl(
            CourseTeachingClassHomeworkTypeRepository courseTeachingClassHomeworkTypeRepository,
            CourseTeachingClassHomeworkBaseinfoRepository courseTeachingClassHomeworkBaseinfoRepository,
            CourseTeachingClassHomeworkFileRepository courseTeachingClassHomeworkFileRepository,
            CourseTeachingClassHomeworkSubmitBaseinfoRepository courseTeachingClassHomeworkSubmitBaseinfoRepository,
            CourseTeachingClassHomeworkDelayedRepository courseTeachingClassHomeworkDelayedRepository) {


        this.courseTeachingClassHomeworkTypeRepository = courseTeachingClassHomeworkTypeRepository;
        this.courseTeachingClassHomeworkBaseinfoRepository = courseTeachingClassHomeworkBaseinfoRepository;
        this.courseTeachingClassHomeworkFileRepository = courseTeachingClassHomeworkFileRepository;
        this.courseTeachingClassHomeworkSubmitBaseinfoRepository = courseTeachingClassHomeworkSubmitBaseinfoRepository;
        this.courseTeachingClassHomeworkDelayedRepository = courseTeachingClassHomeworkDelayedRepository;


    }

    @Autowired
    public void setCourseTeachingClassHomeworkScoreInfoService(CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService) {
        this.courseTeachingClassHomeworkScoreInfoService = courseTeachingClassHomeworkScoreInfoService;
    }

    @Autowired
    public void setCourseTeachingClassHomeworkSubmitService(CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService) {
        this.courseTeachingClassHomeworkSubmitService = courseTeachingClassHomeworkSubmitService;
    }

    @Override
    public CourseTeachingClassHomeworkFile getFileByID(String id) {
        return courseTeachingClassHomeworkFileRepository.getByID(id);
    }

    private void addFiles(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id,
                          MultipartFile[] files) {

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String filename = file.getOriginalFilename();

            String prefix = filename.substring(filename.lastIndexOf(".") + 1);

            ServletContext sc = request.getSession().getServletContext();
            String dir = sc.getRealPath(RealPathConst.RealPath_HomeworkFile); // 设定文件保存的目录

            prefix = prefix.toLowerCase();

            String idfilename = GUID.getGUID();

            String localfilename = dir + RealPathConst.RealPath_PathSeparator + idfilename + "." + prefix;
            String hreffilename = idfilename + "." + prefix;

            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                courseTeachingClassHomeworkFileRepository.add(t_course_teaching_class_homework_baseinfo_id, filename, hreffilename);

            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

    // 增加
    @Override
    public void add(HttpServletRequest request, String t_course_teaching_class_id,
                    String t_course_teaching_class_homeworktype_id, String t_teacher_id, Integer flag, String file_requirement,
                    String title, String content, String enddate, MultipartFile[] files) {

        String t_course_teaching_class_homework_baseinfo_id = courseTeachingClassHomeworkBaseinfoRepository.add(t_course_teaching_class_id,
                t_teacher_id, t_course_teaching_class_homeworktype_id, flag, file_requirement, title, content,
                new Date(), DateTimeSql.GetDateTimeNotIncludingSecond(enddate));

        addFiles(request, t_course_teaching_class_homework_baseinfo_id, files);

        //作业成绩管理
        courseTeachingClassHomeworkScoreInfoService.addHomeworkBaseInfo(t_course_teaching_class_homework_baseinfo_id);

    }

    /**
     * 增加迟交作业
     */
    @Override
    public void addDelayed(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id,
                           String t_teacher_id, String enddate) {

        courseTeachingClassHomeworkDelayedRepository.add(t_course_teaching_class_homework_baseinfo_id, t_teacher_id,
                new Date(), DateTimeSql.GetDateTimeNotIncludingSecond(enddate));

    }

    /**
     * 删除id迟交作业
     */
    @Override
    public void deleteDelayedByID(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id) {
        courseTeachingClassHomeworkDelayedRepository.deleteById(t_course_teaching_class_homework_delayed_id);
    }

    /**
     * 根据t_course_teaching_class_homework_baseinfo_id删除迟交作业
     */
    @Override
    public void deleteDelayedByCourseTeachingClassHomeworkBaseinfoId(HttpServletRequest request,
                                                                     String t_course_teaching_class_homework_baseinfo_id) {
        courseTeachingClassHomeworkDelayedRepository
                .deleteByCourseTeachingClassHomeworkBaseInfoId(t_course_teaching_class_homework_baseinfo_id);
    }

    @Override
    public void deleteDelayedByTeacherId(HttpServletRequest request, String t_teacher_id) {
        courseTeachingClassHomeworkDelayedRepository.deleteByTeacherId(t_teacher_id);
    }

    @Override
    public void updateDelayed(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id,
                              String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, String enddate) {

        courseTeachingClassHomeworkDelayedRepository.update(t_course_teaching_class_homework_delayed_id,
                t_course_teaching_class_homework_baseinfo_id, t_teacher_id, new Date(),
                DateTimeSql.GetDateTimeNotIncludingSecond(enddate));

    }

    @Override
    public void updateDelayed(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id,
                              String t_teacher_id, String enddate) {

        courseTeachingClassHomeworkDelayedRepository.update(t_course_teaching_class_homework_delayed_id, t_teacher_id,
                new Date(), DateTimeSql.GetDateTimeNotIncludingSecond(enddate));

    }

    @Override
    public void updateDelayed(HttpServletRequest request, String t_course_teaching_class_homework_delayed_id,
                              String enddate) {

        courseTeachingClassHomeworkDelayedRepository.update(t_course_teaching_class_homework_delayed_id,
                DateTimeSql.GetDateTimeNotIncludingSecond(enddate));

    }

    // 删除
    @Override
    public void deleteByID(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id) {

        // 删除迟交作业
        deleteDelayedByCourseTeachingClassHomeworkBaseinfoId(request, t_course_teaching_class_homework_baseinfo_id);

        // 删除提交作业
        courseTeachingClassHomeworkSubmitService.deleteByCourseTeachingClassHomeworkBaseinfoID(request,
                t_course_teaching_class_homework_baseinfo_id);

        // 删除基本信息及其附件文件
        deleteBaseInfoByID(request, t_course_teaching_class_homework_baseinfo_id);
    }

    @Override
    public void deleteByTeacherId(HttpServletRequest request, String t_teacher_id) {
        List<CourseTeachingClassHomeworkBaseinfo> list = courseTeachingClassHomeworkBaseinfoRepository.getByTeacherID(t_teacher_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkBaseinfo h : list) deleteByID(request, h.getId());
    }

    @Override
    public void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id) {
        List<CourseTeachingClassHomeworkBaseinfo> list = courseTeachingClassHomeworkBaseinfoRepository
                .getByCourseTeachingClassID(t_course_teaching_class_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkBaseinfo h : list) deleteByID(request, h.getId());
    }

    @Override
    public void deleteByCourseTeachingClassHomeworkTypeId(HttpServletRequest request,
                                                          String t_course_teaching_class_homework_type_id) {
        List<CourseTeachingClassHomeworkBaseinfo> list = courseTeachingClassHomeworkBaseinfoRepository
                .getByHomeWorkTypeID(t_course_teaching_class_homework_type_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkBaseinfo h : list) deleteByID(request, h.getId());
    }

    // 删除基本信息及其附件文件
    private void deleteBaseInfoByID(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id) {
        CourseTeachingClassHomeworkBaseinfo plan = getByID(t_course_teaching_class_homework_baseinfo_id);
        if (plan == null) return;

        // 1.删除文件
        deleteFilesByHomeworkBaseInfoId(request, t_course_teaching_class_homework_baseinfo_id);

        //2.删除成绩
        courseTeachingClassHomeworkScoreInfoService.deleteByCourseTeachingClassHomeworkInfoId(request, t_course_teaching_class_homework_baseinfo_id);

        // 3.删除基本信息
        courseTeachingClassHomeworkBaseinfoRepository.deleteById(t_course_teaching_class_homework_baseinfo_id);
    }

    // 删除文件
    private void deleteFilesByHomeworkBaseInfoId(HttpServletRequest request,
                                                 String t_course_teaching_class_homework_baseinfo_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_HomeworkFile); // 设定文件保存的目录

        List<CourseTeachingClassHomeworkFile> list = courseTeachingClassHomeworkFileRepository
                .getByCourseTeachingClassHomeworkBaseInfoID(t_course_teaching_class_homework_baseinfo_id);
        if (list != null) for (CourseTeachingClassHomeworkFile homeworkfile : list) {
            String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

            File localfile = new File(path);

            if (localfile.exists()) localfile.delete();

            courseTeachingClassHomeworkFileRepository.deleteById(homeworkfile.getId());

        }
    }

    @Override
    public void Update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id,
                       String t_teacher_id, Integer flag, String file_requirement, String title, String content, String enddate,
                       MultipartFile[] files) {

        // 1.删除文件
        deleteFilesByHomeworkBaseInfoId(request, t_course_teaching_class_homework_baseinfo_id);

        // 2.更改基本信息
        courseTeachingClassHomeworkBaseinfoRepository.update(t_course_teaching_class_homework_baseinfo_id, t_teacher_id, flag, file_requirement,
                title, content, DateTimeSql.GetDateTimeNotIncludingSecond(enddate));

        // 3.增加文件
        addFiles(request, t_course_teaching_class_homework_baseinfo_id, files);
    }

    @Override
    public void update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id,
                       String t_teacher_id, String title, String content, String enddate) {

        // 2.更改基本信息
        courseTeachingClassHomeworkBaseinfoRepository.update(t_course_teaching_class_homework_baseinfo_id, t_teacher_id, title, content,
                DateTimeSql.GetDateTimeNotIncludingSecond(enddate));

    }

    @Override
    public CourseTeachingClassHomeworkBaseinfo getByID(String id) {
        return courseTeachingClassHomeworkBaseinfoRepository.getByID(id);
    }

    @Override
    public List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassID(String t_course_teaching_class_id) {
        return courseTeachingClassHomeworkBaseinfoRepository.getByCourseTeachingClassID(t_course_teaching_class_id);
    }

    @Override
    public List<CourseTeachingClassHomeworkBaseinfo> getByCourseTeachingClassIDAndHomeworkTypeId(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id) {
        return courseTeachingClassHomeworkBaseinfoRepository.getByCourseTeachingClassIDAndHomeworkTypeId(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id);
    }

    @Override
    public Page<CourseTeachingClassHomeworkBaseinfoStudentViewData> getPage(String t_course_teaching_class_id,
                                                                            String t_course_teaching_class_homeworktype_id, String t_student_id, int pageNo, int pageSize) {

        Page<CourseTeachingClassHomeworkBaseinfoViewData> page = getPage(t_course_teaching_class_id,
                t_course_teaching_class_homeworktype_id, pageNo, pageSize);
        List<CourseTeachingClassHomeworkBaseinfoViewData> listtemp = page.getResult();

        Page<CourseTeachingClassHomeworkBaseinfoStudentViewData> result = null;
        if (listtemp != null) {
            List<CourseTeachingClassHomeworkBaseinfoStudentViewData> list = new ArrayList<>();
            for (CourseTeachingClassHomeworkBaseinfoViewData d : listtemp) {
                CourseTeachingClassHomeworkBaseinfoStudentViewData s = getCourseTeachingClassHomeworkBaseinfoStudentViewData(
                        d, t_student_id);
                if (s != null) list.add(s);
            }

            result = new Page<>(page.getStart(), page.getTotalCount(),
                    page.getPageSize(), list);
        }

        return result;
    }

    @Override
    public CourseTeachingClassHomeworkBaseinfoStudentViewData getCourseTeachingClassHomeworkBaseinfoStudentViewData(
            CourseTeachingClassHomeworkBaseinfoViewData data, String t_student_id) {

        if (data == null || t_student_id == null) return null;

        String t_course_teaching_class_id = data.getCourseteachingclass().getId();

        CourseTeachingClassHomeworkBaseinfoStudentViewData s = new CourseTeachingClassHomeworkBaseinfoStudentViewData(
                data);

        String t_course_teaching_class_homework_baseinfo_id = data.getHomeworkbaseinfo().getId();
        s.setStudentSubmitted(isStudentSubmitHomework(data, t_course_teaching_class_id,
                t_course_teaching_class_homework_baseinfo_id, t_student_id));
        return s;

    }

    /**
     * 学生是否提交了作业？如果是个人作业，则只检查个人，否则检查小组其它人是否提交
     */
    private boolean isStudentSubmitHomework(CourseTeachingClassHomeworkBaseinfoViewData data,
                                            String t_course_teaching_class_id, String t_course_teaching_class_homework_baseinfo_id,
                                            String t_student_id) {

        if (data == null) return false;

        if (data.getHomeworkbaseinfo().isFlagGroup()) {
            CourseTeachingClassStudentGroupViewData g = courseTeachingClassStudentGroupService
                    .getViewDataByStudentId(t_course_teaching_class_id, t_student_id);
            if (g == null) return false;
            for (StudentViewData s : g.getStudentViewDatas())
                if (courseTeachingClassHomeworkSubmitBaseinfoRepository.getCount(t_course_teaching_class_homework_baseinfo_id, s.getStudent().getId()) > 0)
                    return true;
            return false;

        } else
            return courseTeachingClassHomeworkSubmitBaseinfoRepository.getCount(t_course_teaching_class_homework_baseinfo_id, t_student_id) > 0;
    }

    @Override
    public void update(String id, String t_course_teaching_class_id, String t_teacher_id, String t_item_kinds_id,
                       Integer flag, String file_requirement, String title, String content, Date pubdate, Date enddate,
                       String filename, String filepath) {
        courseTeachingClassHomeworkBaseinfoRepository.update(id, t_course_teaching_class_id, t_teacher_id, t_item_kinds_id, flag,
                file_requirement, title, content, pubdate, enddate);
        // homeworkfileDao.update(t_item_kinds_id,
        // t_course_teaching_class_homework_baseinfo_id, filename, filepath);
    }

    @Override
    public CourseTeachingClassHomeworkBaseinfoViewData getCourseTeachingClassHomeworkBaseinfoViewDataByID(String id) {
        CourseTeachingClassHomeworkBaseinfoViewData data = new CourseTeachingClassHomeworkBaseinfoViewData();

        CourseTeachingClassHomeworkBaseinfo homeworkbaseinfo = getByID(id);

        data.setHomeworkbaseinfo(homeworkbaseinfo);

        CourseTeachingClass courseteachingclass = courseTeachingClassService
                .getById(homeworkbaseinfo.getCourseTeachingClassId());
        data.setCourseteachingclass(courseteachingclass);

        TeacherViewData teacherviewdata = teacherService
                .getTeacherViewById(homeworkbaseinfo.getTeacherId());
        data.setTeacher(teacherviewdata);

        CourseTeachingClassHomeworkType homeworkType = courseTeachingClassHomeworkTypeRepository
                .getByID(homeworkbaseinfo.getCourseTeachingClassHomeworkTypeId());
        data.setHomeworkType(homeworkType);

        List<CourseTeachingClassHomeworkFile> homeworkFileList = courseTeachingClassHomeworkFileRepository
                .getByCourseTeachingClassHomeworkBaseInfoID(homeworkbaseinfo.getId());
        data.setHomeworkFileList(homeworkFileList);

        List<CourseTeachingClassHomeworkDelayed> homeworkDelayedList = courseTeachingClassHomeworkDelayedRepository
                .getByCourseTeachingClassHomeworkBaseInfoID(id);
        data.setHomeworkDelayedList(homeworkDelayedList);


        return data;
    }


    @Override
    public Page<CourseTeachingClassHomeworkBaseinfoViewData> getPage(String t_course_teaching_class_id,
                                                                     String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize) {
        Page<CourseTeachingClassHomeworkBaseinfo> courseTeachingClassHomeworkBaseinfoPage = courseTeachingClassHomeworkBaseinfoRepository.getPage(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, pageNo, pageSize);
        if (courseTeachingClassHomeworkBaseinfoPage == null) return null;


        List<CourseTeachingClassHomeworkBaseinfoViewData> data = new ArrayList<>();


        for (CourseTeachingClassHomeworkBaseinfo courseTeachingClassHomeworkBaseinfo : courseTeachingClassHomeworkBaseinfoPage.getResult()) {

            CourseTeachingClassHomeworkBaseinfoViewData courseTeachingClassHomeworkBaseinfoViewData = getCourseTeachingClassHomeworkBaseinfoViewDataByID(courseTeachingClassHomeworkBaseinfo.getId());

            if (courseTeachingClassHomeworkBaseinfoViewData != null)
                data.add(courseTeachingClassHomeworkBaseinfoViewData);
            // System.out.println(rs.getString("t_user_id"));

        }


        return new Page<>(courseTeachingClassHomeworkBaseinfoPage.getStart(), courseTeachingClassHomeworkBaseinfoPage.getTotalCount(), pageSize, data);

    }

}
