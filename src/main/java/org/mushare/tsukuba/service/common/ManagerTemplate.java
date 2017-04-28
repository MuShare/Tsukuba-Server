package org.mushare.tsukuba.service.common;

import org.mushare.tsukuba.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerTemplate {

    @Autowired
    protected UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
