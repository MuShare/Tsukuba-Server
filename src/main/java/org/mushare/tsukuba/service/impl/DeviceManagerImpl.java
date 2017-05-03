package org.mushare.tsukuba.service.impl;

import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.DeviceManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeviceManagerImpl extends ManagerTemplate implements DeviceManager {

    @Transactional
    public String registerDevice(String identifier, String os, String version, String lan, String deviceToken, String ip, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
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

}
