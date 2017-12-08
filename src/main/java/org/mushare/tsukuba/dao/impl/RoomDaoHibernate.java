package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.RoomDao;
import org.mushare.tsukuba.domain.Room;
import org.mushare.tsukuba.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDaoHibernate extends BaseHibernateDaoSupport<Room> implements RoomDao {

    public RoomDaoHibernate() {
        super();
        setClass(Room.class);
    }

    public Room getBySenderAndReceiver(User sender, User receiver) {
        String hql = "from Room where sender = ? and receiver = ? or receiver = ? and sender = ?";
        List<Room> rooms = (List<Room>)getHibernateTemplate().find(hql, sender, receiver, receiver, sender);
        if (rooms.size() == 0) {
            return null;
        }
        return rooms.get(0);
    }
}
