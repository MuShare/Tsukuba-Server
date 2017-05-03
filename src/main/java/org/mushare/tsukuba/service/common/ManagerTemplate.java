package org.mushare.tsukuba.service.common;

import org.mushare.tsukuba.component.ConfigComponent;
import org.mushare.tsukuba.component.MailComponent;
import org.mushare.tsukuba.dao.*;
import org.mushare.tsukuba.service.AdminManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class ManagerTemplate {

    @Autowired
    protected ConfigComponent configComponent;

    @Autowired
    protected MailComponent mailComponent;

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected DeviceDao deviceDao;

    @Autowired
    protected VerificationDao verificationDao;

    @Autowired
    protected CategoryDao categoryDao;

    @Autowired
    protected SelectionDao selectionDao;

    @Autowired
    protected OptionDao optionDao;


    public boolean checkAdminSession(HttpSession session) {
        return session.getAttribute(AdminManager.AdminFlag) != null;
    }

}
