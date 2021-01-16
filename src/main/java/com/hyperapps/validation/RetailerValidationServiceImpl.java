package com.hyperapps.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.dao.RetailerDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Response;
import com.hyperapps.model.User;
import com.hyperapps.service.LoginService;

@Component
public class RetailerValidationServiceImpl implements RetailerValidationService{

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	RetailerDao retailerDao;
	
	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;
	
	@Autowired
	LoginService loginService;
	
	@Override
	public ResponseEntity validateStoreId(int retailerId,ResponseEntity respEntity) {
		try {
			if(!retailerDao.isStoreAvailable(retailerId))
			{
				LOGGER.error(this.getClass(),"STORE NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Store Not Found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<APIResponse>(apiResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE validateStoreId "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}
	
	@Override
	public ResponseEntity<Object> validateToken(String token,ResponseEntity<Object> respEntity) {
		try {
			if(!loginService.validateToken(token))
			{
				LOGGER.error(this.getClass(), "INVALID TOKEN");
				response.setStatus(HttpStatus.FORBIDDEN.toString());
				response.setMessage("Invalid Token");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE validateToken "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}
	
	@Override
	public ResponseEntity<Object> parentUserValidation(int userId,ResponseEntity<Object> respEntity) {
		try {
			User user = retailerDao.getParentUserDetails(userId);
			if(user.getUser_id()!=0)
			{
				if(user.getIsOwner()!=1)
				{
					if(user.getTeamMemberCount()>3)
					{
						LOGGER.error(this.getClass(),"TEAM MEMBERS COUNT IS MORE THAN 3");
						response.setStatus(HttpStatus.UNAUTHORIZED.toString());
						response.setMessage("Team Members Count is More than 3");
						response.setError(HyperAppsConstants.RESPONSE_TRUE);
						response.setData(null);
						apiResponse.setResponse(response);
						respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
					}
				}
				else
				{
					LOGGER.error(this.getClass(),"USER IS NOT OWNER TO CREATE CHILD USER");
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
					response.setMessage("User is not Owner to Create Child User");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					response.setData(null);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
				}
				
			}
			else
			{
				LOGGER.error(this.getClass(),"USER NOT FOUND");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("User Not Fount");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE teamMembersCountValidation "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}

	@Override
	public ResponseEntity<Object> isMobileNumberExists(String mobile,ResponseEntity<Object> respEntity) {
		try {
			if(retailerDao.isMobileNumberExists(mobile))
			{
				LOGGER.error(this.getClass(),"MOBILE NUMBER ALREADY EXIST");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Mobile Number Already Exist");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE isMobileNumberExists "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}

	
	@Override
	public ResponseEntity<Object> isEmailIdExists(String email,ResponseEntity<Object> respEntity) {
		try {
			if(retailerDao.isEmailIdExists(email))
			{
				LOGGER.error(this.getClass(),"EMAIL ALREADY EXIST");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Email Already Exist");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE isEmailIdExists "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}
	
	@Override
	public ResponseEntity<Object> userIdExistValidation(int userId,ResponseEntity<Object> respEntity) {
		try {
			User user = retailerDao.getParentUserDetails(userId);
			if(user.getUser_id()==0)
			{
				LOGGER.error(this.getClass(),"USER NOT FOUND");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("User Not Fount");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
						
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE userIdExistValidation "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}
	


	
}
