package org.mushare.tsukuba.service;


import org.mushare.tsukuba.bean.DetailMessageBean;
import org.mushare.tsukuba.bean.MessageBean;
import org.mushare.tsukuba.service.common.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MessageManager {

    /**
     * Create a message.
     *
     * @param cid
     * @param uid
     * @param title
     * @param introduction
     * @param oids
     * @param price
     * @param sell
     * @return mid
     */
    String create(String cid, String uid, String title, String introduction, String[] oids, int price, boolean sell);

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
    Result modify(String mid, String title, String[] oids, String introduction, int price, String uid);

    /**
     * Test the message is belong to the user or not.
     *
     * @param mid
     * @param uid
     * @return
     */
    Result hasPrevilege(String mid, String uid);

    /**
     * Enable and disable message.
     *
     * @param mid
     * @param enable
     * @param uid
     * @return
     */
    Result enable(String mid, boolean enable, String uid);

    /**
     * Get message by mid, if uid is existed, get favorite status for this user.
     *
     * @param mid
     * @return
     */
    DetailMessageBean getDetail(String mid, String uid);

    /**
     * Get messages of a user by uid.
     *
     * @param uid
     * @param sell
     * @return
     */
    List<MessageBean> getMessagesByUid(String uid, boolean sell);

    /**
     * Search limted enable messages from a sequence number.
     *
     * @param cid
     * @param seq  sequence number.
     * @param size page size.
     * @param sell
     * @return
     */
    List<MessageBean> searchEnableMessages(long seq, boolean sell, String cid, int size);

}
