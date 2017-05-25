package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.FavoriteDao;
import org.mushare.tsukuba.domain.Favorite;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteDaoHibernate extends BaseHibernateDaoSupport<Favorite> implements FavoriteDao{

    public FavoriteDaoHibernate(){
        super();
        setClass(Favorite.class);
    }

    public Favorite getByMessageForUser(Message message, User user) {
        String hql = "from Favorite where message = ? and user = ?";
        List<Favorite> favorites = (List<Favorite>) getHibernateTemplate().find(hql, message, user);
        if (favorites.size() == 0) {
            return null;
        }
        return favorites.get(0);
    }

    public int getFavoritesCountOfMessage(final Message message) {
        final String hql = "select count(*) from Favorite where message = ?";
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0, message);
                return (Long) query.uniqueResult();
            }
        }).intValue();
    }

    public List<Favorite> findByUser(User user) {
        String hql = "from Favorite where user = ?";
        return (List<Favorite>) getHibernateTemplate().find(hql, user);
    }

}
