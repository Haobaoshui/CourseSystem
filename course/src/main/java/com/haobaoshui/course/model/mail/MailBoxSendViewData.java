package com.haobaoshui.course.model.mail;

import com.haobaoshui.course.model.user.UserInfoViewData;

import java.util.List;


public class MailBoxSendViewData {


    private List<MailBoxSendFile> sendfile;
    private MailBoxSend send;
    private UserInfoViewData userFrom;
    private UserInfoViewData userTo;

    public MailBoxSend getSend() {
        return send;
    }

    public void setSend(MailBoxSend send) {
        this.send = send;
    }

    public List<MailBoxSendFile> getSendfile() {
        return sendfile;
    }

    public void setSendfile(List<MailBoxSendFile> sendfile) {
        this.sendfile = sendfile;
    }

    public UserInfoViewData getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(UserInfoViewData userFrom) {
        this.userFrom = userFrom;
    }

    public UserInfoViewData getUserTo() {
        return userTo;
    }

    public void setUserTo(UserInfoViewData userTo) {
        this.userTo = userTo;
    }
}
