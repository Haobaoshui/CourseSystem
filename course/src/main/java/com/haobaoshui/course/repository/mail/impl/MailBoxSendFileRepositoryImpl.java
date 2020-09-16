package com.haobaoshui.course.repository.mail.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxSendFile;
import com.haobaoshui.course.repository.mail.MailBoxSendFileRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MailBoxSendFileRepositoryImpl implements MailBoxSendFileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MailBoxSendFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(MailBoxSendFile file) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_mail_box_send_file( id,t_mail_box_send_id,filename,filepath) VALUES(?,?,?,?)", newid, file.getMailBoxSendId(), file.getFilename(), file.getFilepath());
        return newid;

    }

    @Override
    public String add(String t_mail_box_send_id, String filename, String filepath) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_mail_box_send_file( id,t_mail_box_send_id,filename,filepath) VALUES(?,?,?,?)", newid, t_mail_box_send_id, filename, filepath);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_mail_box_send_file where id=?", id);

    }

    @Override
    public int deleteByMailBoxId(String t_mail_box_send_id) {
        return jdbcTemplate.update("DELETE FROM t_mail_box_send_file WHERE t_mail_box_send_id=?", t_mail_box_send_id);

    }

    @Override
    public int update(String id, String t_mail_box_send_id, String filename, String filepath) {
        if (id == null || t_mail_box_send_id == null || filename == null || filepath == null) return 0;

        return jdbcTemplate.update("update t_mail_box_send_file set t_mail_box_send_id=?  filename=?,filepath=? WHERE id=?", t_mail_box_send_id, filename, filepath, id);

    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public MailBoxSendFile getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_mail_box_send_file WHERE id=?", new Object[]{id}, new MailBoxSendFileMapper());


    }

    /**
     * 根据课程-作业基本信息得到对应的文件列表
     */
    @Override
    public List<MailBoxSendFile> getByMailBoxSendID(String t_mail_box_send_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send_file WHERE t_mail_box_send_id=?", new
                Object[]{t_mail_box_send_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_send_file WHERE t_mail_box_send_id=?", new
                Object[]{t_mail_box_send_id}, new MailBoxSendFileMapper());


    }

    @Override
    public long getCount(String t_mail_box_send_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_send_file where t_mail_box_send_id=?", new Object[]{t_mail_box_send_id}, Long.class);
    }

    private List<MailBoxSendFile> PageQuery(String t_mail_box_send_id, int PageBegin, int PageSize) {

        PageBegin -= 1;
        if (PageBegin < 0) PageBegin = 0;


        List<MailBoxSendFile> list = new ArrayList<>();

        jdbcTemplate.query("SELECT id FROM t_mail_box_send_file WHERE t_mail_box_send_id=?   limit ?,?", new Object[]{t_mail_box_send_id, PageBegin * PageSize, PageSize},
                new RowCallbackHandler() {

                    @Override
                    public void processRow(ResultSet rs) throws SQLException {


                        MailBoxSendFile file = getByID(rs.getString("id"));


                        list.add(file);
                        // System.out.println(rs.getString("t_user_id"));
                    }

                });
        return list;
    }

    @Override
    public Page<MailBoxSendFile> getPage(String t_mail_box_send_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_mail_box_send_id);
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<MailBoxSendFile> data = PageQuery(t_mail_box_send_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class MailBoxSendFileMapper implements RowMapper<MailBoxSendFile> {

        @Override
        public MailBoxSendFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            MailBoxSendFile mailBoxSendFile = new MailBoxSendFile();
            mailBoxSendFile.setId(rs.getString("id"));
            mailBoxSendFile.setMailBoxSendId(rs.getString("t_mail_box_send_id"));
            mailBoxSendFile.setFilename(rs.getString("filename"));
            mailBoxSendFile.setFilepath(rs.getString("filepath"));
            return mailBoxSendFile;
        }
    }
}
