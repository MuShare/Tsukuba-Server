package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;

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
    Result registerByEmail(String email, String password, String name);

    /**
     * Get user by email.
     *
     * @param email
     * @return
     */
    UserBean getByEmail(String email);

    /**
     * Get a user by facebook's access token. If this user is not existed, create a new one in database.
     * @param token
     * @return
     */
    UserBean getByFacebookAccessToken(String token);

    /**
     * User authentication by login token
     * @param token
     * @return
     */
    UserBean authByToken(String token);

    /**
     * Modify user name, contact and address.
     * These 3 params can be null, update an attribute only if it is not null.
     *
     * @param uid
     * @param name
     * @param contact
     * @param address
     * @return
     */
    Result modify(String uid, String name, String contact, String address);

    /**
     * Send a email with an url to user for modifying password.
     * @param uid
     * @return
     */
    boolean sendModifyPasswordMail(String uid);

    /**
     * Reset password, auth by session.
     * @param password
     * @param session
     * @return
     */
    boolean resetPassword(String password, HttpSession session);

}
