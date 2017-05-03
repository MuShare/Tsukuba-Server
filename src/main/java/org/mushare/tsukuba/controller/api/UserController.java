package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.HashMap;

@Controller
@RequestMapping("/api/user")
public class UserController extends ControllerTemplate {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerByEmail(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        if (!userManager.registerByEmail(email, password, name)) {
            return generateBadRequest(ErrorCode.ErrorEmailExist);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }

    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public ResponseEntity loginByEmail(@RequestParam String email, @RequestParam String password,
                                       @RequestParam String identifier, String deviceToken,
                                       @RequestParam String os, String version, String lan,
                                       HttpServletRequest request) {
        if (!deviceManager.isLegalDevice(os)) {
            return generateBadRequest(ErrorCode.ErrorIllegalIDeviceOS);
        }
        final UserBean userBean = userManager.getByEmail(email);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorEmailNotExist);
        }
        if (!userBean.getCredential().equals(password)) {
            return generateBadRequest(ErrorCode.ErrorPasswordWrong);
        }
        //Login success, register device.
        final String token = deviceManager.registerDevice(identifier, os, version, lan,
                deviceToken, getRemoteIP(request), userBean.getUid());
        return generateOK(new HashMap<String, Object>() {{
            put("token", token);
            put("name", userBean.getName());
        }});
    }

    @RequestMapping(value = "/login/facebook", method = RequestMethod.POST)
    public ResponseEntity loginByFacebook(@RequestParam String accessToken,
                                          @RequestParam String identifier, String deviceToken,
                                          @RequestParam String os, String version, String lan,
                                          HttpServletRequest request) {
        final UserBean userBean = userManager.getByFacebookAccessToken(accessToken);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorFacebookAccessTokenInvalid);
        }
        //Login success, register device.
        final String token = deviceManager.registerDevice(identifier, os, version, lan,
                deviceToken, getRemoteIP(request), userBean.getUid());
        return generateOK(new HashMap<String, Object>() {{
            put("token", token);
            put("name", userBean.getName());
        }});
    }

}
