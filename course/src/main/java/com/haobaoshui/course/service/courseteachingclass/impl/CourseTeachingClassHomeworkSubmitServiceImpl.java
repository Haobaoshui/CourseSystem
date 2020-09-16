package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.*;
import com.haobaoshui.course.model.courseteachingclass.*;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfoRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkSubmitFileRepository;
import com.haobaoshui.course.service.courseteachingclass.*;
import com.haobaoshui.course.service.user.StudentService;
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
public class CourseTeachingClassHomeworkSubmitServiceImpl implements CourseTeachingClassHomeworkSubmitService {


    private final CourseTeachingClassHomeworkSubmitBaseinfoRepository courseTeachingClassHomeworkSubmitBaseinfoRepository;
    private final CourseTeachingClassHomeworkSubmitFileRepository courseTeachingClassHomeworkSubmitFileRepository;

    @Autowired
    CourseTeachingClassHomeworkService courseTeachingClassHomeworkService;

    @Autowired
    CourseTeachingClassStudentService courseTeachingClassStudentService;
    @Autowired
    private CourseTeachingClassService courseTeachingClassService;
    @Autowired
    private CourseTeachingClassHomeworkStatisticsService courseTeachingClassHomeworkStatisticsService;
    @Autowired
    private CourseTeachingClassHomeworkReplyService courseTeachingClassHomeworkReplyService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseTeachingClassStudentGroupService courseTeachingClassStudentGroupService;

    @Autowired
    public CourseTeachingClassHomeworkSubmitServiceImpl(CourseTeachingClassHomeworkSubmitBaseinfoRepository courseTeachingClassHomeworkSubmitBaseinfoRepository,
                                                        CourseTeachingClassHomeworkSubmitFileRepository courseTeachingClassHomeworkSubmitFileRepository) {
        this.courseTeachingClassHomeworkSubmitBaseinfoRepository = courseTeachingClassHomeworkSubmitBaseinfoRepository;

        this.courseTeachingClassHomeworkSubmitFileRepository = courseTeachingClassHomeworkSubmitFileRepository;


    }

    @Autowired
    public void setCourseTeachingClassHomeworkStatisticsService(CourseTeachingClassHomeworkStatisticsService courseTeachingClassHomeworkStatisticsService) {
        this.courseTeachingClassHomeworkStatisticsService = courseTeachingClassHomeworkStatisticsService;
    }

    @Override
    public CourseTeachingClassHomeworkSubmitFile getFileByID(String id) {
        return courseTeachingClassHomeworkSubmitFileRepository.getByID(id);
    }

    private void addFiles(HttpServletRequest request, FileNodePropertyManager fileNodePropertyManager,
                          String t_course_teaching_class_homework_submit_baseinfo_id, MultipartFile[] files) {

        if (fileNodePropertyManager == null) return;

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String filename = file.getOriginalFilename();

            String prefix = filename.substring(filename.lastIndexOf(".") + 1);

            ServletContext sc = request.getSession().getServletContext();
            String dir = sc.getRealPath(RealPathConst.RealPath_HomeworkSubmitFile); // 设定文件保存的目录

            prefix = prefix.toLowerCase();

            String idfilename = GUID.getGUID();

            String localfilename = dir + RealPathConst.RealPath_PathSeparator + idfilename + "." + prefix;
            String hreffilename = idfilename + "." + prefix;

            int fileNodeId = fileNodePropertyManager.getNodeByFileIndex(i).getNodeID();

            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                courseTeachingClassHomeworkSubmitFileRepository.add(t_course_teaching_class_homework_submit_baseinfo_id, fileNodeId, filename,
                        hreffilename);

            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

    /**
     * 增加作业
     */
    @Override
    public void add(HttpServletRequest request, FileNodePropertyManager fileNodePropertyManager,
                    String t_course_teaching_class_homework_baseinfo_id, String t_student_id, String title, String content,
                    MultipartFile[] files) {

        String t_course_teaching_class_homework_submit_baseinfo_id = courseTeachingClassHomeworkSubmitBaseinfoRepository.add(
                t_course_teaching_class_homework_baseinfo_id, t_student_id, title, content, new Date(), new Date());

        addFiles(request, fileNodePropertyManager, t_course_teaching_class_homework_submit_baseinfo_id, files);

    }

    // 删除
    @Override
    public void deleteByID(HttpServletRequest request, String t_course_teaching_class_homework_submit_baseinfo_id) {

        // 删除回复
        courseTeachingClassHomeworkReplyService.deleteByCourseTeachingClassHomeworkSubmitBaseinfoID(request,
                t_course_teaching_class_homework_submit_baseinfo_id);

        // 删除基本信息及其附件文件
        deleteBaseInfoByID(request, t_course_teaching_class_homework_submit_baseinfo_id);

    }

    @Override
    public void deleteByStudentID(HttpServletRequest request, String t_student_id) {
        List<CourseTeachingClassHomeworkSubmitBaseinfo> list = courseTeachingClassHomeworkSubmitBaseinfoRepository.getByStudentID(t_student_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkSubmitBaseinfo s : list) deleteByID(request, s.getId());

    }

    /**
     * 根据t_course_teaching_class_id和t_student_id删除学生该课程中的提交作业
     */
    @Override
    public void deleteByCourseTeachingClassIdAndStudentID(HttpServletRequest request, String t_course_teaching_class_id,
                                                          String t_student_id) {
        List<CourseTeachingClassHomeworkBaseinfo> listhomework = courseTeachingClassHomeworkService
                .getByCourseTeachingClassID(t_course_teaching_class_id);
        if (listhomework == null) return;
        for (CourseTeachingClassHomeworkBaseinfo h : listhomework) {
            List<CourseTeachingClassHomeworkSubmitBaseinfo> list = courseTeachingClassHomeworkSubmitBaseinfoRepository
                    .getByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(h.getId(), t_student_id);
            if (list == null) return;
            for (CourseTeachingClassHomeworkSubmitBaseinfo s : list) deleteByID(request, s.getId());
        }

    }

    @Override
    public void deleteByCourseTeachingClassHomeworkBaseinfoID(HttpServletRequest request,
                                                              String t_course_teaching_class_homework_baseinfo_id) {
        List<CourseTeachingClassHomeworkSubmitBaseinfo> list = courseTeachingClassHomeworkSubmitBaseinfoRepository
                .getByCourseTeachingClassHomeworkBaseinfoID(t_course_teaching_class_homework_baseinfo_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkSubmitBaseinfo s : list) deleteByID(request, s.getId());
    }

    // 删除基本信息及其附件文件
    private void deleteBaseInfoByID(HttpServletRequest request,
                                    String t_course_teaching_class_homework_submit_baseinfo_id) {
        CourseTeachingClassHomeworkSubmitBaseinfo plan = getByID(t_course_teaching_class_homework_submit_baseinfo_id);
        if (plan == null) return;

        // 1.删除文件
        deleteFilesByHomeworkSubmitBaseInfoId(request, t_course_teaching_class_homework_submit_baseinfo_id);

        // 2.删除基本信息
        courseTeachingClassHomeworkSubmitBaseinfoRepository.deleteById(t_course_teaching_class_homework_submit_baseinfo_id);
    }

    // 删除文件
    private void deleteFilesByHomeworkSubmitBaseInfoId(HttpServletRequest request,
                                                       String t_course_teaching_class_homework_submit_baseinfo_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_HomeworkSubmitFile); // 设定文件保存的目录

        List<CourseTeachingClassHomeworkSubmitFile> list = courseTeachingClassHomeworkSubmitFileRepository
                .getByCourseTeachingClassHomeworkBaseInfoID(t_course_teaching_class_homework_submit_baseinfo_id);
        if (list != null) for (CourseTeachingClassHomeworkSubmitFile homeworkfile : list) {
            String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

            File localfile = new File(path);

            if (localfile.exists()) localfile.delete();

            courseTeachingClassHomeworkSubmitFileRepository.deleteById(homeworkfile.getId());

        }
    }

    @Override
    public void update(HttpServletRequest request, String t_course_teaching_class_homework_submit_baseinfo_id,
                       FileNodePropertyManager fileNodePropertyManager, String t_student_id, String title, String content,
                       MultipartFile[] files) {

        // 1.删除文件
        deleteFilesByHomeworkSubmitBaseInfoId(request, t_course_teaching_class_homework_submit_baseinfo_id);

        // 2.更改基本信息
        courseTeachingClassHomeworkSubmitBaseinfoRepository.update(t_course_teaching_class_homework_submit_baseinfo_id, title, content, new Date());

        // 3.增加文件
        addFiles(request, fileNodePropertyManager, t_course_teaching_class_homework_submit_baseinfo_id, files);
    }

    @Override
    public CourseTeachingClassHomeworkSubmitBaseinfo getByID(String id) {
        return courseTeachingClassHomeworkSubmitBaseinfoRepository.getByID(id);
    }

    @Override
    public long getCount(String t_course_teaching_class_homework_baseinfo_id, String t_student_id) {
        return courseTeachingClassHomeworkSubmitBaseinfoRepository.getCount(t_course_teaching_class_homework_baseinfo_id, t_student_id);
    }

    @Override
    public String add(String t_course_teaching_class_homework_baseinfo_id, int fileNodeId, String t_student_id,
                      String title, String content, Date submitdate, Date modifieddate, String filename, String filepath) {

        String t_course_teaching_class_homework_submit_baseinfo_id = courseTeachingClassHomeworkSubmitBaseinfoRepository.add(
                t_course_teaching_class_homework_baseinfo_id, t_student_id, title, content, submitdate, modifieddate);

        courseTeachingClassHomeworkSubmitFileRepository.add(t_course_teaching_class_homework_submit_baseinfo_id, fileNodeId, filename, filepath);
        return t_course_teaching_class_homework_submit_baseinfo_id;
    }

    @Override
    public Page<CourseTeachingClassHomeworkStatisticsViewData> getPageofStatisticsViewData(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize) {
        return courseTeachingClassHomeworkStatisticsService.getPage(t_course_teaching_class_homework_baseinfo_id,
                pageNo, pageSize);
    }

    @Override
    public void update(String id, String title, String content, Date modifieddate, String filename, String filepath) {
        courseTeachingClassHomeworkSubmitBaseinfoRepository.update(id, title, content, modifieddate);
    }

    /**
     * 将指定作业进行压缩
     */
    @Override
    public void zipByHomeworkBaseInfoId(HttpServletRequest request,
                                        String t_course_teaching_class_homework_baseinfo_id) {

        Page<CourseTeachingClassHomeworkSubmitBaseinfoViewData> pageAll = getPageAll(
                t_course_teaching_class_homework_baseinfo_id);

        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_HomeworkSubmitFile); // 设定文件保存的目录

        // 根据实验名称、班级等设置文件夹名称
        CourseTeachingClassHomeworkBaseinfo baseinfo = courseTeachingClassHomeworkService
                .getByID(t_course_teaching_class_homework_baseinfo_id);
        String zipDir = baseinfo.getTitle();

        if (pageAll != null) {
            List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> list = pageAll.getResult();
            List<ZipFileName> filenames = new ArrayList<>();

            for (CourseTeachingClassHomeworkSubmitBaseinfoViewData data : list) {
                // System.out.println(data.getHomeworksubmitbaseinfo().getTitle());

                boolean isDelayedSubmit = data.isDelayedSubmit();// 是否是迟交作业

                List<CourseTeachingClassHomeworkSubmitFile> files = courseTeachingClassHomeworkSubmitFileRepository
                        .getByCourseTeachingClassHomeworkBaseInfoID(data.getHomeworksubmitbaseinfo().getId());
                for (CourseTeachingClassHomeworkSubmitFile file : files) {
                    ZipFileName zipfilename = new ZipFileName();
                    zipfilename.setZipDir(zipDir);

                    String filename = file.getFilename();
                    if (isDelayedSubmit) {
                        String[] userfilenames = filename.split("\\.");
                        if (userfilenames.length == 2) filename = userfilenames[0] + "_迟交" + "." + userfilenames[1];
                        else
                            filename += "_迟交";
                    }

                    // System.out.println(filename);

                    zipfilename.setZipFileName(filename);

                    String path = dir + RealPathConst.RealPath_PathSeparator + file.getFilepath();

                    zipfilename.setRealFilePath(path);

                    filenames.add(zipfilename);
                }

            }


        }


    }

    /**
     * 学生是否允许提交作业
     */
    @Override
    public boolean canSubmit(String t_course_teaching_class_homework_baseinfo_id, String t_student_id) {
        CourseTeachingClassHomeworkBaseinfo baseinfo = courseTeachingClassHomeworkService
                .getByID(t_course_teaching_class_homework_baseinfo_id);
        Date begin = baseinfo.getPubdate();
        Date end = baseinfo.getEnddate();
        Date now = new Date();

        if (now.getTime() > begin.getTime() && now.getTime() < end.getTime()) return true;

        return false;
    }

    /**
     * 统计数据
     */
    @Override
    public StatisticsData getStatisticsData(String t_course_teaching_class_homework_baseinfo_id) {
        StatisticsData s = new StatisticsData();
        CourseTeachingClassHomeworkBaseinfo baseinfo = courseTeachingClassHomeworkService
                .getByID(t_course_teaching_class_homework_baseinfo_id);

        if (baseinfo == null) return null;

        List<CourseTeachingClassHomeworkSubmitBaseinfo> submitlist = courseTeachingClassHomeworkSubmitBaseinfoRepository
                .getByCourseTeachingClassHomeworkBaseinfoID(t_course_teaching_class_homework_baseinfo_id);
        if (submitlist == null) return null;

        int nReplyCount = 0;
        for (CourseTeachingClassHomeworkSubmitBaseinfo sub : submitlist)
            nReplyCount += courseTeachingClassHomeworkReplyService.getCount(sub.getId(), sub.getStudentId());

        CourseTeachingClass ctc = courseTeachingClassService
                .getById(baseinfo.getCourseTeachingClassId());
        int n = courseTeachingClassStudentService.getStudentCountByCourseTeachingClassId(ctc.getId());
        if (n == 0) return null;

        int nsubmit = courseTeachingClassHomeworkSubmitBaseinfoRepository.getRealCount(baseinfo.getId());
        s.setTotalCount(n);
        s.setSubmitCount(nsubmit);
        s.setReplyCount(nReplyCount);

        return s;
    }

    /**
     * 图表数据
     */
    @Override
    public List<chartDataValue> getSubmitChartDataValue(String t_course_teaching_class_homework_baseinfo_id) {
        CourseTeachingClassHomeworkBaseinfo baseinfo = courseTeachingClassHomeworkService
                .getByID(t_course_teaching_class_homework_baseinfo_id);

        if (baseinfo == null) return null;

        CourseTeachingClass ctc = courseTeachingClassService
                .getById(baseinfo.getCourseTeachingClassId());
        int n = courseTeachingClassStudentService.getStudentCountByCourseTeachingClassId(ctc.getId());
        if (n == 0) return null;

        int nsubmit = courseTeachingClassHomeworkSubmitBaseinfoRepository.getCount(baseinfo.getId());

        List<com.haobaoshui.course.model.chartDataValue> list = new ArrayList<>();

        HTMLColors color = new HTMLColors();

        com.haobaoshui.course.model.chartDataValue c1 = new com.haobaoshui.course.model.chartDataValue();
        com.haobaoshui.course.model.chartDataValue c2 = new chartDataValue();

        c1.setValue(nsubmit);
        c1.setColor(color.getColor());
        list.add(c1);

        c2.setValue(n - nsubmit);
        c2.setColor(color.getColor());
        list.add(c2);

        return list;
    }


    /**
     * 得到指定提交情况的详细信息
     */
    @Override
    public CourseTeachingClassHomeworkSubmitBaseinfoViewData getCourseTeachingClassHomeworkSubmitBaseinfoViewDataById(
            String t_course_teaching_class_homework_submit_baseinfo_id) {
        CourseTeachingClassHomeworkSubmitBaseinfoViewData data = new CourseTeachingClassHomeworkSubmitBaseinfoViewData();

        CourseTeachingClassHomeworkSubmitBaseinfo homeworksubmitbaseinfo = getByID(
                t_course_teaching_class_homework_submit_baseinfo_id);
        data.setHomeworksubmitbaseinfo(homeworksubmitbaseinfo);

        StudentViewData student = studentService.getStudentViewByStudentId(homeworksubmitbaseinfo.getStudentId());
        data.setStudent(student);
        String t_student_id = student.getStudent().getId();

        CourseTeachingClassHomeworkBaseinfoViewData homeworkbaseinfoViewData = courseTeachingClassHomeworkService
                .getCourseTeachingClassHomeworkBaseinfoViewDataByID(
                        homeworksubmitbaseinfo.getCourseTeachingClassHomeworkBaseinfoId());
        data.setHomeworkbaseinfoViewData(courseTeachingClassHomeworkService
                .getCourseTeachingClassHomeworkBaseinfoStudentViewData(homeworkbaseinfoViewData, t_student_id));

        List<CourseTeachingClassHomeworkSubmitFile> homeworksubmitFileList = courseTeachingClassHomeworkSubmitFileRepository
                .getByCourseTeachingClassHomeworkBaseInfoID(homeworksubmitbaseinfo.getId());
        data.setHomeworksubmitFileList(homeworksubmitFileList);

        data.setHasReply(courseTeachingClassHomeworkReplyService.getCount(homeworksubmitbaseinfo.getId(), student.getStudent().getId()) > 0);

        //设置小组信息
        CourseTeachingClassStudentGroupViewData studentGroupViewData =
                courseTeachingClassStudentGroupService.getViewDataByStudentId(homeworkbaseinfoViewData.getCourseteachingclass().getId(), t_student_id);
        data.setStudentGroupViewData(studentGroupViewData);

        return data;
    }

    private List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> PageQuery(String t_course_teaching_class_id,
                                                                              int PageBegin, int PageSize) {

        List<String> lstIds = courseTeachingClassHomeworkSubmitBaseinfoRepository.PageQueryIds(t_course_teaching_class_id, PageBegin, PageSize);

        if (lstIds == null) return null;

        List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> list = new ArrayList<>();

        for (String id : lstIds) {
            CourseTeachingClassHomeworkSubmitBaseinfoViewData data = getCourseTeachingClassHomeworkSubmitBaseinfoViewDataById(
                    id);

            if (data != null) list.add(data);

        }
        return list;
    }

    private List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> PageQuery(String t_course_teaching_class_homework_baseinfo_id,
                                                                              String t_student_id, int PageBegin, int PageSize) {

        List<String> lstIds = courseTeachingClassHomeworkSubmitBaseinfoRepository.PageQueryIds(t_course_teaching_class_homework_baseinfo_id, t_student_id, PageBegin, PageSize);

        if (lstIds == null) return null;

        List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> list = new ArrayList<>();

        for (String id : lstIds) {

            CourseTeachingClassHomeworkSubmitBaseinfoViewData data = getCourseTeachingClassHomeworkSubmitBaseinfoViewDataById(
                    id);

            if (data != null) list.add(data);

        }
        return list;
    }

    @Override
    public Page<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, int pageNo, int pageSize) {
        long totalCount = courseTeachingClassHomeworkSubmitBaseinfoRepository.getCount(t_course_teaching_class_homework_baseinfo_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> data = PageQuery(
                t_course_teaching_class_homework_baseinfo_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    @Override
    public Page<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getPageAll(
            String t_course_teaching_class_homework_baseinfo_id) {
        long totalCount = courseTeachingClassHomeworkSubmitBaseinfoRepository.getCount(t_course_teaching_class_homework_baseinfo_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象

        List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> data = PageQuery(
                t_course_teaching_class_homework_baseinfo_id, 0, (int) totalCount);

        return new Page<>(0, totalCount, (int) totalCount, data);

    }

    @Override
    public Page<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getPage(
            String t_course_teaching_class_homework_baseinfo_id, String t_student_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_course_teaching_class_homework_baseinfo_id, t_student_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> data = PageQuery(
                t_course_teaching_class_homework_baseinfo_id, t_student_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    /**
     * 得到指定学生、指定作业的提交情况
     */
    @Override
    public List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getViewDataByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(
            String t_course_teaching_class_homework_baseinfo_id, String t_student_id) {

        List<String> lstIds = courseTeachingClassHomeworkSubmitBaseinfoRepository.getIdsByCourseTeachingClassHomeworkBaseinfoIDAndStudentID(
                t_course_teaching_class_homework_baseinfo_id, t_student_id);

        if (lstIds == null) return null;

        List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> list = new ArrayList<>();

        for (String id : lstIds) {
            CourseTeachingClassHomeworkSubmitBaseinfoViewData data = getCourseTeachingClassHomeworkSubmitBaseinfoViewDataById(
                    id);

            list.add(data);
        }

        return list;
    }

    /**
     * 得到指定学生提交情况
     */
    @Override
    public List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> getCourseTeachingClassHomeworkSubmitBaseinfoViewDataByStudentID(
            String t_student_id) {

        List<String> lstIds = courseTeachingClassHomeworkSubmitBaseinfoRepository.getIdsByStudentID(t_student_id);

        if (lstIds == null) return null;

        List<CourseTeachingClassHomeworkSubmitBaseinfoViewData> list = new ArrayList<>();

        for (String id : lstIds) {
            CourseTeachingClassHomeworkSubmitBaseinfoViewData data = getCourseTeachingClassHomeworkSubmitBaseinfoViewDataById(
                    id);

            list.add(data);
        }

        return list;
    }

}
