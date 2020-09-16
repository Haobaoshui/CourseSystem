package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.*;
import com.haobaoshui.course.model.user.TeacherViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassReferenceFileRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassReferenceRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassReferenceService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassReferenceTypeService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassService;
import com.haobaoshui.course.service.user.TeacherService;
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
public class CourseTeachingClassReferenceServiceImpl implements CourseTeachingClassReferenceService {


    private final CourseTeachingClassReferenceRepository courseTeachingClassReferenceRepository;
    private final CourseTeachingClassReferenceFileRepository courseTeachingClassReferenceFileRepository;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseTeachingClassService courseTeachingClassService;
    @Autowired
    CourseTeachingClassReferenceTypeService courseTeachingClassReferenceTypeService;

    @Autowired
    public CourseTeachingClassReferenceServiceImpl(CourseTeachingClassReferenceRepository courseTeachingClassReferenceRepository,
                                                   CourseTeachingClassReferenceFileRepository courseTeachingClassReferenceFileRepository) {
        this.courseTeachingClassReferenceRepository = courseTeachingClassReferenceRepository;
        this.courseTeachingClassReferenceFileRepository = courseTeachingClassReferenceFileRepository;
    }

    @Override
    public CourseTeachingClassReferenceFile getFileByID(String id) {
        return courseTeachingClassReferenceFileRepository.getByID(id);
    }

    private void addFiles(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id, MultipartFile[] files) {

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String filename = file.getOriginalFilename();

            String prefix = filename.substring(filename.lastIndexOf(".") + 1);

            ServletContext sc = request.getSession().getServletContext();
            String dir = sc.getRealPath(RealPathConst.RealPath_ReferenceFile); // 设定文件保存的目录

            prefix = prefix.toLowerCase();

            String idfilename = GUID.getGUID();

            String localfilename = dir + RealPathConst.RealPath_PathSeparator + idfilename + "." + prefix;
            String hreffilename = idfilename + "." + prefix;

            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                courseTeachingClassReferenceFileRepository.add(t_course_teaching_class_homework_baseinfo_id, filename, hreffilename);

            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

    // 增加
    @Override
    public void add(HttpServletRequest request, String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id,
                    String t_teacher_id, String title, String content, MultipartFile[] files) {

        String t_course_teaching_class_homework_baseinfo_id = courseTeachingClassReferenceRepository.add(t_course_teaching_class_id, t_teacher_id,
                t_course_teaching_class_homeworktype_id, title, content, new Date(), new Date());

        addFiles(request, t_course_teaching_class_homework_baseinfo_id, files);

    }

    // 删除
    @Override
    public void deleteByID(HttpServletRequest request, String t_course_teaching_class_reference_id) {

        // 删除基本信息及其附件文件
        deleteBaseInfoByID(request, t_course_teaching_class_reference_id);

        // TODO：删除其他数据
    }

    // 删除基本信息及其附件文件
    private void deleteBaseInfoByID(HttpServletRequest request, String t_course_teaching_class_reference_id) {
        CourseTeachingClassReference plan = getByID(t_course_teaching_class_reference_id);
        if (plan == null) return;

        // 1.删除文件
        deleteFilesByReferenceId(request, t_course_teaching_class_reference_id);

        // 2.删除基本信息
        courseTeachingClassReferenceRepository.deleteById(t_course_teaching_class_reference_id);
    }

    /**
     * 根据t_course_teaching_class_id删除
     */
    @Override
    public void deleteByCourseTeachingClassId(HttpServletRequest request, String t_course_teaching_class_id) {
        List<CourseTeachingClassReference> list = courseTeachingClassReferenceRepository.getByCourseTeachingClassID(t_course_teaching_class_id);
        if (list == null) return;
        for (CourseTeachingClassReference r : list) deleteByID(request, r.getId());
    }

    /**
     * 根据t_teacher_id删除
     */
    @Override
    public void deleteByTeacherId(HttpServletRequest request, String t_teacher_id) {
        List<CourseTeachingClassReference> list = courseTeachingClassReferenceRepository.getByTeacherID(t_teacher_id);
        if (list == null) return;
        for (CourseTeachingClassReference r : list) deleteByID(request, r.getId());
    }

    /**
     * 根据t_course_teaching_class_id、t_teacher_id删除
     */
    @Override
    public void deleteByCourseTeachingClassIdAndTeacherId(HttpServletRequest request, String t_course_teaching_class_id,
                                                          String t_teacher_id) {
        List<CourseTeachingClassReference> list = courseTeachingClassReferenceRepository.getByCourseTeachingClassIDAndTeacherId(t_course_teaching_class_id,
                t_teacher_id);
        if (list == null) return;
        for (CourseTeachingClassReference r : list) deleteByID(request, r.getId());
    }

    /**
     * 根据t_course_teaching_class_reference_type_id删除
     */
    @Override
    public void deleteByReferenceTypeId(HttpServletRequest request, String t_course_teaching_class_reference_type_id) {
        List<CourseTeachingClassReference> list = courseTeachingClassReferenceRepository.getByReferenceTypeId(t_course_teaching_class_reference_type_id);
        if (list == null) return;
        for (CourseTeachingClassReference r : list) deleteByID(request, r.getId());
    }

    /**
     * 根据t_course_teaching_class_id、t_teacher_id、t_course_teaching_class_reference_type_id删除
     */
    @Override
    public void deleteByCourseTeachingClassIdAndTeacherIdAndReferenceTypeId(HttpServletRequest request, String t_course_teaching_class_id,
                                                                            String t_teacher_id, String t_course_teaching_class_reference_type_id) {
        List<CourseTeachingClassReference> list = courseTeachingClassReferenceRepository.getByCourseTeachingClassIDAndTeacherIdAndReferenceTypeId(
                t_course_teaching_class_id, t_teacher_id, t_course_teaching_class_reference_type_id);
        if (list == null) return;
        for (CourseTeachingClassReference r : list) deleteByID(request, r.getId());
    }

    // 删除文件
    private void deleteFilesByReferenceId(HttpServletRequest request, String t_course_teaching_class_reference_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_ReferenceFile); // 设定文件保存的目录

        List<CourseTeachingClassReferenceFile> list = courseTeachingClassReferenceFileRepository
                .getByCourseTeachingClassHomeworkBaseInfoID(t_course_teaching_class_reference_id);
        if (list != null) for (CourseTeachingClassReferenceFile homeworkfile : list) {
            String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

            File localfile = new File(path);

            if (localfile.exists()) localfile.delete();

            courseTeachingClassReferenceFileRepository.deleteById(homeworkfile.getId());

        }
    }

    @Override
    public void update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, String title,
                       String content, MultipartFile[] files) {

        // 1.删除文件
        deleteFilesByReferenceId(request, t_course_teaching_class_homework_baseinfo_id);

        // 2.更改基本信息
        courseTeachingClassReferenceRepository.update(t_course_teaching_class_homework_baseinfo_id, t_teacher_id, title, content, new Date());

        // 3.增加文件
        addFiles(request, t_course_teaching_class_homework_baseinfo_id, files);
    }

    @Override
    public void Update(HttpServletRequest request, String t_course_teaching_class_homework_baseinfo_id, String t_teacher_id, String title,
                       String content) {

        // 2.更改基本信息
        courseTeachingClassReferenceRepository.update(t_course_teaching_class_homework_baseinfo_id, t_teacher_id, title, content, new Date());

    }

    @Override
    public CourseTeachingClassReference getByID(String id) {
        return courseTeachingClassReferenceRepository.getByID(id);
    }

    @Override
    public CourseTeachingClassReferenceViewData getCourseTeachingClassHomeworkBaseinfoViewDataByID(String id) {
        CourseTeachingClassReferenceViewData data = new CourseTeachingClassReferenceViewData();

        CourseTeachingClassReference reference = getByID(id);
        data.setReference(reference);

        CourseTeachingClass courseteachingclass = courseTeachingClassService
                .getById(reference.getCourseTeachingClassId());
        data.setCourseteachingclass(courseteachingclass);

        TeacherViewData teacherviewdata = teacherService.getTeacherViewById(reference.getTeacherId());
        data.setTeacher(teacherviewdata);

        CourseTeachingClassReferenceType type = courseTeachingClassReferenceTypeService
                .getByID(reference.getCourseTeachingClassReferenceTypeId());
        data.setType(type);

        List<CourseTeachingClassReferenceFile> fileList = courseTeachingClassReferenceFileRepository.getByCourseTeachingClassHomeworkBaseInfoID(reference.getId());

        data.setFileList(fileList);

        return data;
    }

    private static String getFileLength(HttpServletRequest request, String filepath) {

        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_ReferenceFile); // 设定文件保存的目录

        String localfilename = dir + RealPathConst.RealPath_PathSeparator + filepath;
        File file = new File(localfilename);

        String strLen = "";
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        if (file.exists() && file.isFile()) {
            long n = file.length();
            if (n < 1024) strLen = n + "字节";
            else if (n < 1024 * 1024) {
                double d = n;
                strLen = df.format(d / 1024) + "K";
            } else if (n < 1024 * 1024 * 1024) {
                double d = n;
                strLen = df.format(d / (1024 * 1024)) + "M";
            } else if (n < 1024 * 1024 * 1024 * 1024) {
                double d = n;
                strLen = df.format(d / (1024 * 1024 * 1024)) + "G";
            }
        }

        return strLen;

    }

    /**
     * 获得每个文件长度
     */
    private void getFileLength(HttpServletRequest request, Page<CourseTeachingClassReferenceViewData> page) {
        if (page == null) return;

        List<CourseTeachingClassReferenceViewData> list = page.getResult();
        if (list == null) return;

        for (CourseTeachingClassReferenceViewData data : list) {
            List<CourseTeachingClassReferenceFile> fileList = data.getFileList();
            if (fileList == null) continue;
            for (CourseTeachingClassReferenceFile f : fileList)
                f.setFilelength(getFileLength(request, f.getFilepath()));
        }

    }

    @Override
    public Page<CourseTeachingClassReferenceViewData> getPage(HttpServletRequest request, String t_course_teaching_class_id,
                                                              String t_course_teaching_class_homeworktype_id, int pageNo, int pageSize) {
        Page<CourseTeachingClassReference> courseTeachingClassReferencePage = courseTeachingClassReferenceRepository.getPage(t_course_teaching_class_id, t_course_teaching_class_homeworktype_id, pageNo, pageSize);

        if (courseTeachingClassReferencePage == null) return null;

        List<CourseTeachingClassReferenceViewData> data = new ArrayList<>();

        for (CourseTeachingClassReference courseTeachingClassReference : courseTeachingClassReferencePage.getResult()) {
            CourseTeachingClassReferenceViewData courseTeachingClassReferenceViewData = getCourseTeachingClassHomeworkBaseinfoViewDataByID(courseTeachingClassReference.getId());
            if (courseTeachingClassReferenceViewData != null) data.add(courseTeachingClassReferenceViewData);
        }

        Page<CourseTeachingClassReferenceViewData> page = new Page<>(courseTeachingClassReferencePage.getStart(), courseTeachingClassReferencePage.getTotalCount(), pageSize, data);
        getFileLength(request, page);
        return page;
    }

}
