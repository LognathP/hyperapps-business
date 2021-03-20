package com.hyperapps.fcm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Configuration
@EnableScheduling
public abstract class PushNotificationService {
    @Autowired
	static FCMService fcmService;
 
    //private String token="eqlgGDlP4mw:APA91bEmF-OM571wsS1oAzefdhmCd2DwW7xZWQ0YlWw_-pPKuyYNyHi17FMNbNw9oPhCdbj7YtOcxgerGCOJVsHEiPUP_a7v1bcvbAyL6xlHtdO0mB3JxfiQtQQ2_HuSUDkKR2tNMflC";
 
    //private String [] tokens = {"dJ7b7CMzrMo:APA91bF953l02g06HUgF2owwhvBbxKh5wLPbyWx72mMJ0kd8pXiSt6M5rHU1ILaXu-e18U1XUYiajJ2fDJcAJXvQ14XwswAuT46hKWS7Bl65p5k0n5jzSVRc3lIc1_gyC6vwvvXuVzue"};

   // @Scheduled(cron="0 0/1 * 1/1 * ?")
    public static void sendPushNotificationWithData(ArrayList<String> tokens,String message,String title) {
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