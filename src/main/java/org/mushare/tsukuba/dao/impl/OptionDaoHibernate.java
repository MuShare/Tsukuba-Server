package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.OptionDao;
import org.mushare.tsukuba.domain.Option;
import org.mushare.tsukuba.domain.Selection;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptionDaoHibernate extends BaseHibernateDaoSupport<Option> implements OptionDao {
    public OptionDaoHibernate() {
        super();
        setClass(Option.class);
    }

    public int getMaxRev() {
        final String hql = "select max(rev) from Option";
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

    public List<Option> findActivedByRev(int rev) {
        String hql = "from Option where active = true and rev > ?";
        return (List<Option>) getHibernateTemplate().find(hql, rev);
    }

    public List<Option> findBySelection(Selection selection) {
        String hql = "from Option where selection = ? order by createAt desc";
        return (List<Option>) getHibernateTemplate().find(hql, selection);
    }

    public int getCountBySelection(final Selection selection) {
        final String hql = "select count(*) from Option where selection = ?";
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0, selection);
                return (Long) query.uniqueResult();
            }
        }).intValue();
    }

    public List<Option> findInOids(final String[] oids) {
        final String hql = "from Option where oid in(:parameters)";
        return (List<Option>) getHibernateTemplate().execute(new HibernateCallback<List<Option>>() {
            public List<Option> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameterList("parameters", oids);
                return query.list();
            }
        });
    }

}
