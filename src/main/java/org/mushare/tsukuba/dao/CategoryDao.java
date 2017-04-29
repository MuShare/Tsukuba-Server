package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Category;

public interface CategoryDao extends BaseDao<Category> {

    /**
     * Get max revision number of Category entity.
     *
     * @return max revision
     */
    int getMaxRev();

}
