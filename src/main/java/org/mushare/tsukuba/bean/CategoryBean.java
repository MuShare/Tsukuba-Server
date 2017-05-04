package org.mushare.tsukuba.bean;

import net.sf.json.JSONObject;
import org.directwebremoting.annotations.DataTransferObject;
import org.mushare.tsukuba.domain.Category;

import java.util.Date;

@DataTransferObject
public class CategoryBean {

    private String cid;
    private Date createAt;
    private boolean enable;
    private boolean active;
    private String identifier;
    private JSONObject name;
    private String icon;
    private int rev;
    private int priority;

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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isActive() {
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public CategoryBean(Category category) {
        this.cid = category.getCid();
        this.createAt = new Date(category.getCreateAt());
        this.enable = category.getEnable();
        this.active = category.getActive();
        this.identifier = category.getIdentifier();
        this.name = (category.getName() == null || category.getName().equals("")) ? null : JSONObject.fromObject(category.getName());
        this.icon = category.getIcon();
        this.rev = category.getRev() == null ? 0 : category.getRev();
        this.priority = category.getPriority();
    }

}
