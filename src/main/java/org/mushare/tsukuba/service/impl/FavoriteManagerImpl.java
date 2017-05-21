package org.mushare.tsukuba.service.impl;

import org.mushare.tsukuba.domain.Favorite;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.FavoriteManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return Result.Success;
    }

}
