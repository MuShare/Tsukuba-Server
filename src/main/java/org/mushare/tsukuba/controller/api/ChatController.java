package org.mushare.tsukuba.controller.api;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.mushare.tsukuba.bean.*;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.mushare.tsukuba.service.ChatManager;
import org.mushare.tsukuba.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/chat")
@ServerEndpoint(value = "/websocket/chat", configurator = SpringConfigurator.class)
public class ChatController extends ControllerTemplate {

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public ResponseEntity sendPlainText(@RequestParam String receiver, @RequestParam String content, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final ChatBean chatBean = chatManager.sendPlainText(userBean.getUid(), receiver, content);
        if (chatBean == null) {
            return generateBadRequest(ErrorCode.ErrorSendPlainText);
        }
        notifyReciver(userBean, receiver, chatBean);
        return generateOK(new HashMap<String, Object>(){{
            put("chat", chatBean);
        }});
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity sendPicture(@RequestParam String receiver, @RequestParam(defaultValue = "0") double width,
                                      @RequestParam(defaultValue = "0") double height, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        // Receive picture and get file name.
        String filename = upload(request, configComponent.rootPath + configComponent.ChatPicturePath);
        ChatBean chatBean = chatManager.sendPicture(userBean.getUid(), receiver, filename, width, height);
        notifyReciver(userBean, receiver, chatBean);
        return generateOK(new HashMap<String, Object>(){{
            put("chat", chatBean);
        }});
    }

    private void notifyReciver(UserBean sender, String receiver, ChatBean chatBean) {
        List<DeviceBean> deviceBeans = deviceManager.getDevicesByUid(receiver);
        // Send JSON string by web socket
        for (DeviceBean deviceBean: deviceBeans) {
            Session receiverSession = sessions.get(deviceBean.getDid());
            if (receiverSession == null) {
                queueManager.enqueue(deviceBean.getDid(), chatBean.getCid());
                continue;
            }
            if (receiverSession.isOpen()) {
                try {
                    String text = "[" + JSONObject.fromObject(chatBean).toString() +"]";
                    receiverSession.getBasicRemote().sendText(text);
                } catch (IOException e) {
                    queueManager.enqueue(deviceBean.getDid(), chatBean.getCid());
                    e.printStackTrace();
                }
            } else {
                queueManager.enqueue(deviceBean.getDid(), chatBean.getCid());
                sessions.remove(deviceBean.getDid());
            }

        }

        // Push remote notification.
        new Thread(() -> {
            for (DeviceBean deviceBean : deviceBeans) {
                if (deviceBean.getDeviceToken() == null || deviceBean.getDeviceToken().equals("")) {
                    continue;
                }

                String content = "";
                switch (chatBean.getType()) {
                    case ChatManager.ChatTypePlainText:
                        content = chatBean.getContent();
                        break;
                    case ChatManager.ChatTypePicture:
                        String lan = deviceBean.getLan();
                        if (!configComponent.global.pictureNotification.containsKey(lan)) {
                            lan = configComponent.global.languages[0];
                        }
                        content = configComponent.global.pictureNotification.get(lan).replace("{name}", sender.getName());
                        break;
                    default:
                        break;
                }
                apnsComponent.push(deviceBean.getDeviceToken(),
                        sender.getName(), content,
                        "chat:" + sender.getUid());
            }
        }).start();
    }

    @RequestMapping(value = "/picture/{cid}", method = RequestMethod.GET)
    public void getChatPicture(@PathVariable String cid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserBean userBean = auth(request);
        if (userBean == null) {
            response.getWriter().write("Token error.");
            return;
        }
        String storage = chatManager.getPictureStorage(cid, userBean.getUid());
        if (storage == null) {
            response.getWriter().write("Chat id is not found, or no privilege to access this picture");
            return;
        }
        getPicture(configComponent.rootPath + storage, response);
    }

    @RequestMapping(value = "/list/{rid}", method = RequestMethod.GET)
    public ResponseEntity getChatList(@PathVariable String rid, @RequestParam(defaultValue = "0") int seq, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final List<ChatBean> chatBeans = chatManager.getByRidWithSeq(rid, seq);
        return generateOK(new HashMap<String, Object>(){{
            put("chats", chatBeans);
        }});
    }

    @RequestMapping(value = "/room/status", method = RequestMethod.GET)
    public ResponseEntity getRoomStatus(@RequestParam(required = false) final List<String> rids, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final Map<String, List<RoomBean>> roomBeans = roomManager.getByRidsForUser(rids, userBean.getUid());
        return generateOK(new HashMap<String, Object>(){{
            put("exist", (List<RoomBean>)roomBeans.get("exist"));
            put("new", (List<RoomBean>)roomBeans.get("new"));
        }});
    }

    private static final Map<String, Session> sessions = new HashMap<String, Session>();

    @OnOpen
    public void onOpen(Session session) {
        String token = (String) session.getRequestParameterMap().get("token").get(0);
        DeviceBean deviceBean = deviceManager.authDevice(token);
        if (deviceBean == null) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "auth_failed"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sessions.put(deviceBean.getDid(), session);
        List<ChatBean> chatBeans = queueManager.getChatsByDid(deviceBean.getDid());
        if (chatBeans != null && chatBeans.size() > 0) {
            String text = JSONArray.fromObject(chatBeans).toString();
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(text);
                    queueManager.removeByDid(deviceBean.getDid());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(sessions.size() + " devices, " + deviceBean.getDid() + " has been connected.");
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        sessions.values().removeIf(v -> v.equals(session));
        System.out.println(sessions.size() + " devices.");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("on message received: " + message);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {

    }

}
