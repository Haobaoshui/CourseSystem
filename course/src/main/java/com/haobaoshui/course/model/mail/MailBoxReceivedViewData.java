package com.haobaoshui.course.model.mail;

import com.haobaoshui.course.model.user.UserInfoViewData;

import java.util.List;


public class MailBoxReceivedViewData {

    private List<MailBoxReceivedFile> receivedfile;
    private MailBoxReceived received;
    private UserInfoViewData userFrom;
    private UserInfoViewData userTo;


    public List<MailBoxReceivedFile> getReceivedfile() {
        return receivedfile;
    }

    public void setReceivedfile(List<MailBoxReceivedFile> receivedfile) {
        this.receivedfile = receivedfile;
    }

    public MailBoxReceived getReceived() {
        return received;
    }

    public void setReceived(MailBoxReceived received) {
        this.received = received;
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
