package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.ChatBean;

import java.util.List;

public interface QueueManager {

    /**
     * enqueue
     *
     * @param did
     * @param cid
     * @return
     */
    String enqueue(String did, String cid);

    /**
     * dequeue
     *
     * @param did
     * @return
     */
    List<ChatBean> getChatsByDid(String did);

    /**
     *
     * @param did
     * @return
     */
    int removeByDid(String did);

}
