package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.LoginBusiness;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.CommonDataResponse;
import com.hyperapps.model.CommonResponse;
import com.hyperapps.model.CommonSingleResponse;
import com.hyperapps.model.User;

@RestController
public class LoginController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	LoginBusiness loginBusiness;

	@PostMapping("/api/retailer/customer/login")
	public Object loginCustomer(@RequestParam String customers_telephone) {
		Logger.info(this.getClass(),"LOGIN CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return loginBusiness.loginCustomer(customers_telephone);
	}
	
	@PostMapping("/api/retailer/customer/login/check")
	public Object verifyCustomerLogin(@RequestParam int customer_id,@RequestParam String otp,
			@RequestParam String device_token,@RequestParam String device_type) throws Exception {
		Logger.info(this.getClass(),"OTP VERIFICATION API CALL STARTED AT "+dateFormat.format(new Date()));
		return loginBusiness.verifyCustomerLogin(customer_id,otp,device_token,device_type);
	}
	@PostMapping("/api/retailer/login")
	public Object loginRetailer(@RequestParam String email,@RequestParam String password,@RequestParam String device_token) throws Exception {
		Logger.info(this.getClass(),"LOGIN RETAILER API CALL STARTED AT "+dateFormat.format(new Date()));
		return loginBusiness.loginRetailer(email,password,device_token);
	}
	
	
}
