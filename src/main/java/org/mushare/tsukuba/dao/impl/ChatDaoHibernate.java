package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.ChatDao;
import org.mushare.tsukuba.domain.Chat;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDaoHibernate extends BaseHibernateDaoSupport<Chat> implements ChatDao {

    public ChatDaoHibernate() {
        super();
        setClass(Chat.class);
    }

}
