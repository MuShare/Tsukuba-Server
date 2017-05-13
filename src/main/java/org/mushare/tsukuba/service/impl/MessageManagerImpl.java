package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.DetailMessageBean;
import org.mushare.tsukuba.bean.MessageBean;
import org.mushare.tsukuba.domain.*;
import org.mushare.tsukuba.service.MessageManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageManagerImpl extends ManagerTemplate implements MessageManager {

    @Transactional
    public String create(String cid, String uid, String title, String introduction, String[] oids, int price, boolean sell) {
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
        message.setSeq(messageDao.getMaxSeq() + 1);
        message.setTitle(title);
        message.setIntroduction(introduction);
        message.setPrice(price);
        message.setSell(sell);
        message.setEnable(true);
        message.setCategory(category);
        message.setUser(user);
        String mid = messageDao.save(message);
        if (mid == null) {
            Debug.error("Message save failed");
            return null;
        }
        if (oids.length > 0) {
            for (Option option : optionDao.findInOids(oids)) {
                Answer answer = new Answer();
                answer.setCreateAt(System.currentTimeMillis());
                answer.setMessage(message);
                answer.setOption(option);
                if (answerDao.save(answer) == null) {
                    Debug.error("Answer save failed");
                    return null;
                }
            }
        }
        return mid;
    }

    @Transactional
    public Result modify(String mid, String title, String[] oids, String introduction, int price, String uid) {
        Message message = messageDao.get(mid);
        if (message == null) {
            Debug.error("Cannot find the message by this mid.");
            return Result.ObjectIdError;
        }
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find the user by this uid.");
            return Result.ObjectIdError;
        }
        if (!message.getUser().equals(user)) {
            return Result.MessageModifyNoPrevilege;
        }
        if (title != null && !title.equals("")) {
            message.setTitle(title);
        }
        if (introduction != null && !introduction.equals("")) {
            message.setIntroduction(introduction);
        }
        if (price >= 0) {
            message.setPrice(price);
        }
        messageDao.update(message);
        if (oids.length > 0) {
            // Delete old answers.
            answerDao.deleteByMessage(message);
            // Create new answers
            for (Option option : optionDao.findInOids(oids)) {
                Answer answer = new Answer();
                answer.setCreateAt(System.currentTimeMillis());
                answer.setMessage(message);
                answer.setOption(option);
                if (answerDao.save(answer) == null) {
                    return Result.SaveInternalError;
                }
            }
        }

        return Result.Success;
    }

    public Result hasPrevilege(String mid, String uid) {
        Message message = messageDao.get(mid);
        if (message == null) {
            Debug.error("Cannot find the message by this mid.");
            return Result.ObjectIdError;
        }
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find the user by this uid.");
            return Result.ObjectIdError;
        }
        if (!message.getUser().equals(user)) {
            return Result.MessageModifyNoPrevilege;
        }
        return Result.Success;
    }

    public Result enable(String mid, boolean enable, String uid) {
        Message message = messageDao.get(mid);
        if (message == null) {
            Debug.error("Cannot find the message by this mid");
            return Result.ObjectIdError;
        }
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find the user by this uid.");
            return Result.ObjectIdError;
        }
        if (!message.getUser().equals(user)) {
            return Result.MessageModifyNoPrevilege;
        }
        message.setEnable(enable);
        messageDao.update(message);
        return Result.Success;
    }

    public DetailMessageBean getDetail(String mid) {
        Message message = messageDao.get(mid);
        if (message == null) {
            Debug.error("Cannot find the message by this mid.");
            return null;
        }
        return new DetailMessageBean(message,
                pictureDao.findByMessage(message),
                answerDao.findByMessage(message));
    }

    public List<MessageBean> getMessagesByUid(String uid, boolean sell) {
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find the user by this uid.");
            return null;
        }
        List<MessageBean> messageBeans = new ArrayList<MessageBean>();
        for (Message message : messageDao.findByUser(user, sell)) {
            messageBeans.add(new MessageBean(message));
        }
        return messageBeans;
    }

    public List<MessageBean> searchEnableMessages(long seq, boolean sell, String cid, int size) {
        Category category = null;
        if (cid != null && !cid.equals("")) {
            category = categoryDao.get(cid);
        }
        // Get offset, offset is the number of messages whose sequence is larger or equal than the given sequence number.
        int offset = messageDao.getCountWithSeqLargerThan(seq, sell, category);
        List<MessageBean> messageBeans = new ArrayList<MessageBean>();
        // Find limited messages from offset position.
        for (Message message : messageDao.findWithSellInCategoryByPage(sell, category, offset, size)) {
            messageBeans.add(new MessageBean(message));
        }
        return messageBeans;
    }

}
