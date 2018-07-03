package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.common.util.FileTool;
import org.mushare.tsukuba.bean.ChatBean;
import org.mushare.tsukuba.domain.Chat;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.Room;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.ChatManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatManagerImpl extends ManagerTemplate implements ChatManager {

    public String getPictureStorage(String cid, String uid) {
        Chat chat = chatDao.get(cid);
        if (chat == null) {
            Debug.error("Cannot find a chat by this cid.");
            return null;
        }
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find an user by this uid.");
        }
        if (!chat.getRoom().getSender().equals(user) && !chat.getRoom().getReceiver().equals(user)) {
            return null;
        }
        if (chat.getType() != ChatManager.ChatTypePicture) {
            return null;
        }
        return chat.getContent();
    }

    @Transactional
    public ChatBean sendPlainText(String senderId, String receiverId, String content) {
        Room room = getChatRoom(senderId, receiverId);
        if (room == null) {
            Debug.error("Cannot find a room by this rid.");
            return null;
        }
        Chat chat = new Chat();
        chat.setCreateAt(System.currentTimeMillis());
        chat.setContent(content);
        chat.setDownloaded(false);
        chat.setType(ChatTypePlainText);
        chat.setSeq(room.getChats() + 1);
        chat.setRoom(room);
        chat.setDirection(room.getSender().getUid().equals(senderId));
        if (chatDao.save(chat) == null) {
            Debug.error("Chat cannot be saved.");
            return null;
        }
        room.setUpdateAt(System.currentTimeMillis());
        room.setChats(room.getChats() + 1);
        room.setLastMessage(content);
        roomDao.update(room);
        return new ChatBean(chat, room.getSender().getUid().equals(senderId));
    }

    @Transactional
    public ChatBean sendPicture(String senderId, String receiverId, String filename, double width, double height) {
        Room room = getChatRoom(senderId, receiverId);
        if (room == null) {
            Debug.error("Cannot find a room by this rid.");
            return null;
        }
        // Move file.
        String path = configComponent.ChatPicturePath + File.separator + room.getRid();
        String storeFilename = UUID.randomUUID().toString() + ".jpg";
        FileTool.createDirectoryIfNotExsit(configComponent.rootPath + path);
        FileTool.moveFile(configComponent.rootPath + configComponent.ChatPicturePath + File.separator + filename, configComponent.rootPath + path + File.separator + storeFilename);

        // Create new chat object.
        Chat chat = new Chat();
        chat.setCreateAt(System.currentTimeMillis());
        chat.setContent(path + File.separator + storeFilename);
        chat.setPictureWidth(width);
        chat.setPictureHeight(height);
        chat.setDownloaded(false);
        chat.setType(ChatTypePicture);
        chat.setSeq(room.getChats() + 1);
        chat.setRoom(room);
        chat.setDirection(room.getSender().getUid().equals(senderId));

        if (chatDao.save(chat) == null) {
            Debug.error("Chat cannot be saved.");
            return null;
        }
        room.setUpdateAt(System.currentTimeMillis());
        room.setChats(room.getChats() + 1);
        room.setLastMessage("[picture]");
        roomDao.update(room);

        return new ChatBean(chat, room.getSender().getUid().equals(senderId));
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

    private Room getChatRoom(String senderId, String receiverId) {
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
            room.setSender(sender);
            room.setReceiver(receiver);
            if (roomDao.save(room) == null) {
                Debug.error("Room cannot be saved.");
                return null;
            }
        }
        return room;
    }

}
