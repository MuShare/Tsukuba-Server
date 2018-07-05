package org.mushare.tsukuba.component.config;

import net.sf.json.JSONObject;

public class APNs {

    public String p12;
    public String password;
    public String topic;
    public boolean distribution;

    public APNs(JSONObject object) {
        this.p12 = object.getString("p12");
        this.password = object.getString("password");
        this.topic = object.getString("topic");
        this.distribution = object.getBoolean("distribution");
    }

}
