package org.mushare.tsukuba.controller.api;

import net.sf.json.JSONArray;
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
import java.util.Arrays;
import java.util.HashMap;

@Controller
@RequestMapping("/api/message")
public class MessageController extends ControllerTemplate {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createMessage(@RequestParam String cid, @RequestParam String title, @RequestParam String introduction,
                                        @RequestParam String oids, @RequestParam int price,
                                        @RequestParam boolean sell, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        String [] oidsArray = toStringArray(oids);
        if (price < 0 || oidsArray == null) {
            return generateBadRequest(ErrorCode.ErrorInvalidParameter);
        }
        final String mid = messageManager.create(cid, userBean.getUid(), title, introduction, oidsArray, price, sell);
        if (mid == null) {
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
        String [] oidsArray = toStringArray(oids);
        if (price < 0 || oidsArray == null) {
            return generateBadRequest(ErrorCode.ErrorInvalidParameter);
        }
        Result result = messageManager.modify(mid, title, oidsArray, introduction, price, userBean.getUid());
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

    private String [] toStringArray(String string) {
        JSONArray jsonArray = null;
        try {
            jsonArray = JSONArray.fromObject(string);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String [] strings = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            strings[i] = jsonArray.getString(i);
        }
        return strings;
    }

}
