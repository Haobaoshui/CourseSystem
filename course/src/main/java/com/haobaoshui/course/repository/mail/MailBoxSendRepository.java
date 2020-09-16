package com.haobaoshui.course.repository.mail;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxSend;

import java.util.Date;
import java.util.List;


public interface MailBoxSendRepository {

    String add(MailBoxSend send);

    String add(String t_user_id_from, String t_user_id_to, String state,
               String subject, String content);

    int deleteById(String id);

    int deleteByUserIdFrom(String t_user_id_from);

    int deleteByUserIdTo(String t_user_id_to);

    int update(String id, String t_user_id_from, String t_user_id_to, String state,
               String subject, String content, Date sendate);


    MailBoxSend getByID(String id);


    List<MailBoxSend> getByUserIdFrom(String t_user_id_from);


    List<MailBoxSend> getByUserIdTo(String t_user_id_to);


    long getCount(String t_user_id_from);

    long getCount(String t_user_id_from, String t_user_id_to);


    Page<MailBoxSend> getPage(String t_user_from,
                              int pageNo, int pageSize);
}
