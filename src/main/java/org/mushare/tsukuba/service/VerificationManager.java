package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.VerificationBean;

import javax.servlet.http.HttpSession;

public interface VerificationManager {

    public static final String VerificationFlag = "e5a1da83a01540288bfa1da9e4520000";

    /**
     * Validate this verification code
     * @param vid
     * @return
     */
    VerificationBean validate(String vid);

    /**
     * Get verification from session.
     * @param session
     * @return
     */
    VerificationBean getVerificationFromSession(HttpSession session);

}
