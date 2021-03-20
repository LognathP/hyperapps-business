package com.hyperapps.fcm;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.hyperapps.logger.ConfigProperties;


public class MailSender {
	
	@Autowired
	ConfigProperties configProp;
	
	
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
