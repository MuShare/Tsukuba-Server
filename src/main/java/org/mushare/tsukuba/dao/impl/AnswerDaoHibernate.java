package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.AnswerDao;
import org.mushare.tsukuba.domain.Answer;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDaoHibernate extends BaseHibernateDaoSupport<Answer> implements AnswerDao {
  public AnswerDaoHibernate(){
      super();
      setClass(Answer.class);
  }
}
