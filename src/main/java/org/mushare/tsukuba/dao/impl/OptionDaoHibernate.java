package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.OptionDao;
import org.mushare.tsukuba.domain.Option;
import org.springframework.stereotype.Repository;

@Repository
public class OptionDaoHibernate extends BaseHibernateDaoSupport<Option> implements OptionDao{
    public OptionDaoHibernate(){
        super();
        setClass(Option.class);
    }

}
