package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.FavoriteDao;
import org.mushare.tsukuba.domain.Favorite;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;
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

}
