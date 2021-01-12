package com.hyperapps.constants;

public interface LoginQueryConstants {
	
	String LOGIN_USER_CHECK = "select count(1) from users where email=? and password=?";
	
	String GET_LOGIN_USER = "select id,status from users where email=?";
	
	String UPDATE_USER_DEVICE_TOKEN = "update user_devicetoken set device_token = ? where user_id = ?";

	
	

}
