package org.mushare.tsukuba.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.common.util.FileTool;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.Picture;
import org.mushare.tsukuba.service.PictureManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.UUID;

@Service
public class PictureManagerImpl extends ManagerTemplate implements PictureManager {

    @Transactional
    public String handleUploadedPicture(String mid, String fileName) {
        Message message = messageDao.get(mid);
        if (message == null) {
            Debug.error("Cannot find the message by this mid.");
            return null;
        }
        // Modify file name.
        String path = configComponent.rootPath + configComponent.PicturePath + File.separator + mid;
        String newName = UUID.randomUUID().toString() + ".jpg";
        FileTool.modifyFileName(path, fileName, newName);
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
        return picture.getPath();
    }
}
