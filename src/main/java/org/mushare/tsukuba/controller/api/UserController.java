package org.mushare.tsukuba.controller.api;

import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.controller.common.ControllerTemplate;
import org.mushare.tsukuba.controller.common.ErrorCode;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.HashMap;

@Controller
@RequestMapping("/api/user")
public class UserController extends ControllerTemplate {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerByEmail(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        Result result = userManager.registerByEmail(email, password, name);
        if (result == Result.UserEmailRegistered) {
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
            put("user", userBean);
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
            put("user", userBean);
        }});
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(HttpServletRequest request) {
        final UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        userBean.safe();
        return generateOK(new HashMap<String, Object>() {{
            put("user", userBean);
        }});
    }

    @RequestMapping(value = "/modify/info", method = RequestMethod.POST)
    public ResponseEntity modifyUser(String name, String contact, String address, HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        userManager.modify(userBean.getUid(), name, contact, address);
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }

    @RequestMapping(value = "/modify/password", method = RequestMethod.GET)
    public ResponseEntity sendResetPasswordMail(@RequestParam String email) {
        final UserBean user = userManager.getByEmail(email);
        if (user == null) {
            return generateBadRequest(ErrorCode.ErrorEmailNotExist);
        }
        if (!userManager.sendModifyPasswordMail(user.getUid())) {
            return generateBadRequest(ErrorCode.ErrorSendResetPasswordMail);
        }
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public ResponseEntity uploadAvatar(HttpServletRequest request) {
        UserBean userBean = auth(request);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorToken);
        }
        // Reveive avatar and get file name.
        String fileName = upload(request, configComponent.rootPath + configComponent.AvatarPath);
        // Handle uploaded user avatar.
        final String avatar = userManager.handleUploadedAvatar(userBean.getUid(), fileName);
        return generateOK(new HashMap<String, Object>() {{
            put("avatar", avatar);
        }});
    }

}
