package com.haobaoshui.course.model.mail;


public class MailBoxSendFile {


    private String id;
    private String mailBoxSendId;
    private String filename;
    private String filepath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getMailBoxSendId() {
        return mailBoxSendId;
    }

    public void setMailBoxSendId(String mailBoxSendId) {
        this.mailBoxSendId = mailBoxSendId;
    }

}
