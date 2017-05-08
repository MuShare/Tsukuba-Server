package org.mushare.tsukuba.service;


public interface PictureManager {

    /**
     * Handle uploaded picture.
     * Generate UUID file name and return new name.
     *
     * @param mid
     * @param fileName
     * @return new file name
     */
    String handleUploadedPicture(String mid, String fileName);

}
