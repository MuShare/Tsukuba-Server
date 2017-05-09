package org.mushare.tsukuba.dao;


import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.Picture;

import java.util.List;

public interface PictureDao extends BaseDao<Picture> {

    /**
     * Find pictures by message.
     *
     * @param message
     * @return
     */
    List<Picture> findByMessage(Message message);

    /**
     * Find an oldest picture by message.
     *
     * @param message
     * @return
     */
    Picture getOldestByMessage(Message message);

}
