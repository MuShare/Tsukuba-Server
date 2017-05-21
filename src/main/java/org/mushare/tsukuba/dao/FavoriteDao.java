package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.Favorite;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;

public interface FavoriteDao extends BaseDao<Favorite> {

    /**
     * Get a favorite object by message for a user.
     *
     * @param message
     * @param user
     * @return
     */
    Favorite getByMessageForUser(Message message, User user);

}
