package org.mushare.tsukuba.bean;

import org.mushare.tsukuba.domain.Device;

import java.util.Date;

public class DeviceBean {

    private String did;
    private Date createAt;
    private Date updateAt;
    private String identifier;
    private String deviceToken;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public DeviceBean(Device device) {
        this.did = device.getDid();
        this.createAt = new Date(device.getCreateAt());
        this.updateAt = new Date(device.getUpdateAt());
        this.identifier = device.getIdentifier();
        this.deviceToken = device.getDeviceToken();
    }

}
