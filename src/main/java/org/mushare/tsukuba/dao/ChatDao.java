package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Chat;
import org.mushare.tsukuba.domain.Room;

import java.util.List;

public interface ChatDao extends BaseDao<Chat> {

    /**
     * Find chats in a room by the minimum sequence number.
     *
     * @param room
     * @param seq
     * @return
     */
    List<Chat> findInRoomBySeq(Room room, Integer seq);

}
