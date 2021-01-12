package com.hyperapps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hyperapps.model.User;
import com.hyperapps.model.UserDeviceToken;

@Service
public interface UserDeviceTokenService {
	
	public boolean addUserToken(UserDeviceToken userToken);
	
	

}
