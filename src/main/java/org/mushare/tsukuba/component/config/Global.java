package org.mushare.tsukuba.component.config;

import net.sf.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Global {

    // Supported languages
    public String [] languages;

    // Http protocal of Httper Web service.
    public String httpProtocol;

    // Domain nane of Httper Web service.
    public String domain;

    // Period of validity for verfication code, unit is second.
    public int validity;

    // Reset mail titles
    public Map<String, String> resetTitle;

    public Map<String, String> pictureNotification;

    public Global(JSONObject object) {
        this.languages = object.getString("languages").split("/");
        this.httpProtocol = object.getString("httpProtocol");
        this.domain = object.getString("domain");
        this.validity = object.getInt("validity");

        this.resetTitle = new HashMap<>();
        this.pictureNotification = new HashMap<>();
        String [] resetTitles = object.getString("resetTitle").split("/");
        String [] pictureNotifications = object.getString("pictureNotification").split("/");
        for (int i = 0; i < languages.length; i++) {
            this.resetTitle.put(languages[i], resetTitles[i]);
            this.pictureNotification.put(languages[i], pictureNotifications[i]);
        }

    }

}
