package com.hyperapps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.LoginDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Login;

@Component
public class LoginServiceImpl implements LoginService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	LoginDao loginDao;
	
	
	@Override
	public boolean checkUser(Login login) {
		boolean status = false;
		try {
			status = loginDao.checkUser(login);
		} catch (Exception e) {
			status = false;
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE checkUser "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}


	@Override
	public Login isNewUser(Login login) {
		try {
			login = loginDao.isNewUser(login);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE isNewUser "+e.getMessage().toString());
			e.printStackTrace();
		}
		return login;
	}


	@Override
	public void updateDeviceToken(Login login) {
		try {
			loginDao.updateDeviceToken(login);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE updateDeviceToken "+e.getMessage().toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateLoginToken(Login login) {
		loginDao.updateLoginToken(login);
	}


	@Override
	public boolean validateLoginToken(int userId,String token) {
		return loginDao.validateLoginToken(userId,token);
	}
	
	@Override
	public boolean userIdValidation(int userId) {
		return loginDao.userIdValidation(userId);
	}

	@Override
	public boolean validateToken(String token) {
		return loginDao.validateToken(token);
	}


	

}
