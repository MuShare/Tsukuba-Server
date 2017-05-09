package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.MessageDao;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public List<Message> findByUser(User user, boolean sell) {
        String hql = "from Message where user = ? and sell = ? order by seq desc";
        return (List<Message>) getHibernateTemplate().find(hql, user, sell);
    }

    public int getCountWithSeqLargerThan(final long seq, final boolean sell, final Category category) {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                String hql = "select count(*) from Message where seq >= ? and sell = ?";
                if (category != null) {
                    hql += " and category = ?";
                }
                Query query = session.createQuery(hql);
                query.setParameter(0, seq);
                query.setParameter(1, sell);
                if (category != null) {
                    query.setParameter(2, category);
                }
                return (Long) query.uniqueResult();
            }
        }).intValue();
    }

    public List<Message> findWithSellInCategoryByPage(boolean sell, Category category, int offset, int pageSize) {
        List<Object> objects = new ArrayList<Object>();
        String hql = "from Message where enable = true and sell = ?";
        objects.add(sell);
        if (category != null) {
            hql += " and category = ?";
            objects.add(category);
        }
        hql += " order by seq desc";
        return findByPage(hql, objects, offset, pageSize);
    }

}
