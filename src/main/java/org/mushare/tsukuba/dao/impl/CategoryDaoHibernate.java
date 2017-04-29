package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.CategoryDao;
import org.mushare.tsukuba.domain.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoHibernate extends BaseHibernateDaoSupport<Category> implements CategoryDao {

    public CategoryDaoHibernate () {
        super();
        setClass(Category.class);
    }

}
