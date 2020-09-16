package com.haobaoshui.course.service.mail.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.*;
import com.haobaoshui.course.model.user.UserInfoViewData;
import com.haobaoshui.course.repository.mail.MailBoxSendFileRepository;
import com.haobaoshui.course.repository.mail.MailBoxSendRepository;
import com.haobaoshui.course.service.mail.MailBoxSendService;
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
import java.util.List;


@Service
public class MailBoxSendServiceImpl implements MailBoxSendService {


    private final MailBoxSendRepository mailBoxSendRepository;
    private final MailBoxSendFileRepository mailBoxSendFileRepository;
    @Autowired
    UserService userService;

    @Autowired
    public MailBoxSendServiceImpl(MailBoxSendRepository mailBoxSendRepository,
                                  MailBoxSendFileRepository mailBoxSendFileRepository) {
        this.mailBoxSendRepository = mailBoxSendRepository;
        this.mailBoxSendFileRepository = mailBoxSendFileRepository;
    }


    @Override
    public MailBoxSendFile getFileByID(String id) {
        return mailBoxSendFileRepository.getByID(id);
    }

    private List<MailBoxFileName> addFiles(HttpServletRequest request, String t_mail_box_id, MultipartFile[] files) {

        List<MailBoxFileName> list = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String filename = file.getOriginalFilename();

            String prefix = filename.substring(filename.lastIndexOf(".") + 1);

            ServletContext sc = request.getSession().getServletContext();
            String dir = sc.getRealPath(RealPathConst.RealPath_MailBoxFile); // 设定文件保存的目录

            prefix = prefix.toLowerCase();

            String idfilename = GUID.getGUID();

            String localfilename = dir + RealPathConst.RealPath_PathSeparator + idfilename + "." + prefix;
            String hreffilename = idfilename + "." + prefix;


            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                mailBoxSendFileRepository.add(t_mail_box_id, filename, hreffilename);

                MailBoxFileName mfn = new MailBoxFileName();
                mfn.setOriginalFilename(filename);
                mfn.setIDFileName(hreffilename);
                mfn.setID0FileName(GUID.getGUID() + "." + prefix);
                list.add(mfn);


            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }

        return list;
    }

    // 增加
    @Override
    public List<MailBoxFileName> add(HttpServletRequest request, String t_user_id_from, String t_user_id_to,
                                     String subject, String content,
                                     MultipartFile[] files) {

        MailBoxState state = new MailBoxState();

        String t_mail_box_send_id = mailBoxSendRepository.add(t_user_id_from, t_user_id_to,
                state.getState(), subject, content);

        return addFiles(request, t_mail_box_send_id, files);

    }

    // 删除
    @Override
    public void deleteByID(HttpServletRequest request, String t_mail_box_send_id) {

        // 删除基本信息及其附件文件
        deleteBaseInfoByID(request, t_mail_box_send_id);

        // TODO：删除其他数据
    }

    // 删除基本信息及其附件文件
    private void deleteBaseInfoByID(HttpServletRequest request, String t_mail_box_send_id) {
        MailBoxSend plan = getByID(t_mail_box_send_id);
        if (plan == null) return;

        // 1.删除文件
        deleteFilesByMailBoxSendId(request, t_mail_box_send_id);

        // 2.删除基本信息
        mailBoxSendRepository.deleteById(t_mail_box_send_id);
    }

    /**
     * 根据t_user_id删除邮件
     */
    @Override
    public void deleteByUserId(HttpServletRequest request, String t_user_id) {
        deleteFromUserByUserId(request, t_user_id);
        deleteToUserByUserId(request, t_user_id);
    }

    /**
     * 根据t_user_id删除邮件
     */
    @Override
    public void deleteFromUserByUserId(HttpServletRequest request, String t_user_id) {
        List<MailBoxSend> listfrom = mailBoxSendRepository.getByUserIdFrom(t_user_id);
        if (listfrom != null) for (MailBoxSend s : listfrom) deleteByID(request, s.getId());


    }

    /**
     * 根据t_user_id删除邮件
     */
    @Override
    public void deleteToUserByUserId(HttpServletRequest request, String t_user_id) {


        List<MailBoxSend> listto = mailBoxSendRepository.getByUserIdTo(t_user_id);
        if (listto != null) for (MailBoxSend s : listto) deleteByID(request, s.getId());
    }

    // 删除文件
    private void deleteFilesByMailBoxSendId(HttpServletRequest request, String t_mail_box_send_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_MailBoxFile); // 设定文件保存的目录

        List<MailBoxSendFile> list = mailBoxSendFileRepository
                .getByMailBoxSendID(t_mail_box_send_id);
        if (list != null) for (MailBoxSendFile homeworkfile : list) {
            String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

            File localfile = new File(path);

            if (localfile.exists()) localfile.delete();

            mailBoxSendFileRepository.deleteById(homeworkfile.getId());

        }
    }


    @Override
    public MailBoxSend getByID(String id) {
        return mailBoxSendRepository.getByID(id);
    }

    @Override
    public MailBoxSendViewData getMailBoxSendViewDataByID(String id) {
        MailBoxSend mailBoxSend = mailBoxSendRepository.getByID(id);
        if (mailBoxSend == null) return null;
        MailBoxSendViewData mailBoxSendViewData = new MailBoxSendViewData();
        mailBoxSendViewData.setSend(mailBoxSend);

        UserInfoViewData userInfoViewDataFrom = userService.getUserInfoViewDataById(mailBoxSend.getUserIdFrom());
        UserInfoViewData userInfoViewDataTo = userService.getUserInfoViewDataById(mailBoxSend.getUserIdTo());
        mailBoxSendViewData.setUserFrom(userInfoViewDataFrom);
        mailBoxSendViewData.setUserTo(userInfoViewDataTo);
        mailBoxSendViewData.setSendfile(mailBoxSendFileRepository.getByMailBoxSendID(mailBoxSend.getId()));


        return mailBoxSendViewData;
    }

    @Override
    public Page<MailBoxSendViewData> getPage(String t_user_id_from, int pageNo, int pageSize) {

        Page<MailBoxSend> mailBoxSendPage = mailBoxSendRepository.getPage(t_user_id_from, pageNo, pageSize);
        if (mailBoxSendPage == null) return null;
        List<MailBoxSendViewData> data = new ArrayList<>();

        for (MailBoxSend mailBoxSend : mailBoxSendPage.getResult()) {
            MailBoxSendViewData mailBoxSendViewData = getMailBoxSendViewDataByID(mailBoxSend.getId());
            if (mailBoxSendViewData != null) data.add(mailBoxSendViewData);
        }

        return new Page<>(mailBoxSendPage.getStart(),
                mailBoxSendPage.getTotalCount(), pageSize, data);
    }


}
