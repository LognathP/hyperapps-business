package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.LoginBusiness;
import com.hyperapps.logger.HyperAppsLogger;

@RestController
public class LoginController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	LoginBusiness loginBusiness;

	@PostMapping("/api/authenticate")
	public Object loginRetailer(@RequestParam String email,@RequestParam String password,@RequestParam String device_token) throws Exception {
		Logger.info(this.getClass(),"LOGIN RETAILER API CALL STARTED AT "+dateFormat.format(new Date()));
		return loginBusiness.loginRetailer(email,password,device_token);
	}

	@GetMapping("/api/authenticate/user")
	public Object getUserDetails(@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"GET USER RETAILER API CALL STARTED AT "+dateFormat.format(new Date()));
		return loginBusiness.getUserDetails(token);
	}
	
	
}
