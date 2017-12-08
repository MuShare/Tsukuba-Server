package org.mushare.tsukuba.service;

import org.mushare.tsukuba.bean.ChatBean;

public interface ChatManager {

    public static final int ChatTypePlainText = 0;
    public static final int ChatTypePicture = 1;

    /**
     * Sender sends a plain text message to a reveiver.
     *
     * @param senderId
     * @param receiverId
     * @param content
     * @return
     */
    ChatBean sendPlainText(String senderId, String receiverId, String content);

}
