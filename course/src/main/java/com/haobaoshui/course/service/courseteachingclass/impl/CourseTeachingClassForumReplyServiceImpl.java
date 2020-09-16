package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReply;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReplyFile;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumReplyViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassForumTopic;
import com.haobaoshui.course.model.user.UserInfoViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumReplyFileRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumReplyRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassForumTopicRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassForumReplyService;
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
public class CourseTeachingClassForumReplyServiceImpl implements CourseTeachingClassForumReplyService {
    private final CourseTeachingClassForumReplyRepository courseTeachingClassForumReplyRepository;
    private final CourseTeachingClassForumTopicRepository courseTeachingClassForumTopicRepository;
    private final CourseTeachingClassForumReplyFileRepository courseTeachingClassForumReplyFileRepository;

    @Autowired
    UserService userService;


    @Autowired
    public CourseTeachingClassForumReplyServiceImpl(CourseTeachingClassForumReplyRepository courseTeachingClassForumReplyRepository,
                                                    CourseTeachingClassForumTopicRepository courseTeachingClassForumTopicRepository,
                                                    CourseTeachingClassForumReplyFileRepository courseTeachingClassForumReplyFileRepository) {
        this.courseTeachingClassForumReplyRepository = courseTeachingClassForumReplyRepository;
        this.courseTeachingClassForumTopicRepository = courseTeachingClassForumTopicRepository;
        this.courseTeachingClassForumReplyFileRepository = courseTeachingClassForumReplyFileRepository;
    }


    private void addFiles(HttpServletRequest request, String t_forum_reply_id, MultipartFile[] files) {

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String filename = file.getOriginalFilename();

            String prefix = filename.substring(filename.lastIndexOf(".") + 1);

            ServletContext sc = request.getSession().getServletContext();
            String dir = sc.getRealPath(RealPathConst.RealPath_ForumReplyFile); // 设定文件保存的目录

            prefix = prefix.toLowerCase();

            String idfilename = GUID.getGUID();

            String localfilename = dir + RealPathConst.RealPath_PathSeparator + idfilename + "." + prefix;
            String hreffilename = idfilename + "." + prefix;

            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                courseTeachingClassForumReplyFileRepository.add(t_forum_reply_id, filename, hreffilename);

            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

    // 删除文件
    private void deleteFilesByForumReplyId(HttpServletRequest request, String t_forum_reply_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_ForumReplyFile); // 设定文件保存的目录

        List<CourseTeachingClassForumReplyFile> list = courseTeachingClassForumReplyFileRepository.getByForumReplyID(t_forum_reply_id);
        if (list != null) for (CourseTeachingClassForumReplyFile homeworkfile : list) {
            String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

            File localfile = new File(path);

            if (localfile.exists()) localfile.delete();

            courseTeachingClassForumReplyFileRepository.deleteById(homeworkfile.getId());

        }
    }

    @Override
    public void deleteByForumTopicId(HttpServletRequest request, String t_forum_topic_id) {
        List<CourseTeachingClassForumReply> list = courseTeachingClassForumReplyRepository.getByForumTopicID(t_forum_topic_id);
        for (CourseTeachingClassForumReply f : list) deleteById(request, f.getId());
    }

    @Override
    public void deleteById(HttpServletRequest request, String t_forum_reply_id) {
        // 1.删除文件
        deleteFilesByForumReplyId(request, t_forum_reply_id);

        courseTeachingClassForumReplyRepository.deleteById(t_forum_reply_id);
    }

    /**
     * 根据t_user_id删除
     */
    @Override
    public void deleteByUserId(HttpServletRequest request, String t_user_id) {
        List<CourseTeachingClassForumReply> list = courseTeachingClassForumReplyRepository.getByUserID(t_user_id);
        if (list == null) return;
        for (CourseTeachingClassForumReply r : list) deleteById(request, r.getId());
    }

    /**
     * 根据t_user_id和t_course_teaching_class_id删除
     */
    @Override
    public void deleteByCourseTeachingClassIDAndUserID(HttpServletRequest request, String t_course_teaching_class_id, String t_user_id) {
        List<CourseTeachingClassForumTopic> list = courseTeachingClassForumTopicRepository.getByCourseTeachingClassIDAndUserID(t_course_teaching_class_id, t_user_id);
        if (list == null) return;
        for (CourseTeachingClassForumTopic t : list) {
            List<CourseTeachingClassForumReply> listreply = courseTeachingClassForumReplyRepository.getByCourseTeachingClassIdAndUserID(t.getId(), t_user_id);
            if (list == null) return;
            for (CourseTeachingClassForumReply r : listreply) deleteById(request, r.getId());
        }
    }

    @Override
    public String add(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                      String created_date, MultipartFile[] files) {
        String t_forum_reply_id = courseTeachingClassForumReplyRepository.add(t_forum_topic_id, t_user_id, title, content, created_date, created_date);

        addFiles(request, t_forum_reply_id, files);
        return t_forum_reply_id;
    }

    @Override
    public String add(HttpServletRequest request, String t_forum_topic_id, String t_user_id, String title, String content,
                      Date created_date, MultipartFile[] files) {
        String t_forum_reply_id = courseTeachingClassForumReplyRepository.add(t_forum_topic_id, t_user_id, title, content, created_date, created_date);
        addFiles(request, t_forum_reply_id, files);
        return t_forum_reply_id;
    }

    @Override
    public CourseTeachingClassForumReplyFile getFileByID(String id) {
        return courseTeachingClassForumReplyFileRepository.getByID(id);
    }


    /**
     * 增加访问次数
     */
    @Override
    public void IncViewCount(String t_forum_topic_id) {
        courseTeachingClassForumTopicRepository.incViewCount(t_forum_topic_id);
    }

    @Override
    public void update(HttpServletRequest request, String t_forum_reply_id, String t_user_id, String title, String content,
                       Date last_modified_date, MultipartFile[] files) {

        // 1.删除文件
        deleteFilesByForumReplyId(request, t_forum_reply_id);

        // 2.更改基本信息
        courseTeachingClassForumReplyRepository.update(t_forum_reply_id, t_user_id, title, content, last_modified_date);

        // 3.增加文件
        addFiles(request, t_forum_reply_id, files);

    }


    @Override
    public CourseTeachingClassForumReplyViewData getForumReplyViewDataByID(String id) {

        CourseTeachingClassForumReply forumreply = courseTeachingClassForumReplyRepository.getByID(id);
        if (forumreply == null) return null;

        UserInfoViewData userInfoViewData = userService.getUserInfoViewDataById(forumreply.getUserId());
        if (userInfoViewData == null) return null;

        List<CourseTeachingClassForumReplyFile> replyFileList = courseTeachingClassForumReplyFileRepository.getByForumReplyID(forumreply.getId());
        if (replyFileList == null) return null;

        CourseTeachingClassForumReplyViewData data = new CourseTeachingClassForumReplyViewData();
        data.setForumreply(forumreply);
        data.setUserInfoViewData(userInfoViewData);
        data.setReplyFileList(replyFileList);

        return data;
    }


    @Override
    public Page<CourseTeachingClassForumReplyViewData> getPage(String t_course_teaching_class_forum_topic_id, int pageNo, int pageSize) {

        //增加浏览次数
        IncViewCount(t_course_teaching_class_forum_topic_id);

        Page<CourseTeachingClassForumReply> courseTeachingClassForumReplyPage = courseTeachingClassForumReplyRepository.getPage(t_course_teaching_class_forum_topic_id, pageNo, pageSize);


        if (courseTeachingClassForumReplyPage == null) return null;
        List<CourseTeachingClassForumReplyViewData> data = new ArrayList<>();

        for (CourseTeachingClassForumReply courseTeachingClassHomeworkReply : courseTeachingClassForumReplyPage.getResult()) {
            CourseTeachingClassForumReplyViewData courseTeachingClassForumReplyViewData = getForumReplyViewDataByID(courseTeachingClassHomeworkReply.getId());
            if (courseTeachingClassForumReplyViewData != null) data.add(courseTeachingClassForumReplyViewData);
        }

        return new Page<>(courseTeachingClassForumReplyPage.getStart(),
                courseTeachingClassForumReplyPage.getTotalCount(), pageSize, data);

    }

}
