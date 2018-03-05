package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Room;

import java.util.Date;

public class RoomBean {

    public final static int RoomBeanNew = 0;
    public final static int RoomBeanStatus = 1;

    private String rid;
    private Date createAt;
    private Date updateAt;
    private int chats;
    private Boolean creator;
    private String lastMessage;
    private SimpleUserBean sender;
    private SimpleUserBean receiver;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    public int getChats() {
        return chats;
    }

    public void setChats(int chats) {
        this.chats = chats;
    }

    public SimpleUserBean getSender() {
        return sender;
    }

    public void setSender(SimpleUserBean sender) {
        this.sender = sender;
    }

    public SimpleUserBean getReceiver() {
        return receiver;
    }

    public void setReceiver(SimpleUserBean receiver) {
        this.receiver = receiver;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static int getRoomBeanNew() {
        return RoomBeanNew;
    }

    public static int getRoomBeanStatus() {
        return RoomBeanStatus;
    }

    public Boolean getCreator() {
        return creator;
    }

    public void setCreator(Boolean creator) {
        this.creator = creator;
    }

    public RoomBean(Room room, int type, boolean creator) {
        this.rid = room.getRid();
        this.updateAt = new Date(room.getUpdateAt());
        this.chats = room.getChats();
        this.lastMessage = room.getLastMessage();
        this.creator = creator;
        if (type == RoomBeanNew) {
            this.createAt = new Date(room.getCreateAt());
            this.sender = new SimpleUserBean(room.getSender());
            this.receiver = new SimpleUserBean(room.getReceiver());
        }
    }

}
