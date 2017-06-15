package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.MessageBean;
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
import java.util.List;

@Controller
@RequestMapping("/api/favorite")
public class FavoriteController extends ControllerTemplate {

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ResponseEntity favorite(@RequestParam String mid, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final Result result = favoriteManager.favoriteMessage(mid, userBean.getUid());
        if (result == Result.ObjectIdError) {
            return generateBadRequest(ErrorCode.ErrorObjecId);
        }
        if (result == Result.SaveInternalError) {
            return generateBadRequest(ErrorCode.ErrorSaveFailed);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
            put("favorites", result.object);
        }});
    }

    @RequestMapping(value = "/unlike", method = RequestMethod.POST)
    public ResponseEntity cancelFavorite(@RequestParam String mid, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final Result result = favoriteManager.cancelFavoriteMessage(mid, userBean.getUid());
        // If favorite cannot be found by message and user, return false.
        final boolean success = result != Result.FavoriteNotExisted;
        return generateOK(new HashMap<String, Object>() {{
            put("success", success);
            put("favorites", result.object);
        }});
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getFavoritesForUser(HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        final List<MessageBean> messageBeans = favoriteManager.getFavoriteMessageByUid(userBean.getUid());
        return generateOK(new HashMap<String, Object>() {{
            put("messages", messageBeans);
        }});
    }

}
