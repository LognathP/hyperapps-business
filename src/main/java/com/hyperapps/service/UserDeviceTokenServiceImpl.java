package com.hyperapps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.User;
import com.hyperapps.model.UserDeviceToken;
import com.hyperapps.repository.UserDeviceTokenRepository;
import com.hyperapps.repository.UserRepository;

@Component
public class UserDeviceTokenServiceImpl implements UserDeviceTokenService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	UserDeviceTokenRepository userDeviceTokenRepository;
	
	
	@Override
	public boolean addUserToken(UserDeviceToken userToken) {
		boolean status = false;
		try {
			userDeviceTokenRepository.save(userToken);
			status = true;
		} catch (Exception e) {
			status = false;
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE ADDING/UPDATING USER TOKEN  "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}



}
