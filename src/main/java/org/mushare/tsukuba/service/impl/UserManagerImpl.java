package org.mushare.tsukuba.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.mushare.common.util.Debug;
import org.mushare.tsukuba.bean.UserBean;
import org.mushare.tsukuba.domain.Device;
import org.mushare.tsukuba.domain.User;
import org.mushare.tsukuba.service.UserManager;
import org.mushare.tsukuba.service.common.ManagerTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    @Transactional
    public boolean registerByEmail(String email, String password, String name) {
        // Find this user by email, if the email is existed, do not allow him to register.
        User user = userDao.getByIdentifierWithType(email, UserTypeEmail);
        if (user != null) {
            Debug.error("This email has been registerd.");
            return false;
        }
        user = new User(System.currentTimeMillis(), UserTypeEmail, email, password, name, 0);
        if (userDao.save(user) == null) {
            Debug.error("Error to save user.");
            return false;
        }
        return true;
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
        JSONObject userInfo = response.getBody().getObject();
        if (userInfo.has("error")) {
            Debug.error("Malformed access token.");
            return null;
        }
        String userId = userInfo.getString("id");
        String name = userInfo.getString("name");
        User user = userDao.getByIdentifierWithType(userId, UserTypeFacebook);
        if (user == null) {
            user = new User(System.currentTimeMillis(), UserTypeFacebook, userId, token, name, 0);
            userDao.save(user);
        } else {
            user.setName(name);
            user.setCredential(token);
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

}
