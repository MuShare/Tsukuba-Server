package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.Queue;

import java.util.List;

public interface QuqueDao extends BaseDao<Queue> {

    /**
     *
     * @param device
     * @return
     */
    List<Queue> findByDevice(Device device);

    /**
     *
     * @param device
     */
    int deleteByDevice(Device device);

}
