package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Room;
import org.mushare.tsukuba.domain.User;

public interface RoomDao extends BaseDao<Room> {

    /**
     * Get a room by the sender and receiver.
     *
     * @param sender
     * @param receiver
     * @return
     */
    Room getBySenderAndReceiver(User sender, User receiver);

}
