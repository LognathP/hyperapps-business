package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.UserBusiness;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.CommonDataResponse;
import com.hyperapps.model.CommonResponse;
import com.hyperapps.model.CommonSingleResponse;
import com.hyperapps.model.User;

@RestController
public class UserController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	UserBusiness userBusiness;

	@PostMapping("/api/usertype/add")
	public Object addUserType(@RequestBody User userType) {
		Logger.info(this.getClass(),"ADD USER API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.addUser(userType);
		
	}
	@PostMapping("/api/usertype/edit")
	public Object editUserType(@RequestBody User userType) {
		Logger.info(this.getClass(),"EDIT USER API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.editUser(userType);
		
	}
	@PostMapping("/api/usertype/list")
	public Object listUserType() {
		Logger.info(this.getClass(),"LIST USER API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.getAllUserType();
		
	}
	@PostMapping("/api/usertype/get")
	public Object getUserType(@RequestBody User userType) {
		Logger.info(this.getClass(),"GET USER API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.getUser(userType);
		
	}
	@PostMapping("/api/usertype/delete")
	public Object deleteUserType(@RequestBody User userType) {
		Logger.info(this.getClass(),"DELETE USER API CALL STARTED AT "+dateFormat.format(new Date()));
		return userBusiness.deleteUser(userType);
		
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
