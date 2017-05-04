package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.MessageDao;
import org.mushare.tsukuba.domain.Message;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoHibernate extends BaseHibernateDaoSupport<Message> implements MessageDao {
    public MessageDaoHibernate() {
        super();
        setClass(Message.class);
    }

    public long getMaxSeq() {
        final String hql = "select max(seq) from Message";
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                Object result = query.uniqueResult();
                if (result == null) {
                    return 0L;
                }
                return (Long) result;
            }
        });
    }
}
