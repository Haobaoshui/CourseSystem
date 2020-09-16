package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopic;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopicFile;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopicViewData;
import com.haobaoshui.course.model.user.UserInfoViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumTopicFileRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumTopicRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassForumReplyService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassForumTopicService;
import com.haobaoshui.course.service.user.UserService;
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
public class CourseTeachingClassForumTopicServiceImpl implements CourseTeachingClassForumTopicService {


    private final CourseTeachingClassForumTopicRepository courseTeachingClassForumTopicRepository;
    private final CourseTeachingClassForumTopicFileRepository courseTeachingClassForumTopicFileRepository;
    private final CourseTeachingClassForumReplyService courseTeachingClassForumReplyService;
    @Autowired
    UserService userService;


    public CourseTeachingClassForumTopicServiceImpl(CourseTeachingClassForumTopicRepository courseTeachingClassForumTopicRepository,
                                                    CourseTeachingClassForumTopicFileRepository courseTeachingClassForumTopicFileRepository,
                                                    CourseTeachingClassForumReplyService courseTeachingClassForumReplyService) {
        this.courseTeachingClassForumTopicRepository = courseTeachingClassForumTopicRepository;
        this.courseTeachingClassForumTopicFileRepository = courseTeachingClassForumTopicFileRepository;
        this.courseTeachingClassForumReplyService = courseTeachingClassForumReplyService;
    }

    private void addFiles(HttpServletRequest request, String t_forum_reply_id, MultipartFile[] files) {

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String filename = file.getOriginalFilename();

            String prefix = filename.substring(filename.lastIndexOf(".") + 1);

            ServletContext sc = request.getSession().getServletContext();
            String dir = sc.getRealPath(RealPathConst.RealPath_ForumTopicFile); // 设定文件保存的目录

            prefix = prefix.toLowerCase();

            String idfilename = GUID.getGUID();

            String localfilename = dir + RealPathConst.RealPath_PathSeparator + idfilename + "." + prefix;
            String hreffilename = idfilename + "." + prefix;

            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                courseTeachingClassForumTopicFileRepository.add(t_forum_reply_id, filename, hreffilename);

            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

    // 删除文件
    private void deleteFilesByForumTopicId(HttpServletRequest request, String t_forum_topic_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_ForumTopicFile); // 设定文件保存的目录

        List<CourseTeachingClassForumTopicFile> list = courseTeachingClassForumTopicFileRepository.getByForumTopicID(t_forum_topic_id);
        if (list != null) for (CourseTeachingClassForumTopicFile homeworkfile : list) {
            String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

            File localfile = new File(path);

            if (localfile.exists()) localfile.delete();

            courseTeachingClassForumTopicFileRepository.deleteById(homeworkfile.getId());

        }
    }

    @Override
    public void deleteById(HttpServletRequest request, String t_forum_topic_id) {

        // 1.删除文件
        deleteFilesByForumTopicId(request, t_forum_topic_id);

        // 2.删除回复
        courseTeachingClassForumReplyService.deleteByForumTopicId(request, t_forum_topic_id);

        // 3.删除主题
        courseTeachingClassForumTopicRepository.deleteById(t_forum_topic_id);
    }

    /**
     * 根据t_user_id删除
     */
    @Override
    public void deleteByUserId(HttpServletRequest request, String t_user_id) {
        List<CourseTeachingClassForumTopic> list = courseTeachingClassForumTopicRepository.getByUserID(t_user_id);
        if (list == null) return;
        for (CourseTeachingClassForumTopic f : list) deleteById(request, f.getId());
    }

    /**
     * 根据t_course_teaching_class_id、t_user_id删除
     */
    @Override
    public void deleteByCourseTeachingClassIDAndUserID(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id) {
        List<CourseTeachingClassForumTopic> list = courseTeachingClassForumTopicRepository.getByCourseTeachingClassIDAndUserID(t_course_teaching_class_id, t_user_id);
        if (list == null) return;
        for (CourseTeachingClassForumTopic f : list) deleteById(request, f.getId());
    }

    /**
     * 根据t_course_teaching_class_id删除
     */
    @Override
    public void deleteByCourseTeachingClassID(HttpServletRequest request, String t_course_teaching_class_id) {
        List<CourseTeachingClassForumTopic> list = courseTeachingClassForumTopicRepository.getByCourseTeachingClassID(t_course_teaching_class_id);
        if (list == null) return;
        for (CourseTeachingClassForumTopic f : list) deleteById(request, f.getId());
    }

    @Override
    public String add(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id, String title, String content,
                      String created_date, String last_modified_date, MultipartFile[] files) {
        String t_forum_topic_id = courseTeachingClassForumTopicRepository
                .add(t_course_teaching_class_id, t_user_id, title, content, created_date, last_modified_date);
        addFiles(request, t_forum_topic_id, files);
        return t_forum_topic_id;
    }

    @Override
    public String add(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id, String title, String content,
                      Date created_date, Date last_modified_date, MultipartFile[] files) {

        String t_forum_topic_id = courseTeachingClassForumTopicRepository
                .add(t_course_teaching_class_id, t_user_id, title, content, created_date, last_modified_date);
        addFiles(request, t_forum_topic_id, files);
        return t_forum_topic_id;
    }


    @Override
    public CourseTeachingClassForumTopicFile getFileByID(String id) {
        return courseTeachingClassForumTopicFileRepository.getByID(id);
    }

    @Override
    public Page<CourseTeachingClassForumTopicViewData> getPage(String t_course_teaching_class_id, int pageNo, int pageSize) {
        Page<CourseTeachingClassForumTopic> courseTeachingClassForumTopicPage = courseTeachingClassForumTopicRepository.getPage(t_course_teaching_class_id, pageNo, pageSize);
        if (courseTeachingClassForumTopicPage == null) return null;
        List<CourseTeachingClassForumTopicViewData> data = new ArrayList<>();

        for (CourseTeachingClassForumTopic courseTeachingClassForumTopic : courseTeachingClassForumTopicPage.getResult()) {
            CourseTeachingClassForumTopicViewData courseTeachingClassReferenceViewData = getForumTopicViewDataById(courseTeachingClassForumTopic.getId());
            if (courseTeachingClassReferenceViewData != null) data.add(courseTeachingClassReferenceViewData);
        }

        return new Page<>(courseTeachingClassForumTopicPage.getStart(),
                courseTeachingClassForumTopicPage.getTotalCount(), pageSize, data);
    }


    @Override
    public CourseTeachingClassForumTopicViewData getForumTopicViewDataById(String id) {
        CourseTeachingClassForumTopic forumtopic = courseTeachingClassForumTopicRepository.getByID(id);
        if (forumtopic == null) return null;

        UserInfoViewData userInfoViewData = userService.getUserInfoViewDataById(forumtopic.getUserId());
        if (userInfoViewData == null) return null;

        List<CourseTeachingClassForumTopicFile> topicFileList = courseTeachingClassForumTopicFileRepository.getByForumTopicID(forumtopic.getId());
        if (topicFileList == null) return null;

        CourseTeachingClassForumTopicViewData data = new CourseTeachingClassForumTopicViewData();


        data.setForumtopic(forumtopic);


        data.setUserInfoViewData(userInfoViewData);
        data.setTopicFileList(topicFileList);

        return data;
    }

    @Override
    public void update(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                       String last_modified_date, MultipartFile[] files) {

        // 1.删除文件
        deleteFilesByForumTopicId(request, t_forum_topic_id);

        // 2.更改基本信息
        courseTeachingClassForumTopicRepository.update(t_forum_topic_id, t_user_id, title, content, last_modified_date);

        // 3.增加文件
        addFiles(request, t_forum_topic_id, files);

    }

    @Override
    public void update(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                       Date last_modified_date, MultipartFile[] files) {

        // 1.删除文件
        deleteFilesByForumTopicId(request, t_forum_topic_id);

        // 2.更改基本信息
        courseTeachingClassForumTopicRepository.update(t_forum_topic_id, t_user_id, title, content, last_modified_date);

        // 3.增加文件
        addFiles(request, t_forum_topic_id, files);
    }

}
