package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.ChatBean;
import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("api/chat")
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

}
