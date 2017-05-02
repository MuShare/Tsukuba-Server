package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.SelectionDao;
import org.mushare.tsukuba.domain.Selection;
import org.springframework.stereotype.Repository;

@Repository
public class SelectionDaoHibernate extends BaseHibernateDaoSupport<Selection> implements SelectionDao{
    public SelectionDaoHibernate(){
        super();
        setClass(Selection.class);
    }
}
