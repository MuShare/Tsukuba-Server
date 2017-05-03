package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.User;

public class UserBean {

    private String uid;
    private long createAt;
    private String name;
    private String avatar;
    private String type;
    private String identifier;
    private String credential;
    private String contact;
    private String address;
    private int level;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public UserBean(User user, boolean safe) {
        this.uid = user.getUid();
        this.createAt = user.getCreateAt();
        this.name = user.getName();
        this.avatar = user.getAvatar();
        this.contact = user.getContact();
        this.address = user.getAddress();
        this.level = user.getLevel();
        if (!safe) {
            this.type = user.getType();
            this.identifier = user.getIdentifier();
            this.credential = user.getCredential();
        }
    }

    public void safe() {
        this.type = null;
        this.identifier = null;
        this.credential = null;
    }

}
