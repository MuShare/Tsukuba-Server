package org.mushare.tsukuba.bean;

import net.sf.json.JSONObject;
import org.directwebremoting.annotations.DataTransferObject;
import org.mushare.tsukuba.domain.Selection;

import java.util.Date;

@DataTransferObject
public class SelectionBean {
    private String sid;

    private Date createAt;

    private boolean enable;

    private boolean active;

    private String identifier;

    private JSONObject name;

    private int rev;

    private int priority;

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

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    public int getRev() {
        return rev;
    }

    public void setRev(int rev) {
        this.rev = rev;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
        this.priority = selection.getPriority();
        this.cid = selection.getCategory().getCid();
    }
}
