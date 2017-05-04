package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseDao;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.AnswerDao;
import org.mushare.tsukuba.domain.Answer;
import org.mushare.tsukuba.domain.Message;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDaoHibernate extends BaseHibernateDaoSupport<Answer> implements AnswerDao {

    public AnswerDaoHibernate() {
        super();
        setClass(Answer.class);
    }

    public List<Answer> findByMessage(Message message) {
        String hql = "from Answer where message = ?";
        return (List<Answer>) getHibernateTemplate().find(hql, message);
    }

    public void deleteByMessage(final Message message) {
        final String hql = "delete from Answer where message = ?";
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0, message);
                return query.executeUpdate();
            }
        });
    }

}
