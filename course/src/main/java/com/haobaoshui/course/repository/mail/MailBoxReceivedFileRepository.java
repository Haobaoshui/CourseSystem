package com.haobaoshui.course.repository.mail;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxReceivedFile;

import java.util.List;


public interface MailBoxReceivedFileRepository {

    String add(MailBoxReceivedFile file);

    String add(String t_mail_box_received_id, String filename, String filepath);

    int deleteById(String id);

    int deleteByMailBoxId(String t_mail_box_received_id);

    int update(String id, String t_mail_box_send_id, String filename, String filepath);


    MailBoxReceivedFile getByID(String id);


    List<MailBoxReceivedFile> getByMailBoxReceivedID(String t_mail_box_received_id);


    long getCount(String t_mail_box_received_id);


    Page<MailBoxReceivedFile> getPage(String t_mail_box_received_id, int pageNo, int pageSize);
}
