package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Option;

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
     * Get all options in the selection of sid.
     *
     * @param sid
     * @return
     */
    List<Option> findAll(String sid, String orderby, boolean desc);
}
