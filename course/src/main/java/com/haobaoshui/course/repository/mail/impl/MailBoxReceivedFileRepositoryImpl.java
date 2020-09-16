package com.haobaoshui.course.repository.mail.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.mail.MailBoxReceivedFile;
import com.haobaoshui.course.repository.mail.MailBoxReceivedFileRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MailBoxReceivedFileRepositoryImpl implements MailBoxReceivedFileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MailBoxReceivedFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(MailBoxReceivedFile file) {

        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_mail_box_received_file( id,t_mail_box_received_id,filename,filepath) VALUES(?,?,?,?)", newid, file.getMailBoxReceivedId(), file.getFilename(), file.getFilepath());
        return newid;

    }

    @Override
    public String add(String t_mail_box_received_id, String filename, String filepath) {
        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_mail_box_received_file( id,t_mail_box_received_id,filename,filepath) VALUES(?,?,?,?)", newid, t_mail_box_received_id, filename, filepath);
        return newid;


    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_mail_box_received_file where id=?", id);

    }

    @Override
    public int deleteByMailBoxId(String t_mail_box_received_id) {
        return jdbcTemplate.update("DELETE FROM t_mail_box_received_file WHERE t_mail_box_received_id=?", t_mail_box_received_id);

    }

    @Override
    public int update(String id, String t_mail_box_send_id, String filename, String filepath) {
        if (id == null || t_mail_box_send_id == null || filename == null || filepath == null) return 0;

        return jdbcTemplate.update("update t_mail_box_received_file set t_mail_box_received_id=?  filename=?,filepath=? WHERE id=?", t_mail_box_send_id, filename, filepath, id);


    }

    /*
     * 根据用户ID得到用户
     */
    @Override
    public MailBoxReceivedFile getByID(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received_file WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_mail_box_received_file WHERE id=?", new Object[]{id}, new MailBoxReceivedFileMapper());


    }

    /**
     * 根据课程-作业基本信息得到对应的文件列表
     */
    @Override
    public List<MailBoxReceivedFile> getByMailBoxReceivedID(String t_mail_box_received_id) {

        if (getCount(t_mail_box_received_id) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_received_file WHERE t_mail_box_received_id=?", new Object[]{t_mail_box_received_id}, new MailBoxReceivedFileMapper());


    }

    @Override
    public long getCount(String t_mail_box_received_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) FROM t_mail_box_received_file WHERE t_mail_box_received_id=?", new Object[]{t_mail_box_received_id}, Long.class);
    }

    private List<MailBoxReceivedFile> PageQuery(String t_mail_box_received_id, int PageBegin, int PageSize) {

        if (getCount(t_mail_box_received_id) == 0) return null;

        return jdbcTemplate.query("SELECT * FROM t_mail_box_received_file WHERE t_mail_box_received_id=?   limit ?,?", new Object[]{t_mail_box_received_id, PageBegin * PageSize, PageSize},
                new MailBoxReceivedFileMapper());

    }

    @Override
    public Page<MailBoxReceivedFile> getPage(String t_mail_box_received_id, int pageNo, int pageSize) {
        long totalCount = getCount(t_mail_box_received_id);
        if (totalCount < 1) return new Page<>();

        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<MailBoxReceivedFile> data = PageQuery(t_mail_box_received_id, pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class MailBoxReceivedFileMapper implements RowMapper<MailBoxReceivedFile> {

        @Override
        public MailBoxReceivedFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            MailBoxReceivedFile mailBoxReceivedFile = new MailBoxReceivedFile();
            mailBoxReceivedFile.setId(rs.getString("id"));
            mailBoxReceivedFile.setMailBoxReceivedId(rs.getString("t_mail_box_received_id"));
            mailBoxReceivedFile.setFilename(rs.getString("filename"));
            mailBoxReceivedFile.setFilepath(rs.getString("filepath"));
            return mailBoxReceivedFile;
        }
    }
}
