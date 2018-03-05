package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Room;
import org.mushare.tsukuba.domain.User;

import java.util.List;

public interface RoomDao extends BaseDao<Room> {

    /**
     * Get a room by the sender and receiver.
     *
     * @param sender
     * @param receiver
     * @return
     */
    Room getBySenderAndReceiver(User sender, User receiver);

    /**
     * Find rooms by rids.
     *
     * @param rids
     * @return
     */
    List<Room> findByRids(List<String> rids);

    /**
     * Find rooms by sender or receiver.
     *
     * @param user
     * @return
     */
    List<Room> findBySenderOrReceiver(User user);

}
