package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.ChatDao;
import org.mushare.tsukuba.domain.Chat;
import org.mushare.tsukuba.domain.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDaoHibernate extends BaseHibernateDaoSupport<Chat> implements ChatDao {

    public ChatDaoHibernate() {
        super();
        setClass(Chat.class);
    }

    public List<Chat> findInRoomBySeq(Room room, Integer seq) {
        String hql = "from Chat where room = ? and seq > ? order by seq";
        return (List<Chat>) getHibernateTemplate().find(hql, room, seq);
    }

}
