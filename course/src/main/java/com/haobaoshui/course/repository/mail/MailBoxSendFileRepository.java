package com.haobaoshui.course.repository.mail;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxSendFile;

import java.util.List;


public interface MailBoxSendFileRepository {


    String add(MailBoxSendFile file);

    String add(String t_mail_box_send_id, String filename, String filepath);

    int deleteById(String id);

    int deleteByMailBoxId(String t_mail_box_send_id);


    int update(String id, String t_mail_box_send_id, String filename, String filepath);

    MailBoxSendFile getByID(String id);


    List<MailBoxSendFile> getByMailBoxSendID(String t_mail_box_send_id);


    long getCount(String t_mail_box_send_id);


    Page<MailBoxSendFile> getPage(String t_mail_box_send_id, int pageNo, int pageSize);
}
