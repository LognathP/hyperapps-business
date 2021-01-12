package com.hyperapps.fcm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushNotificationRequest {
	private String title;
	private String message;
	private String token;
	
}
