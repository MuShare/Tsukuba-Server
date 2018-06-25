package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.QuqueDao;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.Queue;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueueDaoHibernate extends BaseHibernateDaoSupport<Queue> implements QuqueDao {

    public QueueDaoHibernate() {
        super();
        setClass(Queue.class);
    }

    public List<Queue> findByDevice(Device device) {
        String hql = "from Queue where device = ?";
        return (List<Queue>)getHibernateTemplate().find(hql, device);
    }

    public int deleteByDevice(Device device) {
        final String hql = "delete from Queue where device = ?";
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0, device);
                return query.executeUpdate();
            }
        }).intValue();
    }

}
