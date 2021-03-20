package com.hyperapps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.hyperapps.fcm.MailSender;
import com.hyperapps.logger.HyperAppsLogger;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
    private MailSender emailSender;
	
	public void sendEmail(String to, String subject, String text) {
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setFrom("noreply@baeldung.com");
		        message.setTo(to); 
		        message.setSubject(subject); 
		        message.setText(text);
		        emailSender.mailConfig().send(message);
		        
		    }
}
