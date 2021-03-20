package com.hyperapps.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hyperapps.model.Login;

@Component
public interface LoginDao {
	
	public boolean checkUser(Login login);

	public Login isNewUser(Login login);

	public void updateDeviceToken(Login login);

	public void updateLoginToken(Login login);

	public boolean validateLoginToken(int userId,String token);
	
	public boolean userIdValidation(int userId);

	public boolean validateToken(String token);

	public int getExistingUserDevicetokenId(int userId);
	
	}
