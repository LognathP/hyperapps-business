package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.StoreBusiness;
import com.hyperapps.logger.HyperAppsLogger;

@RestController
public class StoreController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	StoreBusiness storeBusiness;

	@GetMapping("/api/retailer/category/getStoreCategory/{storeId}")
	public Object getStoreCategoryList(@PathVariable ("storeId") int storeId,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"GET STORE CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.getStoreCategoryList(storeId,token);
	}
	
	@PostMapping(value = "/api/retailer/team/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object addTeamMember(@RequestParam String token,@RequestParam int store_id,
			@RequestParam int user_id,@RequestParam String name,@RequestParam String mobile,
			@RequestParam int access_type,@RequestParam String email) {
		Logger.info(this.getClass(),"ADD TEAM MEMBER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.addTeamMember(token,store_id,user_id,name,mobile,access_type,email);
	}
	
	@PostMapping(value = "/api/retailer/team/remove",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object removeTeamMember(@RequestParam String token,@RequestParam int user_id,
			@RequestParam String email) {
		Logger.info(this.getClass(),"REMOVE TEAM MEMBER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.removeTeamMember(token,user_id,email);
	}
	
	
}
