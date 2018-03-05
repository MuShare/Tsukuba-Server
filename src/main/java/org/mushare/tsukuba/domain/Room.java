package org.mushare.tsukuba.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsukuba_room")
public class Room implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String rid;

    @Column(nullable = false)
    private Long createAt;

    @Column(nullable = false)
    private Long updateAt;

    @Column(nullable = false)
    private Integer chats;

    @Column(nullable = false)
    private String lastMessage;

    @ManyToOne
    @JoinColumn(name = "sender_uid", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_uid", nullable = false)
    private User receiver;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getChats() {
        return chats;
    }

    public void setChats(Integer chats) {
        this.chats = chats;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        Room room = (Room)obj;
        return this.rid.equals(room.getRid());
    }

}
