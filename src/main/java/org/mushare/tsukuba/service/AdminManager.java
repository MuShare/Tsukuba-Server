package org.mushare.tsukuba.service;

import net.sf.json.JSONArray;

import javax.servlet.http.HttpSession;

public interface AdminManager {

    public static final String AdminConfigPath = "WEB-INF/admin.json";
    public static final String AdminFlag = "a2534027c005498ba2a002f01538e554";

    /**
     * Get all admin list.
     *
     * @param session
     * @return
     */
    JSONArray getAdmins(HttpSession session);

    /**
     * Admin login
     *
     * @param username
     * @param password
     * @return
     */
    boolean login(String username, String password, HttpSession session);

    /**
     * Admin logout
     *
     * @param session
     */
    void logout(HttpSession session);

    /**
     * Check admin session
     *
     * @param session
     * @return
     */
    String checkSession(HttpSession session);

    /**
     * @param username
     * @param password
     */
    boolean addAdmin(String username, String password, HttpSession session);

    /**
     * Modify admin's password
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    boolean modifyPassword(String username, String oldPassword, String newPassword, HttpSession session);

    /**
     * Remove admin
     *
     * @param username
     */
    boolean removeAdmin(String username, HttpSession session);

}