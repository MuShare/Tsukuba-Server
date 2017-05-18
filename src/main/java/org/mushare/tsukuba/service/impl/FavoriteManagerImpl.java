package org.mushare.tsukuba.service.impl;

import org.mushare.tsukuba.domain.Favorite;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.FavoriteManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;

@Service
public class FavoriteManagerImpl extends ManagerTemplate implements FavoriteManager {

    public Result favoriteMessage(String mid, String uid) {
        User user = userDao.get(uid);
        Message message = messageDao.get(mid);
        if (user == null || message == null) {
            return Result.ObjectIdError;
        }
        Favorite favorite = new Favorite();
        favorite.setCreateAt(System.currentTimeMillis());
        favorite.setMessage(message);
        favorite.setUser(user);
        if (favoriteDao.save(favorite) == null) {
            return Result.SaveInternalError;
        }
        return Result.Success;
    }

}
