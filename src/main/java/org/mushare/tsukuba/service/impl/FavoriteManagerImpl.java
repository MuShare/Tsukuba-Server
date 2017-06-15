package org.mushare.tsukuba.service.impl;

import org.mushare.tsukuba.bean.MessageBean;
import org.mushare.tsukuba.domain.Favorite;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.FavoriteManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteManagerImpl extends ManagerTemplate implements FavoriteManager {

    @Transactional
    public Result favoriteMessage(String mid, String uid) {
        User user = userDao.get(uid);
        Message message = messageDao.get(mid);
        if (user == null || message == null) {
            return Result.ObjectIdError;
        }
        Favorite favorite = favoriteDao.getByMessageForUser(message, user);
        if (favorite != null) {
            return Result.Success;
        }
        favorite = new Favorite();
        favorite.setCreateAt(System.currentTimeMillis());
        favorite.setMessage(message);
        favorite.setUser(user);
        if (favoriteDao.save(favorite) == null) {
            return Result.SaveInternalError;
        }
        // Update number of favorites for its message.
        message.setFavorites(favoriteDao.getFavoritesCountOfMessage(message));
        messageDao.update(message);
        Result result = Result.Success;
        result.object = message.getFavorites();
        return Result.Success;
    }

    @Transactional
    public Result cancelFavoriteMessage(String mid, String uid) {
        User user = userDao.get(uid);
        Message message = messageDao.get(mid);
        if (user == null || message == null) {
            return Result.ObjectIdError;
        }
        Favorite favorite = favoriteDao.getByMessageForUser(message, user);
        if (favorite == null) {
            return Result.FavoriteNotExisted;
        }
        favoriteDao.delete(favorite);
        // Update number of favorites for its message.
        message.setFavorites(favoriteDao.getFavoritesCountOfMessage(message));
        messageDao.update(message);
        Result result = Result.Success;
        result.object = message.getFavorites();
        return Result.Success;
    }

    public List<MessageBean> getFavoriteMessageByUid(String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        List<MessageBean> messageBeans = new ArrayList<MessageBean>();
        for (Favorite favorite : favoriteDao.findByUser(user)) {
            messageBeans.add(new MessageBean(favorite.getMessage()));
        }
        return messageBeans;
    }

}
