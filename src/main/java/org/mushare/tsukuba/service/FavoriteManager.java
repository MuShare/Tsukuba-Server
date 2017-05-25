package org.mushare.tsukuba.service;


import org.mushare.tsukuba.bean.MessageBean;
import org.mushare.tsukuba.service.common.Result;

import java.util.List;

public interface FavoriteManager {

    /**
     * User favorite a message.
     *
     * @param mid
     * @param uid
     * @return
     */
    Result favoriteMessage(String mid, String uid);

    /**
     * User cancel favorite a message.
     *
     * @param mid
     * @param uid
     * @return
     */
    Result cancelFavoriteMessage(String mid, String uid);

    /**
     * Get favorite messages for a user.
     *
     * @param uid
     * @return
     */
    List<MessageBean> getFavoriteMessageByUid(String uid);

}
