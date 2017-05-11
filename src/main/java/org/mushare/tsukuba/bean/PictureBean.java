package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Picture;

import java.util.Date;

public class PictureBean {

    private String pid;
    private Date createAt;
    private String path;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PictureBean(Picture picture) {
        this.pid = picture.getPid();
        this.createAt = new Date(picture.getCreateAt());
        this.path = picture.getPath();
    }

}
