package com.hyperapps.fcm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
  
	@Autowired
	FCMService fcmService;
 
    public void sendPushNotificationWithData(ArrayList<String> tokens,String message,String title) {
    	System.out.println("Push Notification Started");
    	for (String dtoken : tokens) {
    		 PushNotificationRequest pushNotificationRequest=new PushNotificationRequest();
             pushNotificationRequest.setMessage(message);
             pushNotificationRequest.setTitle(title);
             pushNotificationRequest.setToken(dtoken);
             Map<String, String> appData= new HashMap<>();
                 appData.put("name", "PushNotification");
             try {
                 fcmService.sendMessage(appData, pushNotificationRequest);
             } catch (Exception e) {
                 e.printStackTrace();
             }
		}
       
    }
}