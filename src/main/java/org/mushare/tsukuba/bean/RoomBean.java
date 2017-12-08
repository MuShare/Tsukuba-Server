package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Room;

import java.util.Date;

public class RoomBean {

    private String rid;
    private Date createAt;
    private Date updateAt;
    private int chats;

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

    public RoomBean(Room room) {
        this.rid = room.getRid();
        this.createAt = new Date(room.getCreateAt());
        this.updateAt = new Date(room.getUpdateAt());
        this.chats = room.getChats();
    }

}
