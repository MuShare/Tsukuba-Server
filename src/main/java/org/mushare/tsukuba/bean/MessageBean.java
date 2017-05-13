package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Message;

import java.util.Date;

public class MessageBean {

    private String mid;
    private Date createAt;
    private Date updateAt;
    private long seq;
    private String title;
    private int price;
    private String cover;
    private boolean enable;
    private String cid;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public MessageBean(Message message) {
        this.mid = message.getMid();
        this.createAt = new Date(message.getCreateAt());
        this.updateAt = new Date(message.getUpdateAt());
        this.seq = message.getSeq();
        this.title = message.getTitle();
        this.price = message.getPrice();
        this.cover = message.getCover() == null ? "/static/images/picture.png" : message.getCover().getPath();
        this.enable = message.getEnable();
        this.cid = message.getCategory().getCid();
    }

}
