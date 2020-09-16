package com.haobaoshui.course.model.user;

import java.util.List;

public class UserInfoViewData {


    private User user;
    private UserBasicInfo userbasicinfo;
    private List<UserContactInfoViewData> userContactInfoViewDataList;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBasicInfo getUserbasicinfo() {
        return userbasicinfo;
    }

    public void setUserbasicinfo(UserBasicInfo userbasicinfo) {
        this.userbasicinfo = userbasicinfo;
    }

    public List<UserContactInfoViewData> getUserContactInfoViewDataList() {
        return userContactInfoViewDataList;
    }

    public void setUserContactInfoViewDataList(List<UserContactInfoViewData> userContactInfoViewDataList) {
        this.userContactInfoViewDataList = userContactInfoViewDataList;
    }
}
