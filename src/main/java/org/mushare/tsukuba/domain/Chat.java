package org.mushare.tsukuba.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsukuba_chat")
public class Chat implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String cid;

    @Column(nullable = false)
    private Long createAt;

    @Column(nullable = false)
    private String content;

    @Column
    private Double pictureWidth;

    @Column
    private Double pictureHeight;

    @Column(nullable = false)
    private Boolean downloaded;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private Integer seq;

    @Column(nullable = false)
    private Boolean direction;

    @ManyToOne
    @JoinColumn(name = "rid", nullable = false)
    private Room room;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Boolean getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(Double pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    public Double getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(Double pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Boolean getDirection() {
        return direction;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
