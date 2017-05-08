package org.mushare.tsukuba.service;


import org.mushare.tsukuba.bean.PictureBean;
import org.mushare.tsukuba.service.common.Result;

import java.util.List;

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

    /**
     * Get pictures of a message by mid.
     *
     * @param mid
     * @return
     */
    List<PictureBean> getPicturesByMid(String mid);

}
