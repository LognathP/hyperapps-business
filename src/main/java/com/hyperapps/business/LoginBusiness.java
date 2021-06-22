package com.hyperapps.business;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Login;
import com.hyperapps.model.Response;
import com.hyperapps.model.User;
import com.hyperapps.model.UserDeviceToken;
import com.hyperapps.service.LoginService;
import com.hyperapps.service.OtpService;
import com.hyperapps.service.UserDeviceTokenService;
import com.hyperapps.service.RetailerService;
import com.hyperapps.util.DESEncryptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class LoginBusiness {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	OtpService otpService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	RetailerService retailerService;
	
	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;
	
	@Autowired
	UserDeviceTokenService userDeviceTokenService;
	
		
	@SuppressWarnings("unchecked")
	public Object loginRetailer(String email,String password,String device_token)
	{
		LOGGER.info(this.getClass(),"LOGIN RETAILER BUSINESS LAYER");
		Login login = new Login();
		login.setEmail(email);
		login.setDevice_token(device_token);
		login.setPassword(DESEncryptor.encrypt(password, configProp.getConfigValue("enc.key")));
		login = loginService.isNewUser(login);
		if(login.isUserAvailable())
		{
			if(loginService.checkUser(login))
			{
				
				UserDeviceToken ut = new UserDeviceToken();
				ut.setUser_id(Integer.parseInt(login.getUserId()));
				ut.setDevice_token(login.getDevice_token());
				ut.setUser_type(String.valueOf(HyperAppsConstants.RETAILER_USER));
				ut.setDevice_type(String.valueOf(HyperAppsConstants.DEVICE_ANDROID));
				if(!userDeviceTokenService.checkDeviceToken(ut))
				{
					userDeviceTokenService.addDeviceToken(ut);
				}
				else
				{
					userDeviceTokenService.updateDeviceToken(ut);
				}
				login.setLoginToken(getJWTToken(login.getUserId()));
				loginService.updateLoginToken(login);
				LOGGER.info(this.getClass(),"LOGIN CHECK SUCCESS");
				JSONObject js = new JSONObject();
				js.put("token", login.getLoginToken());
				return new ResponseEntity<Object>(js,HttpStatus.OK);
			}
			else
			{
				LOGGER.info(this.getClass(),"LOGIN CHECK FAILED");
				response.setStatus(HttpStatus.UNAUTHORIZED.toString());
				response.setMessage("Invalid Credentials");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
		}
		else
		{
			LOGGER.info(this.getClass(),"LOGIN CHECK FAILED");
			response.setStatus(HttpStatus.UNAUTHORIZED.toString());
			response.setMessage("User Not Found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	
	
	}

	private String getJWTToken(String username) {
		Claims claims = Jwts.claims().setSubject("Generate Token on Request");
		claims.put("loginuser", username);
		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, "secret").compact();
		return token;
	}

	public Object getUserDetails(String token) {
		List<User> userList = new ArrayList<User>();
			userList = retailerService.getUserDetails(token);
			if (userList.size()>0) {
				LOGGER.info(this.getClass(), "USER DETAILS LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("User Details Listed Successfully");
				response.setData(userList);
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
		
}
