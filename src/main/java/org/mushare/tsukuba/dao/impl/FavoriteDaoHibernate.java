package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.FavoriteDao;
import org.mushare.tsukuba.domain.Favorite;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteDaoHibernate extends BaseHibernateDaoSupport<Favorite> implements FavoriteDao{
    public FavoriteDaoHibernate(){
        super();
        setClass(Favorite.class);
    }
}
