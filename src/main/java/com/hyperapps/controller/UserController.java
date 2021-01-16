package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.UserBusiness;
import com.hyperapps.logger.HyperAppsLogger;

@RestController
public class UserController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	UserBusiness userBusiness;

	@GetMapping("/api/retailer/profile/show/{userid}")
	public Object getProfile(@PathVariable ("userid") int userId,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"GET USER RETAILER API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.getProfile(userId,token);
	}
	
	@PostMapping(value = "/api/retailer/profile/update/{userId}",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object updateCustomerProfile(@PathVariable("userId") int user_Id,@RequestParam String token,@RequestParam String userId,@RequestParam String business_name,
			@RequestParam String business_short_desc,
			@RequestParam String business_long_desc,@RequestParam String store_category_ids,
			@RequestParam int physical_store_status,@RequestParam String physical_store_address,
			@RequestParam String business_phone,@RequestParam int business_operating_mode,
			@RequestParam String business_operating_timings,@RequestParam int store_id) {
		Logger.info(this.getClass(),"UPDATE RETAILER PROFILE API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.updateUserProfile(user_Id,token,userId,business_name,
				business_short_desc,business_long_desc,store_category_ids,physical_store_status,
				physical_store_address,business_phone,business_operating_mode,business_operating_timings,store_id);
	}
	@PutMapping(value = "/api/retailer/profile/update/{userId}",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object updateCustomerProfileImage(@PathVariable("userId") int user_Id,@RequestParam String token,@RequestParam String userId,@RequestParam String business_name,
			@RequestParam String business_short_desc,
			@RequestParam String business_long_desc,@RequestParam String store_category_ids,
			@RequestParam int physical_store_status,@RequestParam String physical_store_address,
			@RequestParam String business_phone,@RequestParam int business_operating_mode,
			@RequestParam String business_operating_timings,@RequestParam String userImage,@RequestParam int store_id) {
		Logger.info(this.getClass(),"UPDATE RETAILER PROFILE IMAGE API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.updateUserProfileImage(user_Id,token,userId,business_name,
				business_short_desc,business_long_desc,store_category_ids,physical_store_status,
				physical_store_address,business_phone,business_operating_mode,business_operating_timings,userImage,store_id);
	}
	@PostMapping("/api/retailer/store/running_status")
	public Object updateRunningStatus(@RequestParam int store_id,@RequestParam int running_status) {
		Logger.info(this.getClass(),"STORE RUNNING STATUS UPDATE API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.updateRunningStatus(store_id,running_status);
		
	}
	@PostMapping("/api/retailer/profile/storeTaxStatus")
	public Object updateRunningStatus(@RequestParam int tax_status,@RequestParam int tax_percentage,@RequestParam String tax_gst,@RequestParam int store_id) {
		Logger.info(this.getClass(),"STORE RUNNING STATUS UPDATE API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.updateTaxInfo(store_id,tax_status,tax_percentage,tax_gst);
		
	}
	@PostMapping("/api/retailer/profile/getbusinesstime")
	public Object getStoreBusinesstime(@RequestParam int store_id) {
		Logger.info(this.getClass(),"GET STORE BUSINESS TIME API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.getStoreBusinesstime(store_id);
		
	}
	@PostMapping("/api/retailer/profile/updatebusinesstime")
	public Object updateStoreBusinesstime(@RequestParam int tax_status,@RequestParam int tax_percentage,@RequestParam String tax_gst,@RequestParam int store_id) {
		Logger.info(this.getClass(),"STORE RUNNING STATUS UPDATE API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.updateTaxInfo(store_id,tax_status,tax_percentage,tax_gst);
		
	}
}
