package org.mushare.tsukuba.bean;

import net.sf.json.JSONObject;
import org.directwebremoting.annotations.DataTransferObject;
import org.mushare.tsukuba.domain.Category;

import java.util.Date;

@DataTransferObject
public class CategoryBean {

    private String oid;
    private Date createAt;
    private boolean enable;
    private String identifier;
    private JSONObject name;
    private String icon;
    private int rev;

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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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

    public CategoryBean(Category category) {
        this.oid = category.getOid();
        this.createAt = new Date(category.getCreateAt());
        this.enable = category.getEnable();
        this.identifier = category.getIdentifier();
        this.name = (category.getName() == null || category.getName().equals("")) ? null : JSONObject.fromObject(category.getName());
        this.icon = category.getIcon();
        this.rev = category.getRev();
    }

}