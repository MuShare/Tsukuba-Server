package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Message;

import java.util.Date;

public class MessageBean {

    private String mid;
    private Date createAt;
    private Date updateAt;
    private long seq;
    private String title;
    private String introduction;
    private int price;
    private String cover;
    private boolean sell;
    private boolean enable;
    private String cid;
    private String uid;

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

    public void setPrice(int price) {
        this.price = price;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public MessageBean(Message message) {
        this.mid = message.getMid();
        this.createAt = new Date(message.getCreateAt());
        this.updateAt = new Date(message.getUpdateAt());
        this.seq = message.getSeq();
        this.title = message.getTitle();
        this.introduction = message.getIntroduction();
        this.price = message.getPrice();
        this.sell = message.getSell();
        this.cover = message.getCover() == null ? "/static/images/picture.png" : message.getCover().getPath();
        this.enable = message.getEnable();
        this.cid = message.getCategory().getCid();
        this.uid = message.getUser().getUid();
    }

}
