package org.mushare.tsukuba.service;


import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;

public interface MessageManager {
    /**
     * Create a message
     *
     * @param cid
     * @param uid
     * @param title
     * @param oids
     * @param price
     * @param sell
     * @return
     */
    String create(String cid, String uid, String title, String[] oids, int price, boolean sell);
}
