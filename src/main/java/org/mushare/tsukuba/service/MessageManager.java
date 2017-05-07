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
     * @param introduction
     * @param oids
     * @param price
     * @param sell
     * @return
     */
    String create(String cid, String uid, String title, String introduction, String [] oids, int price, boolean sell);

    /**
     * Modify a message.
     *
     * @param mid
     * @param title
     * @param oids
     * @param introduction
     * @param price
     * @param uid
     * @return
     */
    Result modify(String mid, String title, String [] oids, String introduction, int price, String uid);

}
