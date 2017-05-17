package org.mushare.tsukuba.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.json.JSONObject;
import org.mushare.common.util.Debug;
import org.mushare.common.util.FileTool;
import org.mushare.common.util.MengularDocument;
import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.bean.VerificationBean;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.domain.Verification;
import org.mushare.tsukuba.service.UserManager;
import org.mushare.tsukuba.service.VerificationManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.mushare.tsukuba.service.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RemoteProxy(name = "UserManager")
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    @Transactional
    public Result registerByEmail(String email, String password, String name) {
        // Find this user by email, if the email is existed, do not allow him to register.
        User user = userDao.getByIdentifierWithType(email, UserTypeEmail);
        if (user != null) {
            Debug.error("This email has been registerd.");
            return Result.UserEmailRegistered;
        }
        user = new User(System.currentTimeMillis(), UserTypeEmail, email, password, name, 0, 0);
        user.setAvatar("/static/images/avatar.png");
        if (userDao.save(user) == null) {
            Debug.error("Error to save user.");
            return Result.SaveInternalError;
        }
        return Result.Success;
    }

    public UserBean getByUid(String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return null;
        }
        return new UserBean(user, true);
    }

    public UserBean getByEmail(String email) {
        User user = userDao.getByIdentifierWithType(email, UserTypeEmail);
        if (user == null) {
            return null;
        }
        return new UserBean(user, false);
    }

    @Transactional
    public UserBean getByFacebookAccessToken(String token) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://graph.facebook.com/me")
                    .header("accept", "application/json")
                    .queryString("access_token", token)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Debug.error("Cannot get user info from facebook, response is null.");
            return null;
        }
        if (response.getStatus() != HttpStatus.OK.value()) {
            Debug.error("Cannot get user info from facebook, bad request.");
            return null;
        }
        if (!appAuth(token)) {
            Debug.error("Access token belongs to another app.");
            return null;
        }
        JSONObject userInfo = response.getBody().getObject();
        if (userInfo.has("error")) {
            Debug.error("Malformed access token.");
            return null;
        }
        String userId = userInfo.getString("id");
        String name = userInfo.getString("name");
        User user = userDao.getByIdentifierWithType(userId, UserTypeFacebook);
        if (user == null) {
            user = new User(System.currentTimeMillis(), UserTypeFacebook, userId, token, name, 0, 0);
            // Download avatar from facebook when user login at first.
            user.setAvatar(downloadAvatarFromFacebook(token));
            userDao.save(user);
        } else {
            user.setCredential(token);
            user.setRev(user.getRev() + 1);
            userDao.update(user);
        }
        return new UserBean(user, false);
    }

    public UserBean authByToken(String token) {
        Device device = deviceDao.getByToken(token);
        if (device == null) {
            return null;
        }
        return new UserBean(device.getUser(), false);
    }

    @Transactional
    public int modify(String uid, String name, String contact, String address) {
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find the user by this uid.");
            return -1;
        }
        if (name != null && !name.equals("")) {
            user.setName(name);
        }
        if (contact != null && !contact.equals("")) {
            user.setContact(contact);
        }
        if (address != null && !address.equals("")) {
            user.setAddress(address);
        }
        user.setRev(user.getRev() + 1);
        userDao.update(user);
        return user.getRev();
    }

    @RemoteMethod
    @Transactional
    public boolean sendModifyPasswordMail(String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return false;
        }
        Verification verification = new Verification();
        verification.setCreateAt(System.currentTimeMillis() / 1000L);
        verification.setType(Verification.VerificationModifyPassword);
        verification.setActive(true);
        verification.setUser(user);
        String vid = verificationDao.save(verification);
        if (vid == null) {
            return false;
        }
        String rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        MengularDocument document = new MengularDocument(rootPath, 0, "mail/password.html", null);
        document.setValue("username", user.getName());
        document.setValue("httpProtocol", configComponent.global.httpProtocol);
        document.setValue("domain", configComponent.global.domain);
        document.setValue("vid", vid);
        boolean send =  mailComponent.send(user.getIdentifier(), "Reset yout password", document.getDocument());
        // If send mail failed, verficiation should be deleted.
        if (!send) {
            verificationDao.delete(verification);
        }
        return send;
    }

    @RemoteMethod
    @Transactional
    public boolean resetPassword(String password, HttpSession session) {
        if (password.equals("")) {
            return false;
        }
        VerificationBean verificationBean = (VerificationBean) session.getAttribute(VerificationManager.VerificationFlag);
        if (verificationBean == null) {
            return false;
        }
        Verification verification = verificationDao.get(verificationBean.getVid());
        if (verification == null) {
            return false;
        }
        if (verification.getType() != Verification.VerificationModifyPassword) {
            return false;
        }
        if (System.currentTimeMillis() / 1000L - verification.getCreateAt() > configComponent.global.validity) {
            session.removeAttribute(VerificationManager.VerificationFlag);
            return false;
        }
        User user = verification.getUser();
        user.setCredential(password);
        userDao.update(user);
        // Remove verfication from session.
        session.removeAttribute(VerificationManager.VerificationFlag);
        // Set verifivation not active.
        verification.setActive(false);
        verificationDao.update(verification);
        return true;
    }

    @Transactional
    public String handleUploadedAvatar(String uid, String fileName) {
        User user = userDao.get(uid);
        if (user == null) {
            Debug.error("Cannot find the user by this uid.");
            return null;
        }
        // If user has icon before, try to delete the old icon at first.
        if (user.getAvatar() != null) {
            File file = new File(configComponent.rootPath + user.getAvatar());
            if (file.exists()) {
                file.delete();
            }
        }
        // Modify avatar name.
        String path = configComponent.rootPath + configComponent.AvatarPath;
        String newName = UUID.randomUUID().toString() + ".jpg";
        FileTool.modifyFileName(path, fileName, newName);
        // Update avatar path and rev.
        user.setAvatar(configComponent.AvatarPath + File.separator + newName);
        user.setRev(user.getRev() + 1);
        userDao.update(user);
        return user.getAvatar();
    }

    private boolean appAuth(String token) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://graph.facebook.com/app")
                    .header("accept", "application/json")
                    .queryString("access_token", token)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (response == null) {
            return false;
        }
        JSONObject userInfo = response.getBody().getObject();
        String appId = userInfo.getString("id");
        return configComponent.facebook.appId.equals(appId);
    }

    private String downloadAvatarFromFacebook(String token) {
        HttpResponse<InputStream> response = null;
        try {
            response = Unirest.get("https://graph.facebook.com/me/picture")
                    .header("accept", "image/jpeg")
                    .queryString("width", 480)
                    .queryString("access_token", token)
                    .asBinary();
            String avatar = configComponent.AvatarPath + File.separator + UUID.randomUUID().toString() + ".jpg";
            File file = new File(configComponent.rootPath +  avatar);
            FileUtils.copyInputStreamToFile(response.getBody(), file);
            return avatar;
        } catch (Exception e) {
            e.printStackTrace();
            return "/static/images/avatar.jpg";
        }
    }

}
