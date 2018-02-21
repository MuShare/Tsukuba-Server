package org.mushare.tsukuba.component;


import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.PayloadBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class APNsComponent {

    @Autowired
    private ConfigComponent config;

    private String p12;
    private String password;
    private boolean distribution;

    public void setP12(String p12) {
        this.p12 = p12;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDistribution(boolean distribution) {
        this.distribution = distribution;
    }

    private ApnsService service = null;

    public ApnsService getService() {
        if (service == null) {
            ApnsServiceBuilder builder = APNS.newService().withCert(config.rootPath + p12, password);
            if (distribution) {
                service = builder.withSandboxDestination().build();
            } else {
                service = builder.build();
            }
        }
        return service;
    }

    public void push(String deviceToken, String alertBody, String category) {
        if (deviceToken == null || deviceToken.equals("")) {
            return;
        }
        PayloadBuilder payload = APNS.newPayload().alertBody(alertBody).sound("default");
        if (category != null) {
            payload.category(category);
        }
        getService().push(deviceToken, payload.build());
    }

}
