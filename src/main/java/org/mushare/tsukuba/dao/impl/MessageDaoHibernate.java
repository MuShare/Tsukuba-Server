package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.MessageDao;
import org.mushare.tsukuba.domain.Message;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoHibernate extends BaseHibernateDaoSupport<Message> implements MessageDao {
    public MessageDaoHibernate(){
        super();
        setClass(Message.class);
    }
}
