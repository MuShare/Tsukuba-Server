package org.mushare.tsukuba.controller.api;

import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static org.mushare.tsukuba.controller.common.ErrorCode.*;

@Controller
@RequestMapping("/api/message")
public class MessageController extends ControllerTemplate {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createMessage(@RequestParam String cid, @RequestParam String title,
                                        @RequestParam String oids, @RequestParam int price,
                                        @RequestParam boolean sell, HttpServletRequest request) {
        if (price < 0) {
            Debug.error("Invalid price.");
            return generateBadRequest(ErrorInvalidPrice);
        }
        UserBean userBean;
        if ((userBean = auth(request)) == null) {
            Debug.error("User not found.");
            return generateBadRequest(ErrorToken);
        }
        String[] oidArray = oids.split(" *, *");
        final String mid;
        if ((mid = messageManager.create(cid, userBean.getUid(), title, oidArray, price, sell)) == null) {
            Debug.error("Save failed");
            return generateBadRequest(ErrorCode.ErrorSaveFailed);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("mid", mid);
        }});
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity modifyMessage(@RequestParam String mid, String title, String oids,
                                        String introduction, @RequestParam(defaultValue = "-1") int price, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        Result result = messageManager.modify(mid, title, oids.split(" *, *"), introduction, price, userBean.getUid());
        if (result == Result.ObjectIdError) {
            return generateBadRequest(ErrorCode.ErrorModifyMessageMidError);
        }
        if (result == Result.MessageModifyNoPrevilege) {
            return generateBadRequest(ErrorCode.ErrorModifyMessageNoPrivilege);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseEntity enableMessage(@RequestParam String mid, @RequestParam boolean enable, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>() {{

        }});
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity uploadPictureToMessage(@RequestParam String mid, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>() {{

        }});
    }

    @RequestMapping(value = "/picture", method = RequestMethod.DELETE)
    public ResponseEntity deletePictureFromMessage(@RequestParam String pid, HttpServletRequest request) {
        return generateOK(new HashMap<String, Object>() {{

        }});
    }

}
