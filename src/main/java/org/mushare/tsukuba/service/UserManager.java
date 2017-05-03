package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.UserBean;

public interface UserManager {

    public static final String UserTypeEmail = "email";
    public static final String UserTypeFacebook = "facebook";

    /**
     * Register by user mail.
     *
     * @param email
     * @param password
     * @param name
     * @return
     */
    boolean registerByEmail(String email, String password, String name);

    /**
     * Get user by email.
     *
     * @param email
     * @return
     */
    UserBean getByEmail(String email);

}
