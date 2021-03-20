package com.hyperapps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.Scheduled;

import com.hyperapps.fcm.PushNotificationService;


@SpringBootApplication
public class HyperappsBusinessApplication extends SpringBootServletInitializer {

	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(HyperappsBusinessApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HyperappsBusinessApplication.class, args);
	}
	
	
}
