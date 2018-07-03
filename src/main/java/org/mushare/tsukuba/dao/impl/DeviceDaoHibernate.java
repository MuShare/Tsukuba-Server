package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.DeviceDao;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceDaoHibernate extends BaseHibernateDaoSupport<Device> implements DeviceDao {

    public DeviceDaoHibernate() {
        super();
        setClass(Device.class);
    }

    public Device getByIdentifier(String identifier) {
        String hql = "from Device where identifier = ?";
        List<Device> devices = (List<Device>)getHibernateTemplate().find(hql, identifier);
        if (devices.size() == 0) {
            return null;
        }
        return devices.get(0);
    }

    public Device getByToken(String token) {
        String hql = "from Device where token =?";
        List<Device> devices = (List<Device>)getHibernateTemplate().find(hql, token);
        if (devices.size() == 0) {
            return null;
        }
        return devices.get(0);
    }

    public Device getByDeviceToken(String deviceToken) {
        String hql = "from Device where deviceToken =?";
        List<Device> devices = (List<Device>)getHibernateTemplate().find(hql, deviceToken);
        if (devices.size() == 0) {
            return null;
        }
        return devices.get(0);
    }

    public List<Device> findByUser(User user) {
        String hql = "from Device where user = ?";
        return (List<Device>)getHibernateTemplate().find(hql, user);
    }

}
