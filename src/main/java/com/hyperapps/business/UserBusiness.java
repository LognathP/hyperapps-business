package com.hyperapps.business;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Profile;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.model.Response;
import com.hyperapps.model.Store;
import com.hyperapps.request.ProfileUpdateRequest;
import com.hyperapps.service.LoginService;
import com.hyperapps.service.RetailerService;
import com.hyperapps.validation.RetailerValidationService;

@Component
public class UserBusiness {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	RetailerService userService;
	
	@Autowired
	RetailerValidationService retailerValidationService;
	
	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;
	
	@Autowired
	LoginService loginService;
	
	
	
	public Object updateRunningStatus(int store_id,int running_status) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			if (userService.updateStoreRunningStatus(store_id,running_status)) {
				LOGGER.info(this.getClass(), "STORE RUNNING STATUS UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Store Running status Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE CUSTOMER DETAILS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to update Store Running status");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			}
		return respEntity;
	}
	public Object updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			if (userService.updateTaxInfo(store_id,tax_status,tax_percentage,tax_gst)) {
				LOGGER.info(this.getClass(), "STORE TAX INFO UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Store Tax info Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE TAX INFO");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to update Tax Info");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			}
		return respEntity;
	}
	public Object getStoreBusinesstime(int store_id) {
		ResponseEntity<?> respEntity = null;
		Store store = new Store();
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			List<Business_operating_timings> botList = userService.getStoreBusinessTime(store_id,store).getBusiness_operating_timings();
			if(!botList.isEmpty())
			{
				LOGGER.info(this.getClass(),"STORE BUSINESS TIME RETRIVED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Store Business Time listed Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				response.setData(botList);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			else
			{
				LOGGER.error(this.getClass(),"STORE BUSINESS TIME RETRIEVAL FAILED");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to List Store Business Time");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);	
			}
			
			}
		return respEntity;
	}
	public Object getProfile(int userId, String token) {
		if(loginService.validateLoginToken(userId,token))
		{
			if(loginService.userIdValidation(userId))
			{
				List<Profile> profile = userService.getProfileDetails(userId);
				if (profile.size()!=0) {
					LOGGER.info(this.getClass(), "USER DETAILS LISTED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("User Details Listed Successfully");
					response.setData(profile);
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
				} else {
					LOGGER.error(this.getClass(), "USER DETAILS NOT FOUND");
					response.setStatus(HttpStatus.NOT_FOUND.toString());
					response.setMessage("User Details Not found");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					response.setData(null);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
				}
			}
			else
			{
				LOGGER.error(this.getClass(), "USER NOT FOUND");
				response.setStatus(HttpStatus.FORBIDDEN.toString());
				response.setMessage("User Not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			
		}
		else
		{
			LOGGER.error(this.getClass(), "INVALID TOKEN");
			response.setStatus(HttpStatus.FORBIDDEN.toString());
			response.setMessage("Invalid Token");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	}
	public Object updateUserProfile(int user_Id, String token, String userId, String business_name,
			String business_short_desc, String business_long_desc, String store_category_ids, int physical_store_status,
			String physical_store_address, String business_phone, int business_operating_mode,
			String business_operating_timings, int store_id) {
		ProfileUpdateRequest prof = new ProfileUpdateRequest();
		prof.setUser_id(user_Id);
		prof.setBusiness_name(business_name);
		prof.setBusiness_short_desc(business_short_desc);
		prof.setBusiness_long_desc(business_long_desc);
		prof.setPhysical_store_status(physical_store_status);
		prof.setPhysical_store_address(physical_store_address);
		prof.setBusiness_operating_mode(business_operating_mode);
		prof.setBusiness_operating_timings(business_operating_timings);
		prof.setBusiness_phone(business_phone);
		prof.setStore_category_ids(store_category_ids);
		prof.setId(store_id);
		if (loginService.validateLoginToken(user_Id,token)) {
			if (userService.updateUserProfile(prof,0)) {
				LOGGER.info(this.getClass(), "USER PROFILE UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("User Profile Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE USER DETAILS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to updated User Details");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		} else {
			LOGGER.error(this.getClass(), "INVALID TOKEN");
			response.setStatus(HttpStatus.FORBIDDEN.toString());
			response.setMessage("Invalid Token");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		}
		
	}
	
	public Object updateUserProfileImage(int user_Id, String token, String userId, String business_name,
			String business_short_desc, String business_long_desc, String store_category_ids, int physical_store_status,
			String physical_store_address, String business_phone, int business_operating_mode,
			String business_operating_timings,String userImage,int store_id) {
		ProfileUpdateRequest prof = new ProfileUpdateRequest();
		prof.setUser_id(user_Id);
		prof.setBusiness_name(business_name);
		prof.setBusiness_short_desc(business_short_desc);
		prof.setBusiness_long_desc(business_long_desc);
		prof.setPhysical_store_status(physical_store_status);
		prof.setPhysical_store_address(physical_store_address);
		prof.setBusiness_operating_mode(business_operating_mode);
		prof.setBusiness_operating_timings(business_operating_timings);
		prof.setBusiness_phone(business_phone);
		prof.setStore_category_ids(store_category_ids);
		prof.setId(store_id);
		prof.setUserImage(userImage);
		if (loginService.validateLoginToken(user_Id,token)) {
			if (userService.updateUserProfile(prof,1)) {
				LOGGER.info(this.getClass(), "USER PROFILE UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("User Profile Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE USER DETAILS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to updated User Details");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		} else {
			LOGGER.error(this.getClass(), "INVALID TOKEN");
			response.setStatus(HttpStatus.FORBIDDEN.toString());
			response.setMessage("Invalid Token");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		}
		
	}
	
	
}
