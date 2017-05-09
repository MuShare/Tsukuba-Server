package org.mushare.tsukuba.controller.api;

import net.sf.json.JSONArray;
import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.MessageBean;
import org.mushare.tsukuba.bean.PictureBean;
import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        Result result = messageManager.enable(mid, enable, userBean.getUid());
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

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity uploadPictureToMessage(@RequestParam String mid, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        Result result = messageManager.hasPrevilege(mid, userBean.getUid());
        if (result == Result.ObjectIdError) {
            return generateBadRequest(ErrorCode.ErrorObjecId);
        }
        if (result == Result.MessageModifyNoPrevilege) {
            return generateBadRequest(ErrorCode.ErrorModifyMessageNoPrivilege);
        }
        String fileNname = upload(request, createUploadDirectory(configComponent.PicturePath + File.separator + mid));
        final PictureBean pictureBean = pictureManager.handleUploadedPicture(mid, fileNname);
        if (pictureBean == null) {
            return generateBadRequest(ErrorCode.ErrorSavePicture);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("picture", pictureBean);
        }});
    }

    @RequestMapping(value = "/picture", method = RequestMethod.DELETE)
    public ResponseEntity deletePictureFromMessage(@RequestParam String pid, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        Result result = pictureManager.remove(pid, userBean.getUid());
        if (result == Result.ObjectIdError) {
            return generateBadRequest(ErrorCode.ErrorObjecId);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getMessages(@RequestParam(defaultValue = "-1") long seq,
                                      @RequestParam(defaultValue = "true") boolean sell,
                                      String cid, @RequestParam(defaultValue = "20") int size) {
        if (seq < 0) {
            seq = Long.MAX_VALUE;
        }
        final List<MessageBean> messageBeans = messageManager.searchEnableMessages(seq, sell, cid, size);
        return generateOK(new HashMap<String, Object>() {{
            put("messages", messageBeans);
        }});
    }

    @RequestMapping(value = "/list/user", method = RequestMethod.GET)
    public ResponseEntity getMessages(@RequestParam(defaultValue = "true") boolean sell, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final List<MessageBean> messageBeans = messageManager.getMessagesByUid(userBean.getUid(), sell);
        return generateOK(new HashMap<String, Object>() {{
            put("messages", messageBeans);
        }});
    }

    @RequestMapping(value = "/pictures", method = RequestMethod.GET)
    public ResponseEntity getPicturesOfMessage(@RequestParam String mid) {
        final List<PictureBean> pictureBeans = pictureManager.getPicturesByMid(mid);
        if (pictureBeans == null) {
            generateBadRequest(ErrorCode.ErrorObjecId);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("pictures", pictureBeans);
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
