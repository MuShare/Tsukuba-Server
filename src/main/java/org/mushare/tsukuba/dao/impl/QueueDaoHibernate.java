package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.QuqueDao;
import org.mushare.tsukuba.domain.Queue;
import org.springframework.stereotype.Repository;

@Repository
public class QueueDaoHibernate extends BaseHibernateDaoSupport<Queue> implements QuqueDao {

    public QueueDaoHibernate() {
        super();
        setClass(Queue.class);
    }
}
