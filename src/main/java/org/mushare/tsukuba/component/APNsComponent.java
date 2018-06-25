package org.mushare.tsukuba.component;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

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

    private ApnsClient apnsClient = null;

    public ApnsClient getApnsClient() {
        if (apnsClient == null) {
            try {
                apnsClient = new ApnsClientBuilder()
                        .setApnsServer(distribution ? ApnsClientBuilder.PRODUCTION_APNS_HOST : ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                        .setClientCredentials(new File(config.rootPath + p12), password)
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return apnsClient;
    }

    public void push(String deviceToken, String alertBody, String category) {
        if (deviceToken == null || deviceToken.equals("")) {
            return;
        }
        final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
        payloadBuilder.setAlertBody(alertBody);
        payloadBuilder.setAlertTitle("Meng Li");
        payloadBuilder.setSound("default");
        payloadBuilder.setCategoryName(category);
        final SimpleApnsPushNotification pushNotification =  new SimpleApnsPushNotification(deviceToken, "org.mushare.Tsukuba-iOS", payloadBuilder.buildWithDefaultMaximumLength());
        final PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
                sendNotificationFuture = getApnsClient().sendNotification(pushNotification);

        try {
            final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                    sendNotificationFuture.get();

            if (pushNotificationResponse.isAccepted()) {
                System.out.println("Push notification accepted by APNs gateway.");
            } else {
                System.out.println("Notification rejected by the APNs gateway: " +
                        pushNotificationResponse.getRejectionReason());

                if (pushNotificationResponse.getTokenInvalidationTimestamp() != null) {
                    System.out.println("\t…and the token is invalid as of " +
                            pushNotificationResponse.getTokenInvalidationTimestamp());
                }

            }
        } catch (final InterruptedException | ExecutionException e) {
            System.err.println("Failed to send push notification.");
            e.printStackTrace();
        }

    }

}
