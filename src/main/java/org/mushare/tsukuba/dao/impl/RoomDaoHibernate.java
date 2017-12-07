package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.RoomDao;
import org.mushare.tsukuba.domain.Room;
import org.springframework.stereotype.Repository;

@Repository
public class RoomDaoHibernate extends BaseHibernateDaoSupport<Room> implements RoomDao {

    public RoomDaoHibernate() {
        super();
        setClass(Room.class);
    }

}
