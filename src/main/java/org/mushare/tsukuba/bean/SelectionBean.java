package org.mushare.tsukuba.bean;

import net.sf.json.JSONObject;
import org.directwebremoting.annotations.DataTransferObject;
import org.mushare.tsukuba.domain.Selection;

import java.util.Date;

@DataTransferObject
public class SelectionBean {
    private String sid;

    private Date createAt;

    private Boolean enable;

    private Boolean active;

    private String identifier;

    private JSONObject name;

    private Integer rev;

    private String cid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public JSONObject getName() {
        return name;
    }

    public void setName(JSONObject name) {
        this.name = name;
    }

    public Integer getRev() {
        return rev;
    }

    public void setRev(Integer rev) {
        this.rev = rev;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public SelectionBean(Selection selection) {
        this.sid = selection.getSid();
        this.createAt = new Date(selection.getCreateAt());
        this.enable = selection.getEnable();
        this.active = selection.getActive();
        this.identifier = selection.getIdentifier();
        this.name = (selection.getName() == null || selection.getName().equals("")) ? null : JSONObject.fromObject(selection.getName());
        this.rev = selection.getRev() == null ? 0 : selection.getRev();
        this.cid = selection.getCategory().getCid();
    }
}
