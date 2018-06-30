package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Chat;
import org.mushare.tsukuba.service.ChatManager;

import java.util.Date;


public class ChatBean {

    private String cid;
    private Date createAt;
    private String content;
    private int type;
    private int seq;
    private boolean direction;
    private RoomBean room;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public ChatBean(Chat chat) {
        fill(chat);
    }

    public ChatBean(Chat chat, boolean creator) {
        fill(chat);
        this.room = new RoomBean(chat.getRoom(), RoomBean.RoomBeanNew, creator);
    }

    private void fill(Chat chat) {
        this.cid = chat.getCid();
        this.createAt = new Date(chat.getCreateAt());
        this.type = chat.getType();
        this.seq = chat.getSeq();
        this.direction = chat.getDirection();
        switch (chat.getType()) {
            case ChatManager.ChatTypePlainText:
                this.content = chat.getContent();
                break;
            case ChatManager.ChatTypePicture:
                this.content = "/api/chat/picture/" + chat.getCid();
                break;
        }
    }

}
