package com.hyperapps.business;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.BusinessOperatingTimings;
import com.hyperapps.model.CommonDataResponse;
import com.hyperapps.model.CommonResponse;
import com.hyperapps.model.CommonSingleResponse;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;
import com.hyperapps.service.UserService;
import com.hyperapps.validation.RetailerValidationService;

@Component
public class UserBusiness {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	UserService userTypeService;
	
	@Autowired
	CommonResponse commonResponse;
	
	@Autowired
	RetailerValidationService retailerValidationService;
	
	@Autowired
	CommonDataResponse commonDataResponse;
	
	@Autowired
	CommonSingleResponse commonSingleResponse;
	
	
	public ResponseEntity<CommonResponse> addUser(User userType)
	{
		LOGGER.info(this.getClass(),"USER TYPE ADD BUSINESS LAYER");
		if(userTypeService.addUserType(userType))
		{
			LOGGER.info(this.getClass(),"USER TYPE ADDED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("User Type added");
			return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(),"USER TYPE ADD FAILED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Error occured, Please try Again");
			return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);	
		}
	}
	public ResponseEntity<CommonResponse> editUser(User userType)
	{
		LOGGER.info(this.getClass(),"USER TYPE EDIT BUSINESS LAYER");
		if(userTypeService.addUserType(userType))
		{
			LOGGER.info(this.getClass(),"USER TYPE UPDATED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("User Type updated");
			return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(),"USER TYPE EDIT FAILED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Error occured, Please try Again");
			return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);	
		}
	}
	public ResponseEntity<CommonSingleResponse> getUser(User userType)
	{
		LOGGER.info(this.getClass(),"USER TYPE GET BUSINESS LAYER");
		Optional<User> userTyp = userTypeService.getUserType(userType);
		if(userTyp.isPresent())
		{
			LOGGER.info(this.getClass(),"USER TYPE RETRIEVED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("User Type Retrieved");
			userType = userTyp.get();
			commonSingleResponse.setData(userType);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse,HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(),"USER TYPE RETRIEVAL FAILED");
			commonSingleResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonSingleResponse.setMessage("Error occured, Please try Again");
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse,HttpStatus.OK);	
		}
	}
	public ResponseEntity<CommonResponse> deleteUser(User userType)
	{
		LOGGER.info(this.getClass(),"USER TYPE DELETE BUSINESS LAYER");
		if(userTypeService.deleteUserType(userType))
		{
			LOGGER.info(this.getClass(),"USER TYPE DELETED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("User Type deleted");
			return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(),"USER TYPE DELETE FAILED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Error occured, Please try Again");
			return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);	
		}
	}
	public ResponseEntity<CommonDataResponse> getAllUserType()
	{
		LOGGER.info(this.getClass(),"USER TYPE RETRIEVE BUSINESS LAYER");
		List<User> userTypeList = userTypeService.getAllUserType();
		if(!userTypeList.isEmpty())
		{
			LOGGER.info(this.getClass(),"USER TYPE RETRIVED SUCCESSFULLY");
			commonDataResponse.setStatus(HttpStatus.OK.toString());
			commonDataResponse.setMessage("User Type listed");
			commonDataResponse.setData(userTypeList);
			return new ResponseEntity<CommonDataResponse>(commonDataResponse,HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(),"USER TYPE RETRIEVAL FAILED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Error occured, Please try Again");
			return new ResponseEntity<CommonDataResponse>(commonDataResponse,HttpStatus.OK);		
		}
		
		
		
	}
	public Object updateRunningStatus(int store_id,int running_status) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			if (userTypeService.updateStoreRunningStatus(store_id,running_status)) {
				LOGGER.info(this.getClass(), "STORE RUNNING STATUS UPDATED SUCCESSFULLY");
				commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("Store Running status Updated Successfully");
				respEntity = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE CUSTOMER DETAILS");
				commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				commonResponse.setMessage("Unable to update Store Running status");
				respEntity = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			}
			}
		return respEntity;
	}
	public Object updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			if (userTypeService.updateTaxInfo(store_id,tax_status,tax_percentage,tax_gst)) {
				LOGGER.info(this.getClass(), "STORE TAX INFO UPDATED SUCCESSFULLY");
				commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("Store Tax info Updated Successfully");
				respEntity = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE TAX INFO");
				commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				commonResponse.setMessage("Unable to update Tax Info");
				respEntity = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			}
			}
		return respEntity;
	}
	public Object getStoreBusinesstime(int store_id) {
		ResponseEntity<?> respEntity = null;
		Store store = new Store();
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			List<BusinessOperatingTimings> botList = userTypeService.getStoreBusinessTime(store_id,store).getBusiness_operating_timings();
			if(!botList.isEmpty())
			{
				LOGGER.info(this.getClass(),"STORE BUSINESS TIME RETRIVED SUCCESSFULLY");
				commonDataResponse.setStatus(HttpStatus.OK.toString());
				commonDataResponse.setMessage("Store Business Time listed");
				commonDataResponse.setData(botList);
				return new ResponseEntity<CommonDataResponse>(commonDataResponse,HttpStatus.OK);
			}
			else
			{
				LOGGER.error(this.getClass(),"STORE BUSINESS TIME RETRIEVAL FAILED");
				commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				commonResponse.setMessage("Error occured, Please try Again");
				return new ResponseEntity<CommonDataResponse>(commonDataResponse,HttpStatus.OK);		
			}
			
			}
		return respEntity;
	}
}
