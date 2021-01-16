package com.hyperapps.business;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Category;
import com.hyperapps.model.Response;
import com.hyperapps.service.StoreService;
import com.hyperapps.service.RetailerService;
import com.hyperapps.validation.RetailerValidationService;

@Component
public class StoreBusiness {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	RetailerValidationService retailerValidationService;
	
	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;
	
	@Autowired
	RetailerService retailerService;
	
	
	
	public Object getStoreCategoryList(int storeId, String token) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			List<Category> catList = storeService.getStoreCategoryList(storeId);
			if (catList.size() != 0) {
				LOGGER.info(this.getClass(), "CATEGORIES LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Categories Listed Successfully");
				JSONObject jb1 = new JSONObject();
				jb1.put("data", catList);
				JSONObject jb = new JSONObject();
				jb.put("next_page_url", "etst");
				jb.put("category_list", jb1);
				response.setData(new JSONArray().put(jb.toMap()).toList());
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "CATEGORIES NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Categories Not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}

		}
		return respEntity;
	}


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
	
	
	
}
