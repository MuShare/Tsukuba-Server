package org.mushare.tsukuba.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsukuba_picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String pid;

    @Column(nullable = false)
    private Long createAt;

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "mid")
    private Message message;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Picture)) {
            return false;
        }
        Picture picture = (Picture)obj;
        return this.pid.equals(picture.getPid());
    }

}
