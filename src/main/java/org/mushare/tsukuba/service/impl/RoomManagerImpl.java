package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.RoomBean;
import org.mushare.tsukuba.domain.Room;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.RoomManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomManagerImpl extends ManagerTemplate implements RoomManager {

    public Map<String, List<RoomBean>> getByRidsForUser(List<String> rids, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find an user by this uid.");
            return null;
        }
        List<Room> rooms = roomDao.findBySenderOrReceiver(user);
        final List<RoomBean> existingRooms = new ArrayList<RoomBean>();
        if (rids != null && rids.size() > 0) {
            for (Room room : roomDao.findByRids(rids)) {
                if (!room.getReceiver().equals(user) && !room.getSender().equals(user)) {
                    continue;
                }
                rooms.remove(room);
                existingRooms.add(new RoomBean(room, RoomBean.RoomBeanStatus, room.getSender().equals(user)));
            }
        }
        final List<RoomBean> newRooms = new ArrayList<RoomBean>();
        for (Room room : rooms) {
            newRooms.add(new RoomBean(room, RoomBean.RoomBeanNew, room.getSender().equals(user)));
        }
        return new HashMap<String, List<RoomBean>>() {{
            put("exist", existingRooms);
            put("new", newRooms);
        }};
    }

}
