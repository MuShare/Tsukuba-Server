package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.RoomDao;
import org.mushare.tsukuba.domain.Room;
import org.mushare.tsukuba.domain.User;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDaoHibernate extends BaseHibernateDaoSupport<Room> implements RoomDao {

    public RoomDaoHibernate() {
        super();
        setClass(Room.class);
    }

    public Room getBySenderAndReceiver(User sender, User receiver) {
        String hql = "from Room where sender = ? and receiver = ?";
        List<Room> rooms = (List<Room>)getHibernateTemplate().find(hql, sender, receiver);
        if (rooms.size() == 0) {
            return null;
        }
        return rooms.get(0);
    }

    public List<Room> findByRids(final List<String> rids) {
        final String hql = "from Room where rid in(:rids)";
        return (List<Room>) getHibernateTemplate().execute(new HibernateCallback<List<Room>>() {
            public List<Room> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameterList("rids", rids);
                return query.list();
            }
        });
    }

    public List<Room> findBySenderOrReceiver(User user) {
        String hql = "from Room where sender = ? or receiver = ?";
        return (List<Room>)getHibernateTemplate().find(hql, user, user);
    }

}
