package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.domain.*;
import org.mushare.tsukuba.service.MessageManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageManagerImpl extends ManagerTemplate implements MessageManager {

    @Transactional
    public String create(String cid, String uid, String title, String[] oids, int price, boolean sell) {
        Category category = categoryDao.get(cid);
        if (category == null) {
            Debug.error("Category not found");
            return null;
        }
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("User not found");
            return null;
        }
        List<Option> options = new ArrayList<Option>();
        for (String oid : oids) {
            Option option = optionDao.get(oid);
            if (option == null) {
                Debug.error("Cannot find a option by this oid.");
                return null;
            }
            if (!option.getSelection().getCategory().getCid().equals(cid)) {
                Debug.error("Option not belongs to the category.");
                return null;
            }
            options.add(option);
        }
        Message message = new Message();
        message.setCreateAt(System.currentTimeMillis());
        message.setUpdateAt(message.getCreateAt());
        message.setSeq(messageDao.getMaxSeq());
        message.setTitle(title);
        message.setPrice(price);
        message.setSell(sell);
        message.setEnable(true);
        message.setCategory(category);
        message.setUser(user);
        String mid;
        if ((mid = messageDao.save(message)) == null) {
            Debug.error("Message save failed");
            return null;
        }
        for (Option option : options) {
            Answer answer = new Answer();
            answer.setCreateAt(System.currentTimeMillis());
            answer.setMessage(message);
            answer.setOption(option);
            if (answerDao.save(answer) == null) {
                Debug.error("Answer save failed");
                return null;
            }
        }
        return mid;
    }
}
