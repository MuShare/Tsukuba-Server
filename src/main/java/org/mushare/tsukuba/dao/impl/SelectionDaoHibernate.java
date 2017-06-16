package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.SelectionDao;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.domain.Selection;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SelectionDaoHibernate extends BaseHibernateDaoSupport<Selection> implements SelectionDao {
    public SelectionDaoHibernate() {
        super();
        setClass(Selection.class);
    }

    public int getMaxRev() {
        final String hql = "select max(rev) from Selection";
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                Object result = query.uniqueResult();
                if (result == null) {
                    return 0;
                }
                return (Integer) result;
            }
        });
    }

    public List<Selection> findActivedByRev(int rev) {
        String hql = "from Selection where active = true and rev > ? order by priority";
        return (List<Selection>) getHibernateTemplate().find(hql, rev);
    }

    public List<Selection> findByCategory(Category category) {
        String hql = "from Selection where category = ? order by createAt desc";
        return (List<Selection>) getHibernateTemplate().find(hql, category);
    }

    public int getCountByCategory(final Category category) {
        final String hql = "select count(*) from Selection where category = ?";
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0, category);
                return (Long) query.uniqueResult();
            }
        }).intValue();
    }
}
