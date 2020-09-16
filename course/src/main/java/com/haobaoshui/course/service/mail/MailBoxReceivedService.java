package com.haobaoshui.course.service.mail;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxFileName;
import com.haobaoshui.course.model.mail.MailBoxReceived;
import com.haobaoshui.course.model.mail.MailBoxReceivedFile;
import com.haobaoshui.course.model.mail.MailBoxReceivedViewData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface MailBoxReceivedService {


    MailBoxReceivedFile getFileByID(String id);


    // 增加
    void add(HttpServletRequest request, String t_user_id_from, String t_user_id_to,
             String subject, String content,
             List<MailBoxFileName> list);

    // 删除
    void deleteByID(HttpServletRequest request, String t_mail_box_send_id);


    /**
     * 根据t_user_id删除邮件
     */
    void deleteByUserId(HttpServletRequest request, String t_user_id);

    /**
     * 根据t_user_id删除邮件
     */
    void deleteFromUserByUserId(HttpServletRequest request, String t_user_id);

    /**
     * 根据t_user_id删除邮件
     */
    void deleteToUserByUserId(HttpServletRequest request, String t_user_id);


    void setRead(String t_mail_box_Received_id);


    MailBoxReceived getByID(String id);

    MailBoxReceivedViewData getMailBoxViewDataByID(String id);

    Page<MailBoxReceivedViewData> getPage(String t_user_id_to, int pageNo, int pageSize);

    Page<MailBoxReceivedViewData> getPageNotRead(String t_user_id_to, int pageNo, int pageSize);

    boolean hasMailNotReaded(String t_user_id_to);


}
