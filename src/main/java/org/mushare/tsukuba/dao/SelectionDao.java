package org.mushare.tsukuba.dao;


import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.domain.Selection;

import java.util.List;

public interface SelectionDao extends BaseDao<Selection> {
    /**
     * Get max revision number of Selection entity.
     *
     * @return max revision
     */
    int getMaxRev();

    /**
     * Find actived selections by revision.
     *
     * @param rev
     * @return
     */
    List<Selection> findActivedByRev(int rev);

    /**
     * Find selections by category.
     *
     * @param category
     * @return
     */
    List<Selection> findByCategory(Category category);

    /**
     * Return the count of selections in the category.
     *
     * @param category
     * @return
     */
    int getCountByCategory(Category category);
}

