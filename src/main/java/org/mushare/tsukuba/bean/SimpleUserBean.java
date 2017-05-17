package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.User;

public class SimpleUserBean {

    private String uid;
    private String name;
    private String avatar;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public SimpleUserBean(User user) {
        this.uid = user.getUid();
        this.name = user.getName();
        this.avatar = user.getAvatar();
    }

}
