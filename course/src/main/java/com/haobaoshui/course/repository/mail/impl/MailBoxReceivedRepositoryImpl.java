package com.haobaoshui.course.repository.mail.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxReceived;
import com.haobaoshui.course.repository.mail.MailBoxReceivedRepository;
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
public class MailBoxReceivedRepositoryImpl implements MailBoxReceivedRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MailBoxReceivedRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* 增加用户 */
    @Override
    public String add(MailBoxReceived received) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_mail_box_received( id,t_user_id_from, t_user_id_to, state,subject,content,senddate,readdate) VALUES(?,?,?,?,?,?,?,?)", newid, received.getUserIdFrom(), received.getUserIdTo(), received.getState(),
                received.getSubject(), received.getContent(), new Date(), new Date());
        return newid;

    }

    @Override
    public String add(String t_user_id_from, String t_user_id_to, String state, String subject, String content) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_mail_box_received( id,t_user_id_from, t_user_id_to, state,subject,content,senddate,readdate) VALUES(?,?,?,?,?,?,?,?)", newid, t_user_id_from, t_user_id_to, state, subject, content, new Date(), new Date());
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_mail_box_received where id=?", id);


    }

    @Override
    public int deleteByUserIdFrom(String t_user_id_from) {
        return jdbcTemplate.update("DELETE from  t_mail_box_received where t_user_id_from=?", t_user_id_from);

    }

    @Override
    public int deleteByUserIdTo(String t_user_id_to) {
        return jdbcTemplate.update("DELETE from  t_mail_box_received where t_user_id_to=?", t_user_id_to);


    }

    @Override
    public int update(String id, String t_user_id_from, String t_user_id_to, String state, String subject, String content, Date senddate,
                      Date readdate) {
        if (id == null || t_user_id_from == null || t_user_id_to == null) return 0;
        return jdbcTemplate.update("update t_mail_box_received set t_user_id_from=?, t_user_id_to=?, state=?, subject=?,content=?,senddate=? ,readdate=? WHERE id=?", t_user_id_from, t_user_id_to, state, subject, content, senddate, readdate, id);


    }

    @Override
    public int update(String id, String state) {
        if (id == null) return 0;
        return jdbcTemplate.update("update t_mail_box_received set  state=?,readdate=? WHERE id=?", state, new Date(), id);

    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public MailBoxReceived getByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_mail_box_received WHERE id=?", new Object[]{id}, new MailBoxReceivedMapper());


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public List<MailBoxReceived> getByUserIdFrom(String t_user_id_from) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received WHERE t_user_id_from=?", new
                Object[]{t_user_id_from}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_received WHERE t_user_id_from=?", new Object[]{t_user_id_from}, new MailBoxReceivedMapper());


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public List<MailBoxReceived> getByUserIdTo(String t_user_id_to) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received WHERE t_user_id_to=?", new
                Object[]{t_user_id_to}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_received WHERE t_user_id_to=?", new Object[]{t_user_id_to}, new MailBoxReceivedMapper());


    }

    /**
     * 所有邮件数目
     */
    @Override
    public long getCount(String t_user_id_to) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received WHERE t_user_id_to=?", new Object[]{t_user_id_to},
                Long.class);
    }

    /**
     * 没有读取的邮件数目
     */
    @Override
    public long getCountNotRead(String t_user_id_to) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received WHERE t_user_id_to=? and state not like '%r%'", new Object[]{t_user_id_to},
                Long.class);
    }

    /**
     * 从指定发件人发送过来的邮件数目
     */
    @Override
    public long getCount(String t_user_id_from, String t_user_id_to) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received WHERE t_user_id_to=? and t_user_id_to=?", new Object[]{t_user_id_from, t_user_id_to},
                Long.class);
    }


    private List<MailBoxReceived> PageQuery(String t_user_to, int PageBegin, int PageSize) {
        if (getCount(t_user_to) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_received WHERE t_user_id_to=? order by senddate desc limit ?,?", new Object[]{t_user_to, PageBegin * PageSize, PageSize},
                new MailBoxReceivedMapper());

    }

    private List<MailBoxReceived> PageQueryNotRead(String t_user_to, int PageBegin, int PageSize) {

        if (getCountNotRead(t_user_to) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_received WHERE t_user_id_to=? and state not like '%r%' order by senddate desc limit ?,?", new Object[]{t_user_to, PageBegin * PageSize, PageSize},
                new MailBoxReceivedMapper());

    }

    @Override
    public Page<MailBoxReceived> getPage(String t_user_to, int pageNo, int pageSize) {
        long totalCount = getCount(t_user_to);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<MailBoxReceived> data = PageQuery(t_user_to, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    @Override
    public Page<MailBoxReceived> getPageNotRead(String t_user_to, int pageNo, int pageSize) {
        long totalCount = getCountNotRead(t_user_to);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<MailBoxReceived> data = PageQueryNotRead(t_user_to, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class MailBoxReceivedMapper implements RowMapper<MailBoxReceived> {

        @Override
        public MailBoxReceived mapRow(ResultSet rs, int rowNum) throws SQLException {
            MailBoxReceived mailBoxReceived = new MailBoxReceived();
            mailBoxReceived.setId(rs.getString("id"));
            mailBoxReceived.setUserIdFrom(rs.getString("t_user_id_from"));
            mailBoxReceived.setUserIdTo(rs.getString("t_user_id_to"));
            mailBoxReceived.setState(rs.getString("state"));
            mailBoxReceived.setSenddate(rs.getTimestamp("senddate"));
            mailBoxReceived.setReaddate(rs.getTimestamp("readdate"));
            mailBoxReceived.setSubject(rs.getString("subject"));
            mailBoxReceived.setContent(rs.getString("content"));

            return mailBoxReceived;
        }
    }
}
