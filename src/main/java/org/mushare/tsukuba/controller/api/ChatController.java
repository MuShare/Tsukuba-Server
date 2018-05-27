package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.ChatBean;
import org.mushare.tsukuba.bean.RoomBean;
import org.mushare.tsukuba.bean.SimpleUserBean;
import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
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
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
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
        SimpleUserBean receiverBean = chatBean.isDirection() ? chatBean.getRoom().getReceiver() : chatBean.getRoom().getSender();
        Session receiverSession = sessions.get(receiverBean.getUid());
        if (receiverSession != null) {
            try {
                receiverSession.getBasicRemote().sendText(chatBean.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return generateOK(new HashMap<String, Object>(){{
            put("chat", chatBean);
        }});
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity sendPicture(@RequestParam String uid, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>(){{

        }});
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

        UserBean userBean = userManager.authByToken(token);
        if (userBean == null) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "auth_failed"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sessions.put(userBean.getUid(), session);
    }

    @OnClose
    public void onClose() {

    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("on message received: " + message);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {

    }

}
