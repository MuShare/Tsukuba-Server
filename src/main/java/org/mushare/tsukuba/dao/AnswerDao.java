package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Answer;
import org.mushare.tsukuba.domain.Message;

import java.util.List;

public interface AnswerDao extends BaseDao<Answer> {

    /**
     * Find answers by message
     *
     * @param message
     * @return
     */
    List<Answer> findByMessage(Message message);

    /**
     * Delete answers by message
     *
     * @param message
     */
    void deleteByMessage(Message message);

}
