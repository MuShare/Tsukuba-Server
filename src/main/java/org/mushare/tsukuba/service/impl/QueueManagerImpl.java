package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.ChatBean;
import org.mushare.tsukuba.domain.Chat;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.Queue;
import org.mushare.tsukuba.service.QueueManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueManagerImpl extends ManagerTemplate implements QueueManager {

    @Transactional
    public String enqueue(String did, String cid) {
        Device device = deviceDao.get(did);
        if (device == null) {
            Debug.error("Cannot find a device by this did " + did);
            return null;
        }
        Chat chat = chatDao.get(cid);
        if (chat == null) {
            Debug.error("Cannot find a chat by this cid " + cid);
            return null;
        }
        Queue queue = new Queue();
        queue.setCreateAt(System.currentTimeMillis());
        queue.setDevice(device);
        queue.setChat(chat);
        return ququeDao.save(queue);
    }

    public List<ChatBean> getChatsByDid(String did) {
        Device device = deviceDao.get(did);
        if (device == null) {
            Debug.error("Cannot find a device by this did " + did);
            return null;
        }
        List<ChatBean> chatBeans = new ArrayList<>();
        for (Queue queue: ququeDao.findByDevice(device)) {
            chatBeans.add(new ChatBean(queue.getChat()));
        }
        return chatBeans;
    }

    @Override
    public int removeByDid(String did) {
        Device device = deviceDao.get(did);
        if (device == null) {
            Debug.error("Cannot find a device by this did " + did);
            return 0;
        }
        return ququeDao.deleteByDevice(device);
    }
}
