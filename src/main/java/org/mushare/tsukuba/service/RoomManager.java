package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.RoomBean;

import java.util.List;
import java.util.Map;

public interface RoomManager {

    /**
     * Find rooms for user by rids.
     *
     * @param rids
     * @param uid
     * @return
     */
    Map<String, List<RoomBean>> getByRidsForUser(List<String> rids, String uid);

}
