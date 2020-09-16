package com.haobaoshui.course.service.mail.impl;

import com.haobaoshui.course.configure.RealPathConst;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.*;
import com.haobaoshui.course.model.user.UserInfoViewData;
import com.haobaoshui.course.repository.mail.MailBoxReceivedFileRepository;
import com.haobaoshui.course.repository.mail.MailBoxReceivedRepository;
import com.haobaoshui.course.service.mail.MailBoxReceivedService;
import com.haobaoshui.course.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@Service
public class MailBoxReceivedServiceImpl implements MailBoxReceivedService {


    private final MailBoxReceivedRepository mailBoxReceivedRepository;
    private final MailBoxReceivedFileRepository mailBoxReceivedFileRepository;
    @Autowired
    UserService userService;

    @Autowired
    public MailBoxReceivedServiceImpl(MailBoxReceivedRepository mailBoxReceivedRepository,
                                      MailBoxReceivedFileRepository mailBoxReceivedFileRepository) {
        this.mailBoxReceivedRepository = mailBoxReceivedRepository;
        this.mailBoxReceivedFileRepository = mailBoxReceivedFileRepository;
    }

    @Override
    public MailBoxReceivedFile getFileByID(String id) {
        return mailBoxReceivedFileRepository.getByID(id);
    }

    private void addFiles(HttpServletRequest request, String t_mail_box_id, List<MailBoxFileName> list) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_MailBoxFile); // 设定文件保存的目录

        for (MailBoxFileName mailBoxFileName : list) {

            File srcFile = new File(dir + RealPathConst.RealPath_PathSeparator + mailBoxFileName.getIDFileName());
            File destFile = new File(dir + RealPathConst.RealPath_PathSeparator + mailBoxFileName.getID0FileName());
            try {

                Files.copy(srcFile.toPath(), destFile.toPath());
                mailBoxReceivedFileRepository.add(t_mail_box_id, mailBoxFileName.getOriginalFilename(), mailBoxFileName.getID0FileName());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // 增加
    @Override
    public void add(HttpServletRequest request, String t_user_id_from, String t_user_id_to,
                    String subject, String content,
                    List<MailBoxFileName> list) {

        MailBoxState state = new MailBoxState();

        String t_mail_box_send_id = mailBoxReceivedRepository.add(t_user_id_from, t_user_id_to,
                state.getState(), subject, content);

        addFiles(request, t_mail_box_send_id, list);

    }

    // 删除
    @Override
    public void deleteByID(HttpServletRequest request, String t_mail_box_send_id) {

        // 删除基本信息及其附件文件
        deleteReceivedByID(request, t_mail_box_send_id);

        // TODO：删除其他数据
    }

    // 删除基本信息及其附件文件
    private void deleteReceivedByID(HttpServletRequest request, String t_mail_box_send_id) {
        MailBoxReceived plan = getByID(t_mail_box_send_id);
        if (plan == null) return;

        // 1.删除文件
        deleteFilesByMailBoxReceivedId(request, t_mail_box_send_id);

        // 2.删除基本信息
        mailBoxReceivedRepository.deleteById(t_mail_box_send_id);
    }

    // 删除文件
    private void deleteFilesByMailBoxReceivedId(HttpServletRequest request, String t_mail_box_Received_id) {
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(RealPathConst.RealPath_MailBoxFile); // 设定文件保存的目录

        List<MailBoxReceivedFile> list = mailBoxReceivedFileRepository
                .getByMailBoxReceivedID(t_mail_box_Received_id);
        if (list != null) for (MailBoxReceivedFile homeworkfile : list) {
            String path = dir + RealPathConst.RealPath_PathSeparator + homeworkfile.getFilepath();

            File localfile = new File(path);

            if (localfile.exists()) localfile.delete();

            mailBoxReceivedFileRepository.deleteById(homeworkfile.getId());

        }
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
        List<MailBoxReceived> listfrom = mailBoxReceivedRepository.getByUserIdFrom(t_user_id);
        if (listfrom != null) for (MailBoxReceived s : listfrom) deleteByID(request, s.getId());


    }

    /**
     * 根据t_user_id删除邮件
     */
    @Override
    public void deleteToUserByUserId(HttpServletRequest request, String t_user_id) {


        List<MailBoxReceived> listto = mailBoxReceivedRepository.getByUserIdTo(t_user_id);
        if (listto != null) for (MailBoxReceived s : listto) deleteByID(request, s.getId());
    }


    @Override
    public void setRead(String t_mail_box_Received_id) {
        MailBoxState state = new MailBoxState();
        state.setRead();
        mailBoxReceivedRepository.update(t_mail_box_Received_id, state.getState());
    }


    @Override
    public MailBoxReceived getByID(String id) {
        return mailBoxReceivedRepository.getByID(id);
    }

    @Override
    public MailBoxReceivedViewData getMailBoxViewDataByID(String id) {
        MailBoxReceived mailBoxReceived = mailBoxReceivedRepository.getByID(id);
        if (mailBoxReceived == null) return null;
        MailBoxReceivedViewData mailBoxReceivedViewData = new MailBoxReceivedViewData();
        mailBoxReceivedViewData.setReceived(mailBoxReceived);

        UserInfoViewData userInfoViewDataFrom = userService.getUserInfoViewDataById(mailBoxReceived.getUserIdFrom());
        UserInfoViewData userInfoViewDataTo = userService.getUserInfoViewDataById(mailBoxReceived.getUserIdTo());
        mailBoxReceivedViewData.setUserFrom(userInfoViewDataFrom);
        mailBoxReceivedViewData.setUserTo(userInfoViewDataTo);
        mailBoxReceivedViewData.setReceivedfile(mailBoxReceivedFileRepository.getByMailBoxReceivedID(mailBoxReceived.getId()));


        return mailBoxReceivedViewData;
    }

    @Override
    public Page<MailBoxReceivedViewData> getPage(String t_user_id_to, int pageNo, int pageSize) {
        Page<MailBoxReceived> mailBoxReceivedPage = mailBoxReceivedRepository.getPage(t_user_id_to, pageNo, pageSize);
        if (mailBoxReceivedPage == null) return null;
        List<MailBoxReceivedViewData> data = new ArrayList<>();

        for (MailBoxReceived mailBoxReceived : mailBoxReceivedPage.getResult()) {
            MailBoxReceivedViewData mailBoxReceivedViewData = getMailBoxViewDataByID(mailBoxReceived.getId());
            if (mailBoxReceivedViewData != null) data.add(mailBoxReceivedViewData);
        }

        return new Page<>(mailBoxReceivedPage.getStart(),
                mailBoxReceivedPage.getTotalCount(), pageSize, data);

    }

    @Override
    public Page<MailBoxReceivedViewData> getPageNotRead(String t_user_id_to, int pageNo, int pageSize) {
        Page<MailBoxReceived> mailBoxReceivedPage = mailBoxReceivedRepository.getPageNotRead(t_user_id_to, pageNo, pageSize);
        if (mailBoxReceivedPage == null) return null;
        List<MailBoxReceivedViewData> data = new ArrayList<>();

        for (MailBoxReceived mailBoxReceived : mailBoxReceivedPage.getResult()) {
            MailBoxReceivedViewData mailBoxReceivedViewData = getMailBoxViewDataByID(mailBoxReceived.getId());
            if (mailBoxReceivedViewData != null) data.add(mailBoxReceivedViewData);
        }

        return new Page<>(mailBoxReceivedPage.getStart(),
                mailBoxReceivedPage.getTotalCount(), pageSize, data);
    }

    @Override
    public boolean hasMailNotReaded(String t_user_id_to) {
        return mailBoxReceivedRepository.getCountNotRead(t_user_id_to) > 0;
    }


}
