package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.VerificationDao;
import org.mushare.tsukuba.domain.Verification;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationDaoHibernate extends BaseHibernateDaoSupport<Verification> implements VerificationDao {

    public VerificationDaoHibernate() {
        super();
        setClass(Verification.class);
    }

}
