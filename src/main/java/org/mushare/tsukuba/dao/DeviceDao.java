package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Device;

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

}
