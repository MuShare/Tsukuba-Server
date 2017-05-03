package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Option;
import org.mushare.tsukuba.domain.Selection;

import java.util.List;

public interface OptionDao extends BaseDao<Option> {
    /**
     * Get max revision number of Option entity.
     *
     * @return max revision
     */
    int getMaxRev();

    /**
     * Find actived options by revision.
     *
     * @param rev
     * @return
     */
    List<Option> findActivedByRev(int rev);

    /**
     * Get all options by selection.
     *
     * @param selection
     * @return
     */
    List<Option> findBySelection(Selection selection);

    /**
     * Return the count of options in the selection.
     *
     * @param selection
     * @return
     */
    int getCountBySelection(Selection selection);
}
