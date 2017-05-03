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

    private Boolean enable;

    private Boolean active;

    private String identifier;

    private JSONObject name;

    private Integer rev;

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
    }
}
