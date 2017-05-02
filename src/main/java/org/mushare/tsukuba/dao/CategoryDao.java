package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Category;

import java.util.List;

public interface CategoryDao extends BaseDao<Category> {

    /**
     * Get max revision number of Category entity.
     *
     * @return max revision
     */
    int getMaxRev();

    /**
     * Find actived categories by revision.
     *
     * @param rev
     * @return
     */
    List<Category> findActivedByRev(int rev);

}
