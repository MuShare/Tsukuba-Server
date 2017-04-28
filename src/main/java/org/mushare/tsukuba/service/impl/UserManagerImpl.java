package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.UserManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    @Transactional
    public boolean registerByEmail(String email, String password, String name) {
        // Find this user by email, if the email is existed, do not allow him to register.
        User user = userDao.getByIdentifierWithType(email, UserTypeEmail);
        if (user != null) {
            Debug.error("This email has been registerd.");
            return false;
        }
        user = new User();
        user.setCreateAt(System.currentTimeMillis());
        user.setType(UserTypeEmail);
        user.setIdentifier(email);
        user.setCredential(password);
        user.setName(name);
        user.setLevel(0);
        if (userDao.save(user) == null) {
            Debug.error("Error to save user.");
            return false;
        }
        return true;
    }

}
