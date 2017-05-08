package org.mushare.tsukuba.service;


import org.mushare.tsukuba.bean.PictureBean;
import org.mushare.tsukuba.service.common.Result;

public interface PictureManager {

    /**
     * Handle uploaded picture.
     * Generate UUID file name and return new name.
     *
     * @param mid
     * @param fileName
     * @return picture
     */
    PictureBean handleUploadedPicture(String mid, String fileName);

    /**
     * Remove uploaded message picture
     *
     * @param pid
     * @param uid
     * @return
     */
    Result remove(String pid, String uid);

}
