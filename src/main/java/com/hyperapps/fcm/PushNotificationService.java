package com.hyperapps.fcm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    @Autowired
    FCMService fcmService;
 
    private String token="eLs0WItVXAERsHw2:APA91bFxeQ0-BPVWb6IX905s8ZacvVR6x1DYlp3-ikfitZwGMONyYT7mBMDBLRB07kbdWIzXCm";
 
    @Scheduled(cron="0 0/2 * 1/1 * ?")
    public void sendPushNotificationWithData() {
        PushNotificationRequest pushNotificationRequest=new PushNotificationRequest();
            pushNotificationRequest.setMessage("Send push notifications from Spring Boot server");
            pushNotificationRequest.setTitle("Hello !");
            pushNotificationRequest.setToken(token);
            Map<String, String> appData= new HashMap<>();
                appData.put("name", "PushNotification");
            try {
                fcmService.sendMessage(appData, pushNotificationRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}