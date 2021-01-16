package com.hyperapps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hyperapps.model.Login;
import com.hyperapps.model.User;

@Service
public interface LoginService {
	
	public boolean checkUser(Login login);

	public Login isNewUser(Login login);

	public void updateDeviceToken(Login login);

	public void updateLoginToken(Login login);

	public boolean validateLoginToken(int userId,String token);

	public boolean userIdValidation(int userId);
	
	public boolean validateToken(String token);


}
