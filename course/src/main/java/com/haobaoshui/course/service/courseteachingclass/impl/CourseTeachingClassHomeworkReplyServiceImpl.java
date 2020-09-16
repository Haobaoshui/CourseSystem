package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReply;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReplyFile;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkReplyViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkSubmitBaseinfo;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkReplyFileRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkReplyRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkReplyService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkSubmitService;
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
public class CourseTeachingClassHomeworkReplyServiceImpl implements CourseTeachingClassHomeworkReplyService {


    private final CourseTeachingClassHomeworkReplyRepository courseTeachingClassHomeworkReplyRepository;
    private final CourseTeachingClassHomeworkReplyFileRepository courseTeachingClassHomeworkReplyFileRepository;
    @Autowired
    CourseTeachingClassHomeworkSubmitService courseTeachingClassHomeworkSubmitService;


    @Autowired
    public CourseTeachingClassHomeworkReplyServiceImpl(CourseTeachingClassHomeworkReplyRepository courseTeachingClassHomeworkReplyRepository,
													   CourseTeachingClassHomeworkReplyFileRepository courseTeachingClassHomeworkReplyFileRepository) {
        this.courseTeachingClassHomeworkReplyRepository = courseTeachingClassHomeworkReplyRepository;
        this.courseTeachingClassHomeworkReplyFileRepository = courseTeachingClassHomeworkReplyFileRepository;
    }

    @Override
    public CourseTeachingClassHomeworkReplyFile getFileByID(String id) {
        return courseTeachingClassHomeworkReplyFileRepository.getByID(id);
    }

    private void addFiles(HttpServletRequest request, String t_course_teaching_class_homework_reply_id, MultipartFile[] files) {

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String filename = file.getOriginalFilename();

            String prefix = filename.substring(filename.lastIndexOf(".") + 1);

            ServletContext sc = request.getSession().getServletContext();
            String dir = sc.getRealPath(RealPathConst.RealPath_HomeworkReplyFile); // 设定文件保存的目录

            prefix = prefix.toLowerCase();

            String idfilename = GUID.getGUID();

            String localfilename = dir + RealPathConst.RealPath_PathSeparator + idfilename + "." + prefix;
            String hreffilename = idfilename + "." + prefix;

            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

				courseTeachingClassHomeworkReplyFileRepository.add(t_course_teaching_class_homework_reply_id, filename, hreffilename);

            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

    // 增加
    @Override
    public void add(HttpServletRequest request, String t_course_teaching_class_homework_submit_baseinfo_id, String t_teacher_id,
					String title, String content, MultipartFile[] files) {

        String t_course_teaching_class_homework_reply_id = courseTeachingClassHomeworkReplyRepository.add(t_course_teaching_class_homework_submit_baseinfo_id, t_teacher_id,
                title, content, new Date(), new Date());

		addFiles(request, t_course_teaching_class_homework_reply_id, files);

    }

    // 删除
    @Override
    public void deleteByID(HttpServletRequest request, String t_course_teaching_class_homework_reply_id) {

        // 删除基本信息及其附件文件
		deleteBaseInfoByID(request, t_course_teaching_class_homework_reply_id);

        // TODO：删除其他数据
    }


    /**
     * 根据t_teacher_id删除回复
     */
    @Override
    public void deleteByTeacherID(HttpServletRequest request, String t_teacher_id) {
        List<CourseTeachingClassHomeworkReply> list = courseTeachingClassHomeworkReplyRepository.getByTeacherID(t_teacher_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkReply r : list) deleteByID(request, r.getId());
    }

    @Override
    public void deleteByCourseTeachingClassHomeworkSubmitBaseinfoID(HttpServletRequest request,
																	String t_course_teaching_class_homework_submit_baseinfo_id) {
        List<CourseTeachingClassHomeworkReply> list = courseTeachingClassHomeworkReplyRepository.getByCourseTeachingClassHomeworkSubmitBaseInfoID(t_course_teaching_class_homework_submit_baseinfo_id);
        if (list == null) return;
        for (CourseTeachingClassHomeworkReply r : list) deleteByID(request, r.getId());
    }

    // 删除基本信息及其附件文件
    private void deleteBaseInfoByID(HttpServletRequest request, String t_course_teaching_class_homework_reply_id) {
        CourseTeachingClassHomeworkReply plan = getByID(t_course_teaching_class_homework_reply_id);
        if (plan == null) return;

        // 1.删除文件
		deleteFilesByHomeworkBaseInfoId(request, t_course_teaching_class_homework_reply_id);

        // 2.删除基本信息
		courseTeachingClassHomeworkReplyRepository.deleteById(t_course_teaching_class_homework_reply_id);
    }

    // 删除文件
    private void deleteFilesByHomeworkBaseInfoId(HttpServletRequest request, String t_course_teaching_class_homework_reply_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_HomeworkReplyFile); // 设定文件保存的目录

        List<CourseTeachingClassHomeworkReplyFile> list = courseTeachingClassHomeworkReplyFileRepository
                .getByCourseTeachingClassHomeworkReplyID(t_course_teaching_class_homework_reply_id);
        if (list != null) for (CourseTeachingClassHomeworkReplyFile homeworkfile : list) {
			String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

			File localfile = new File(path);

			if (localfile.exists()) localfile.delete();

			courseTeachingClassHomeworkReplyFileRepository.deleteById(homeworkfile.getId());

		}
    }

    @Override
    public void update(HttpServletRequest request, String t_course_teaching_class_homework_reply_id, String t_teacher_id, String title,
					   String content, MultipartFile[] files) {

        // 1.删除文件
		deleteFilesByHomeworkBaseInfoId(request, t_course_teaching_class_homework_reply_id);

        // 2.更改基本信息
		courseTeachingClassHomeworkReplyRepository.update(t_course_teaching_class_homework_reply_id, title, content, new Date());

        // 3.增加文件
		addFiles(request, t_course_teaching_class_homework_reply_id, files);
    }

    @Override
    public CourseTeachingClassHomeworkReply getByID(String id) {
        return courseTeachingClassHomeworkReplyRepository.getByID(id);
    }

    @Override
    public CourseTeachingClassHomeworkReplyViewData getCourseTeachingClassHomeworkReplyViewDataByID(String id) {
        CourseTeachingClassHomeworkReplyViewData data = new CourseTeachingClassHomeworkReplyViewData();

        CourseTeachingClassHomeworkReply reply = getByID(id);
        data.setReply(reply);

        CourseTeachingClassHomeworkSubmitBaseinfo homeworksubmitbaseinfo = courseTeachingClassHomeworkSubmitService.getByID(reply
                .getCourseTeachingClassHomeworkSubmitBaseinfoId());
        data.setHomeworksubmitbaseinfo(homeworksubmitbaseinfo);

        List<CourseTeachingClassHomeworkReplyFile> repplyFileList = courseTeachingClassHomeworkReplyFileRepository.getByCourseTeachingClassHomeworkReplyID(reply
                .getId());
        data.setRepplyFileList(repplyFileList);
        return data;
    }

    @Override
    public int getCount(String t_course_teaching_class_homework_submit_baseinfo_id, String t_student_id) {
        return courseTeachingClassHomeworkReplyRepository.getCount(t_course_teaching_class_homework_submit_baseinfo_id, t_student_id);
    }

    @Override
    public Page<CourseTeachingClassHomeworkReplyViewData> getPage(String t_course_teaching_class_homework_baseinfo_id, String t_student_id,
																  int pageNo, int pageSize) {
        Page<CourseTeachingClassHomeworkReply> courseTeachingClassHomeworkReplyPage = courseTeachingClassHomeworkReplyRepository.getPage(t_course_teaching_class_homework_baseinfo_id, t_student_id, pageNo, pageSize);
        if (courseTeachingClassHomeworkReplyPage == null) return null;
        List<CourseTeachingClassHomeworkReplyViewData> data = new ArrayList<>();

        for (CourseTeachingClassHomeworkReply courseTeachingClassHomeworkReply : courseTeachingClassHomeworkReplyPage.getResult()) {
            CourseTeachingClassHomeworkReplyViewData courseTeachingClassReferenceViewData = getCourseTeachingClassHomeworkReplyViewDataByID(courseTeachingClassHomeworkReply.getId());
            if (courseTeachingClassReferenceViewData != null) data.add(courseTeachingClassReferenceViewData);
        }

        return new Page<>(courseTeachingClassHomeworkReplyPage.getStart(),
                courseTeachingClassHomeworkReplyPage.getTotalCount(), pageSize, data);

    }

    @Override
    public void update(String id, String title, String content, Date modifieddate, String filename, String filepath) {
		courseTeachingClassHomeworkReplyRepository.update(id, title, content, modifieddate);
    }

}
