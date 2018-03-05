package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.ChatBean;
import org.mushare.tsukuba.domain.Chat;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.Room;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.ChatManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatManagerImpl extends ManagerTemplate implements ChatManager {

    @Transactional
    public ChatBean sendPlainText(String senderId, String receiverId, String content) {
        User sender = userDao.get(senderId);
        if (sender == null) {
            Debug.error("Sender not found.");
            return null;
        }
        User receiver = userDao.get(receiverId);
        if (receiver == null) {
            Debug.error("Receiver not found");
            return null;
        }
        Room room = roomDao.getBySenderAndReceiver(sender, receiver);
        if (room == null) {
            room = roomDao.getBySenderAndReceiver(receiver, sender);
        }
        // Create a new room if there is no chat room between the sender and the reciver.
        if (room == null) {
            room = new Room();
            room.setCreateAt(System.currentTimeMillis());
            room.setUpdateAt(room.getCreateAt());
            room.setChats(0);
            room.setLastMessage(content);
            room.setSender(sender);
            room.setReceiver(receiver);
            if (roomDao.save(room) == null) {
                Debug.error("Room cannot be saved.");
                return null;
            }
        }
        Chat chat = new Chat();
        chat.setCreateAt(System.currentTimeMillis());
        chat.setContent(content);
        chat.setDownloaded(false);
        chat.setType(ChatTypePlainText);
        chat.setSeq(room.getChats() + 1);
        chat.setRoom(room);
        chat.setDirection(room.getSender().equals(sender));
        if (chatDao.save(chat) == null) {
            Debug.error("Chat cannot be saved.");
            return null;
        }
        room.setUpdateAt(System.currentTimeMillis());
        room.setChats(room.getChats() + 1);
        room.setLastMessage(content);
        roomDao.update(room);
        // Push remote notification
        for (Device device : deviceDao.findByUser(receiver)) {
            if (device.getDeviceToken() == null || device.getDeviceToken().equals("")) {
                continue;
            }
            apnsComponent.push(device.getDeviceToken(), sender.getName() + "\n" + content, "chat:" + senderId);
        }
        return new ChatBean(chat, room.getSender().equals(sender));
    }

    public List<ChatBean> getByRidWithSeq(String rid, int seq) {
        Room room = roomDao.get(rid);
        if (room == null) {
            Debug.error("Cannot find a room by this rid.");
            return null;
        }
        List<ChatBean> chatBeans = new ArrayList<ChatBean>();
        for (Chat chat : chatDao.findInRoomBySeq(room, seq)) {
            chatBeans.add(new ChatBean(chat));
        }
        return chatBeans;
    }

}
