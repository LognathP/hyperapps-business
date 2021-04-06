package com.hyperapps.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	public void sendEmail(String to, String subject, String text) {
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setFrom(configProp.getConfigValue("spring.mail.host"));
		        message.setTo(to); 
		        message.setSubject(subject); 
		        message.setText(text);
		        mailConfig().send(message);
		        
		    }
	
	public JavaMailSenderImpl mailConfig()
	{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(configProp.getConfigValue("spring.mail.host"));
	    mailSender.setPort(Integer.parseInt(configProp.getConfigValue("spring.mail.port")));
	    mailSender.setUsername(configProp.getConfigValue("spring.mail.username"));
	    mailSender.setPassword(configProp.getConfigValue("spring.mail.password"));
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	    return mailSender;	
	}
	

}
