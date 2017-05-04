package org.mushare.tsukuba.bean;

import net.sf.json.JSONObject;
import org.directwebremoting.annotations.DataTransferObject;
import org.mushare.tsukuba.domain.Option;
import org.mushare.tsukuba.domain.Selection;

import java.util.Date;

@DataTransferObject
public class OptionBean {
    private String oid;

    private Date createAt;

    private boolean enable;

    private boolean active;

    private String identifier;

    private JSONObject name;

    private int rev;

    private int priority;

    private String sid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public OptionBean(Option option) {
        this.oid = option.getOid();
        this.createAt = new Date(option.getCreateAt());
        this.enable = option.getEnable();
        this.active = option.getActive();
        this.identifier = option.getIdentifier();
        this.name = (option.getName() == null || option.getName().equals("")) ? null : JSONObject.fromObject(option.getName());
        this.rev = option.getRev() == null ? 0 : option.getRev();
        this.sid = option.getSelection().getSid();
        this.priority = option.getPriority();
    }
}
