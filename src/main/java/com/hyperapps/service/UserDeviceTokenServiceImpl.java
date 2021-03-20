package com.hyperapps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.CustomerDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.UserDeviceToken;
import com.hyperapps.repository.UserDeviceTokenRepository;

@Component
public class UserDeviceTokenServiceImpl implements UserDeviceTokenService {

	@Autowired
	CustomerDao customerDao;
		
	@Override
	public boolean checkDeviceToken(UserDeviceToken ut) {
		return customerDao.checkDeviceToken(ut);
	}
	
	@Override
	public void addDeviceToken(UserDeviceToken ut) {
		customerDao.addDeviceToken(ut);
	}



}
