package org.mushare.tsukuba.dao;


import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;

import java.util.List;

public interface MessageDao extends BaseDao<Message> {

    /**
     * Get the max sequence number.
     *
     * @return
     */
    long getMaxSeq();

    /**
     * Find messages by user.
     *
     * @param user
     * @param sell
     * @return
     */
    List<Message> findByUser(User user, boolean sell);

    /**
     * Get number of messages whose sequence number is lagre than the given sequence number.
     *
     * @param seq
     * @return
     */
    int getCountWithSeqLargerThan(long seq, boolean sell, Category category);

    /**
     * Find messages from sequence number with size in category.
     *
     * @param sell
     * @param category
     * @param offset
     * @param pageSize
     * @return
     */
    List<Message> findWithSellInCategoryByPage(boolean sell, Category category, int offset, int pageSize);

}
