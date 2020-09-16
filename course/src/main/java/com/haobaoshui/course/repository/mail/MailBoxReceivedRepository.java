package com.haobaoshui.course.repository.mail;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxReceived;

import java.util.Date;
import java.util.List;


public interface MailBoxReceivedRepository {


    String add(MailBoxReceived received);

    String add(String t_user_id_from, String t_user_id_to, String state, String subject, String content);

    int deleteById(String id);

    int deleteByUserIdFrom(String t_user_id_from);

    int deleteByUserIdTo(String t_user_id_to);

    int update(String id, String t_user_id_from, String t_user_id_to, String state, String subject, String content, Date senddate,
               Date readdate);

    int update(String id, String state);

    /*
     * 根据用户ID得到用户
     */
    MailBoxReceived getByID(String id);

    /*
     * 根据用户ID得到用户
     */
    List<MailBoxReceived> getByUserIdFrom(String t_user_id_from);

    /*
     * 根据用户ID得到用户
     */
    List<MailBoxReceived> getByUserIdTo(String t_user_id_to);


    /**
     * 所有邮件数目
     */
    long getCount(String t_user_id_to);

    /**
     * 没有读取的邮件数目
     */
    long getCountNotRead(String t_user_id_to);


    /**
     * 从指定发件人发送过来的邮件数目
     */
    long getCount(String t_user_id_from, String t_user_id_to);


    Page<MailBoxReceived> getPage(String t_user_to, int pageNo, int pageSize);

    Page<MailBoxReceived> getPageNotRead(String t_user_to, int pageNo, int pageSize);
}
