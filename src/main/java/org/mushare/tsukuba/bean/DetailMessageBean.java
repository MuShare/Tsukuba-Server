package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Answer;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.Picture;
import org.mushare.tsukuba.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailMessageBean {

    private String mid;
    private Date createAt;
    private Date updateAt;
    private long seq;
    private String title;
    private int price;
    private String cover;
    private boolean enable;
    private String cid;
    private String introduction;
    private String author;
    private String avatar;
    private List<PictureBean> pictures;
    private List<String> options;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<PictureBean> getPictures() {
        return pictures;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPictures(List<PictureBean> pictures) {
        this.pictures = pictures;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public DetailMessageBean(Message message, List<Picture> pictures, List<Answer> answers) {
        this.mid = message.getMid();
        this.createAt = new Date(message.getCreateAt());
        this.updateAt = new Date(message.getUpdateAt());
        this.seq = message.getSeq();
        this.title = message.getTitle();
        this.price = message.getPrice();
        this.cover = message.getCover() == null ? "/static/images/picture.png" : message.getCover().getPath();
        this.enable = message.getEnable();
        this.introduction = message.getIntroduction();
        this.cid = message.getCategory().getCid();
        this.author = message.getUser().getName();
        this.avatar = message.getUser().getAvatar();
        this.pictures = new ArrayList<PictureBean>();
        for (Picture picture : pictures) {
            this.pictures.add(new PictureBean(picture));
        }
        this.options = new ArrayList<String>();
        for (Answer answer: answers) {
            this.options.add(answer.getOption().getOid());
        }
    }
}
