package com.haobaoshui.course.repository.mail.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxSend;
import com.haobaoshui.course.repository.mail.MailBoxSendRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MailBoxSendRepositoryImpl implements MailBoxSendRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MailBoxSendRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加用户 */
    @Override
    public String add(MailBoxSend send) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_mail_box_send( id,t_user_id_from, t_user_id_to, state,subject,content,senddate) VALUES(?,?,?,?,?,?,?)", newid, send.getUserIdFrom(), send.getUserIdTo(), send.getState(), send.getSubject(), send.getContent(), new Date());
        return newid;

    }

    @Override
    public String add(String t_user_id_from, String t_user_id_to, String state,
                      String subject, String content) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_mail_box_send( id,t_user_id_from, t_user_id_to, state,subject,content,senddate) VALUES(?,?,?,?,?,?,?)", newid, t_user_id_from, t_user_id_to, state, subject,
                content, new Date());
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_mail_box_send where id=?", id);


    }

    @Override
    public int deleteByUserIdFrom(String t_user_id_from) {
        return jdbcTemplate.update("DELETE from  t_mail_box_send where t_user_id_from=?", t_user_id_from);

    }

    @Override
    public int deleteByUserIdTo(String t_user_id_to) {
        return jdbcTemplate.update("DELETE from  t_mail_box_send where t_user_id_to=?", t_user_id_to);


    }

    @Override
    public int update(String id, String t_user_id_from, String t_user_id_to, String state,
                      String subject, String content, Date sendate) {
        if (id == null || t_user_id_from == null || t_user_id_to == null) return 0;

        return jdbcTemplate.update("update t_mail_box_send set t_user_id_from=?, t_user_id_to=?, state=?, subject=?,content=?,senddate=?  WHERE id=?", t_user_id_from, t_user_id_to, state, subject,
                content, sendate, id);

    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public MailBoxSend getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_mail_box_send WHERE id=?", new Object[]{id}, new MailBoxSendMapper());


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public List<MailBoxSend> getByUserIdFrom(String t_user_id_from) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send WHERE t_user_id_from=?", new
                Object[]{t_user_id_from}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_send WHERE t_user_id_from=?", new Object[]{t_user_id_from}, new MailBoxSendMapper());


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public List<MailBoxSend> getByUserIdTo(String t_user_id_to) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send WHERE t_user_id_to=?", new
                Object[]{t_user_id_to}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_send WHERE t_user_id_to=?", new Object[]{t_user_id_to}, new MailBoxSendMapper());


    }

    @Override
    public long getCount(String t_user_id_from) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send WHERE t_user_id_from=?", Integer.class);


    }

    @Override
    public long getCount(String t_user_id_from, String t_user_id_to) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send WHERE t_user_id_from=? and t_user_id_to=?", Integer.class);


    }


    private List<MailBoxSend> PageQuery(String t_user_from,
                                        int PageBegin, int PageSize) {

        if (getCount(t_user_from) == 0) return null;


        return jdbcTemplate.query("SELECT * FROM t_mail_box_send WHERE t_user_id_from=? order by senddate desc limit ?,?",
                new Object[]{t_user_from, PageBegin * PageSize, PageSize},
                new MailBoxSendMapper());

    }

    @Override
    public Page<MailBoxSend> getPage(String t_user_from,
                                     int pageNo, int pageSize) {
        long totalCount = getCount(t_user_from);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<MailBoxSend> data = PageQuery(t_user_from, pageNo - 1,
                pageSize);


        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class MailBoxSendMapper implements RowMapper<MailBoxSend> {

        @Override
        public MailBoxSend mapRow(ResultSet rs, int rowNum) throws SQLException {
            MailBoxSend mailBoxSend = new MailBoxSend();
            mailBoxSend.setUserIdFrom(rs.getString("t_user_id_from"));
            mailBoxSend.setUserIdTo(rs.getString("t_user_id_to"));
            mailBoxSend.setState(rs.getString("state"));
            mailBoxSend.setSenddate(rs.getTimestamp("senddate"));
            mailBoxSend.setSubject(rs.getString("subject"));
            mailBoxSend.setContent(rs.getString("content"));

            return mailBoxSend;
        }
    }
}
