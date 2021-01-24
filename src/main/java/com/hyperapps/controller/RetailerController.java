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

import com.hyperapps.business.RetailerBusiness;
import com.hyperapps.logger.HyperAppsLogger;

@RestController
public class RetailerController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	RetailerBusiness retailerBusiness;

	@GetMapping("/api/retailer/profile/show/{userid}")
	public Object getProfile(@PathVariable ("userid") int userId,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"GET USER RETAILER API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.getProfile(userId,token);
	}
	
	@PostMapping(value = "/api/retailer/profile/update/{userId}",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object updateCustomerProfile(@PathVariable("userId") int user_Id,@RequestParam String token,@RequestParam String user_id,@RequestParam String business_name,
			@RequestParam String business_short_desc,
			@RequestParam String business_long_desc,@RequestParam String store_category_ids,
			@RequestParam int physical_store_status,@RequestParam String physical_store_address,
			@RequestParam String business_phone,@RequestParam int business_operating_mode,
			@RequestParam String business_operating_timings,@RequestParam int store_id) {
		Logger.info(this.getClass(),"UPDATE RETAILER PROFILE API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.updateUserProfile(user_Id,token,user_id,business_name,
				business_short_desc,business_long_desc,store_category_ids,physical_store_status,
				physical_store_address,business_phone,business_operating_mode,business_operating_timings,store_id);
	}
	@PutMapping(value = "/api/retailer/profile/update/{userId}",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object updateCustomerProfileImage(@PathVariable("userId") int user_Id,@RequestParam String token,@RequestParam String user_id,@RequestParam String business_name,
			@RequestParam String business_short_desc,
			@RequestParam String business_long_desc,@RequestParam String store_category_ids,
			@RequestParam int physical_store_status,@RequestParam String physical_store_address,
			@RequestParam String business_phone,@RequestParam int business_operating_mode,
			@RequestParam String business_operating_timings,@RequestParam String userImage,@RequestParam int store_id) {
		Logger.info(this.getClass(),"UPDATE RETAILER PROFILE IMAGE API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.updateUserProfileImage(user_Id,token,user_id,business_name,
				business_short_desc,business_long_desc,store_category_ids,physical_store_status,
				physical_store_address,business_phone,business_operating_mode,business_operating_timings,userImage,store_id);
	}
	
	@PostMapping(value = "/api/retailer/team/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object addTeamMember(@RequestParam String token,@RequestParam int store_id,
			@RequestParam int user_id,@RequestParam String name,@RequestParam String mobile,
			@RequestParam int access_type,@RequestParam String email) {
		Logger.info(this.getClass(),"ADD TEAM MEMBER API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.addTeamMember(token,store_id,user_id,name,mobile,access_type,email);
	}
	
	@PostMapping(value = "/api/retailer/team/remove",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object removeTeamMember(@RequestParam String token,@RequestParam int user_id,
			@RequestParam String email) {
		Logger.info(this.getClass(),"REMOVE TEAM MEMBER API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.removeTeamMember(token,user_id,email);
	}
	
	@PostMapping(value = "/api/retailer/customer/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object addCustomer(@RequestParam String token,@RequestParam int user_id,
			@RequestParam String customers_firstname,@RequestParam String customers_telephone,@RequestParam String customers_email_address) {
		Logger.info(this.getClass(),"ADD CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.addCustomer(token,user_id,customers_firstname,customers_telephone,customers_email_address);
	}
	
	@PostMapping(value = "/api/retailer/feedback/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object addfeedback(@RequestParam String token,@RequestParam int user_id,
			@RequestParam String details) {
		Logger.info(this.getClass(),"ADD USER FEEDBACK API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.addfeedback(token,user_id,details);
	}
	
	@PostMapping(value = "/api/retailer/customer/total_list")
	public Object fetchCustomerList(@RequestParam String token,@RequestParam String customer_type) {
		Logger.info(this.getClass(),"FETCH CUSTOMER LIST API CALL STARTED AT "+dateFormat.format(new Date()));
		return retailerBusiness.fetchCustomerList(token,customer_type);
	}
}
