package org.mushare.tsukuba.dao;


import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Message;

public interface MessageDao extends BaseDao<Message> {
    /**
     * Get the max sequence number.
     *
     * @return
     */
    long getMaxSeq();
}
