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
     * Get all selections in the category of cid.
     *
     * @param cid
     * @return
     */
    List<Selection> findAll(String cid, String orderby, boolean desc);
}

