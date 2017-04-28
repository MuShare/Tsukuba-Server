package org.mushare.tsukuba.dao;

import org.mushare.common.hibernate.BaseDao;
import org.mushare.tsukuba.domain.User;

public interface UserDao extends BaseDao<User> {

    /**
     * Get a user by an indentifier with a type
     * @param identifier
     * @param type
     * @return
     */
    User getByIdentifierWithType(String identifier, String type);

}
