package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.User;

import java.util.List;

public interface DeviceDao extends BaseDao<Device> {

    /**
     * Get a device by identifier.
     * @param identifier
     * @return
     */
    Device getByIdentifier(String identifier);

    /**
     * Get a device by login token
     * @param token
     * @return
     */
    Device getByToken(String token);

    /**
     * Get a device by device token.
     *
     * @param deviceToken
     * @return
     */
    Device getByDeviceToken(String deviceToken);

    /**
     * Find all devices of a user.
     *
     * @param user
     * @return
     */
    List<Device> findByUser(User user);

}
