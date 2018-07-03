package org.mushare.tsukuba.component;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import org.mushare.common.util.Debug;
import org.mushare.tsukuba.dao.DeviceDao;
import org.mushare.tsukuba.domain.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class APNsComponent {

    @Autowired
    private ConfigComponent config;

    @Autowired
    private DeviceDao deviceDao;

    private ApnsClient apnsClient = null;

    public void load() {
        try {
            apnsClient = new ApnsClientBuilder()
                    .setApnsServer(config.apns.distribution ? ApnsClientBuilder.PRODUCTION_APNS_HOST : ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                    .setClientCredentials(new File(config.rootPath + config.apns.p12), config.apns.password)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ApnsClient getApnsClient() {
        if (apnsClient == null) {
            load();
        }
        return apnsClient;
    }

    @Transactional
    public void push(String deviceToken, String title, String alertBody, String category) {
        if (deviceToken == null || deviceToken.equals("")) {
            return;
        }
        final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
        payloadBuilder.setAlertBody(alertBody);
        payloadBuilder.setAlertTitle(title);
        payloadBuilder.setSound("default");
        payloadBuilder.setCategoryName(category);
        final SimpleApnsPushNotification pushNotification =  new SimpleApnsPushNotification(deviceToken, "org.mushare.Tsukuba-iOS", payloadBuilder.buildWithDefaultMaximumLength());
        final PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
                sendNotificationFuture = getApnsClient().sendNotification(pushNotification);

        try {
            final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                    sendNotificationFuture.get();

            if (!pushNotificationResponse.isAccepted()) {
                Debug.error("Notification rejected by the APNs gateway: " + pushNotificationResponse.getRejectionReason());

                if (pushNotificationResponse.getTokenInvalidationTimestamp() != null) {
                    System.out.println("\t…and the token is invalid as of " + pushNotificationResponse.getTokenInvalidationTimestamp());
                }

                // Clear device token if it is a bad device token.
                if (pushNotificationResponse.getRejectionReason().equals("BadDeviceToken")) {
                    Device device = deviceDao.getByDeviceToken(deviceToken);
                    if (device != null) {
                        device.setDeviceToken(null);
                        deviceDao.update(device);
                    }
                }

            }
        } catch (final InterruptedException | ExecutionException e) {
            Debug.error("Failed to send push notification.");
            e.printStackTrace();
        }

    }

}
