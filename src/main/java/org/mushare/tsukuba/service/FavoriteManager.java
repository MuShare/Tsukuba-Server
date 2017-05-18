package org.mushare.tsukuba.service;


import org.mushare.tsukuba.service.common.Result;

public interface FavoriteManager {

    /**
     * User favorite a message.
     *
     * @param mid
     * @param uid
     * @return
     */
    Result favoriteMessage(String mid, String uid);

}
