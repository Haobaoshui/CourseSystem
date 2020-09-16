package com.haobaoshui.course.service.mail;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxFileName;
import com.haobaoshui.course.model.mail.MailBoxSend;
import com.haobaoshui.course.model.mail.MailBoxSendFile;
import com.haobaoshui.course.model.mail.MailBoxSendViewData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface MailBoxSendService {


    MailBoxSendFile getFileByID(String id);


    // 增加
    List<MailBoxFileName> add(HttpServletRequest request, String t_user_id_from, String t_user_id_to,
                              String subject, String content,
                              MultipartFile[] files);

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


    MailBoxSend getByID(String id);

    MailBoxSendViewData getMailBoxSendViewDataByID(String id);

    Page<MailBoxSendViewData> getPage(String t_user_id_from, int pageNo, int pageSize);


}
