package org.mushare.tsukuba.service.common;

import org.mushare.tsukuba.component.ConfigComponent;
import org.mushare.tsukuba.component.MailComponent;
import org.mushare.tsukuba.dao.CategoryDao;
import org.mushare.tsukuba.dao.UserDao;
import org.mushare.tsukuba.service.AdminManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class ManagerTemplate {

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected CategoryDao categoryDao;

    @Autowired
    protected ConfigComponent configComponent;

    @Autowired
    protected MailComponent mailComponent;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public ConfigComponent getConfigComponent() {
        return configComponent;
    }

    public void setConfigComponent(ConfigComponent configComponent) {
        this.configComponent = configComponent;
    }

    public boolean checkAdminSession(HttpSession session) {
        return session.getAttribute(AdminManager.ADMIN_FLAG) != null;
    }

}
