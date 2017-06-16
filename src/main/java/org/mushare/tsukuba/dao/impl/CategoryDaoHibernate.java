package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.CategoryDao;
import org.mushare.tsukuba.domain.Category;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoHibernate extends BaseHibernateDaoSupport<Category> implements CategoryDao {

    public CategoryDaoHibernate () {
        super();
        setClass(Category.class);
    }

    public int getMaxRev() {
        final String hql = "select max(rev) from Category";
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

    public List<Category> findActivedByRev(int rev) {
        String hql = "from Category where active = true and rev > ? order by priority";
        return (List<Category>) getHibernateTemplate().find(hql, rev);
    }

}
