package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.DeviceBean;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.DeviceManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceManagerImpl extends ManagerTemplate implements DeviceManager {

    @Transactional
    public String registerDevice(String identifier, String os, String version, String lan, String deviceToken, String ip, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find an user by this uid.");
            return null;
        }
        Device device = deviceDao.getByIdentifier(identifier);
        // If device info cannot be found, create a new device.
        if (device == null) {
            device = new Device();
            device.setCreateAt(System.currentTimeMillis());
            device.setUpdateAt(device.getCreateAt());
            device.setIdentifier(identifier);
            device.setOs(os);
            device.setVersion(version);
            device.setLan(lan);
            device.setDeviceToken(deviceToken);
            device.setIp(ip);
            device.setUser(user);
            device.setToken(UUID.randomUUID().toString());
            if (deviceDao.save(device) == null) {
                return null;
            }
        } else {
            // Device info found, update device info and create new access token.
            device.setUpdateAt(System.currentTimeMillis());
            device.setOs(os);
            device.setLan(lan);
            device.setDeviceToken(deviceToken);
            device.setIp(ip);
            device.setUser(user);
            device.setToken(UUID.randomUUID().toString());
            deviceDao.update(device);
        }
        return device.getToken();
    }

    public boolean isLegalDevice(String os) {
        return os.equals(iOSDevice) || os.equals(AndroidDevice);
    }

    @Transactional
    public boolean updateDeviceToken(String deviceToken, String token) {
        Device device = deviceDao.getByToken(token);
        if (device == null) {
            Debug.error("Cannot find a device by this token.");
            return false;
        }
        device.setDeviceToken(deviceToken);
        deviceDao.update(device);
        return true;
    }

    public DeviceBean authDevice(String token) {
        Device device = deviceDao.getByToken(token);
        if (device == null) {
            Debug.error("Cannot find a device by this token.");
            return null;
        }
        return new DeviceBean(device);
    }

    public List<DeviceBean> getDevicesByUid(String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find an user by this uid.");
            return null;
        }
        List<DeviceBean> deviceBeans = new ArrayList<>();
        for (Device device: deviceDao.findByUser(user)) {
            deviceBeans.add(new DeviceBean(device));
        }
        return deviceBeans;
    }

}
