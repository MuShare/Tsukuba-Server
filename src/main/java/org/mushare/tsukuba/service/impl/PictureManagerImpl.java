package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.common.util.FileTool;
import org.mushare.tsukuba.bean.PictureBean;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.Picture;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.PictureManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PictureManagerImpl extends ManagerTemplate implements PictureManager {

    @Transactional
    public PictureBean handleUploadedPicture(String mid, String fileName) {
        Message message = messageDao.get(mid);
        if (message == null) {
            Debug.error("Cannot find the message by this mid.");
            return null;
        }
        // Modify file name.
        String path = configComponent.rootPath + configComponent.PicturePath + File.separator + mid;
        String newName = UUID.randomUUID().toString() + ".jpg";
        FileTool.modifyFileName(path, fileName, newName);
        // Save picture store path to persistent store.
        Picture picture = new Picture();
        picture.setCreateAt(System.currentTimeMillis());
        picture.setPath(configComponent.PicturePath + File.separator + mid + File.separator + newName);
        picture.setMessage(message);
        if (pictureDao.save(picture) == null) {
            // Delete picture file if save to persistent store failed.
            File file = new File(path + File.separator + newName);
            if (file.exists()) {
                file.delete();
            }
            return null;
        }
        // If the message of this picture has no cover, set this picture as its message's cover.
        if (message.getCover() == null) {
            message.setCover(picture);
            messageDao.update(message);
        }
        return new PictureBean(picture);
    }

    @Transactional
    public Result remove(String pid, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find the user by this uid");
            return Result.ObjectIdError;
        }
        Picture picture = pictureDao.get(pid);
        if (picture == null) {
            Debug.error("Cannot find the picture by this pid.");
            return Result.ObjectIdError;
        }
        // If this picture is the cover of its message, set cover to null at first.
        Message message = picture.getMessage();
        if (message.getCover().equals(picture)) {
            message.setCover(null);
            messageDao.update(message);
        }
        // Remove files at first.
        File file = new File(configComponent.rootPath + picture.getPath());
        if (file.exists()) {
            file.delete();
        }
        // Delete picture from persistent store.
        pictureDao.delete(picture);
        // If message has no cover, try to set cover to oldest picture of this message at first.
        if (message.getCover() == null) {
            Picture cover = pictureDao.getOldestByMessage(message);
            // If cover is not null, set it as new cover of this message.
            if (cover != null) {
                message.setCover(cover);
                messageDao.update(message);
            }
        }
        return Result.Success;
    }

    public List<PictureBean> getPicturesByMid(String mid) {
        Message message = messageDao.get(mid);
        if (message == null) {
            Debug.error("Cannot find the message by this mid.");
            return null;
        }
        List<PictureBean> pictureBeans = new ArrayList<PictureBean>();
        for (Picture picture : pictureDao.findByMessage(message)) {
            pictureBeans.add(new PictureBean(picture));
        }
        return pictureBeans;
    }

}
