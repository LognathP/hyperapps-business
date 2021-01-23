package com.hyperapps.business;


import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Customer;
import com.hyperapps.model.Profile;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.model.Response;
import com.hyperapps.model.Store;
import com.hyperapps.model.WelcomeMessage;
import com.hyperapps.request.ProfileUpdateRequest;
import com.hyperapps.service.LoginService;
import com.hyperapps.service.RetailerService;
import com.hyperapps.util.ResponseKeys;
import com.hyperapps.validation.RetailerValidationService;

@Component
public class RetailerBusiness {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	RetailerService retailerService;
	
	@Autowired
	RetailerValidationService retailerValidationService;
	
	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;
	
	@Autowired
	LoginService loginService;
	
	public Object addTeamMember(String token, int store_id, int user_id, String name, String mobile, int access_type,
			String email) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if ((respEntity = retailerValidationService.parentUserValidation(user_id, respEntity)) == null) {
				if ((respEntity = retailerValidationService.isMobileNumberExists(mobile, respEntity)) == null) {
					if ((respEntity = retailerValidationService.isEmailIdExists(email, respEntity)) == null) {
						if (retailerService.addTeamMember(store_id, user_id, name, mobile, access_type, email)) {
							LOGGER.info(this.getClass(), "MEMBER ADDED SUCCESSFULLY");
							response.setStatus(HttpStatus.OK.toString());
							response.setMessage("Member Added Successfully");
							response.setData(null);
							response.setError(HyperAppsConstants.RESPONSE_FALSE);
							apiResponse.setResponse(response);
							return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
						} else {
							LOGGER.error(this.getClass(), "UNABLE TO ADD MEMBER");
							response.setStatus(HttpStatus.NOT_FOUND.toString());
							response.setMessage("Unable to Add Member");
							response.setError(HyperAppsConstants.RESPONSE_TRUE);
							response.setData(null);
							apiResponse.setResponse(response);
							return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
						}
					}
				}
			}
		}

		return respEntity;
	}

	public Object removeTeamMember(String token, int user_id, String email) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if ((respEntity = retailerValidationService.userIdExistValidation(user_id, respEntity)) == null) {
				if (retailerService.removeTeamMember(user_id, email)) {
					LOGGER.info(this.getClass(), "MEMBER REMOVED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("Member Removed Successfully");
					response.setData(null);
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				} else {
					LOGGER.error(this.getClass(), "UNABLE TO REMOVE MEMBER");
					response.setStatus(HttpStatus.NOT_FOUND.toString());
					response.setMessage("Unable to Remove Member");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					response.setData(null);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				}

			}
		}

		return respEntity;
	}
	
	public Object getProfile(int userId, String token) {
		if(loginService.validateLoginToken(userId,token))
		{
			if(loginService.userIdValidation(userId))
			{
				List<Profile> profile = retailerService.getProfileDetails(userId);
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
		LOGGER.info(this.getClass(), "UPDATE PROFILE BUSINESS LAYER");
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
		System.out.println(prof.toString());
		if (loginService.validateLoginToken(user_Id,token)) {
			if (retailerService.updateUserProfile(prof,0)) {
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
			if (retailerService.updateUserProfile(prof,1)) {
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

	public Object addCustomer(String token, int user_id, String customers_firstname, String customers_telephone,
			String customers_email_address) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if (retailerService.addCustomer(customers_firstname,customers_telephone,customers_email_address)) {
				LOGGER.info(this.getClass(), "CUSTOMER ADDED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Customer Added Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				respEntity =  new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO ADD CUSTOMER DETAILS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to Add Customer");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				respEntity =  new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}
		return respEntity;
	}

	public Object addfeedback(String token, int user_id, String details) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if (retailerService.addfeedback(user_id,details)) {
				LOGGER.info(this.getClass(), "FEEDBACK ADDED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Feedback Added Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				respEntity =  new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO ADD FEEDBACK");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to Add Feedback");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				respEntity =  new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}
		return respEntity;
	}

	public Object fetchCustomerList(String token, int customer_type) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			List<Customer> customerList = retailerService.fetchCustomerList(customer_type);
			if (customerList.size() != 0) {
				LOGGER.info(this.getClass(), "CUSTOMER LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Customer Listed Successfully");
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put(ResponseKeys.next_page_url, "etst");
				data.put(ResponseKeys.data, customerList);
				response.setData(new JSONObject(data).toMap());
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "CUSTOMERS NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Customers Not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}

		}
		return respEntity;
	}
	
	
}
