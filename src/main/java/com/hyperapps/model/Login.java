package com.hyperapps.model;

import javax.persistence.Convert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hyperapps.util.AttributeEncryptor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Login {

	String email;
	String password;
	String device_token;
	String userId;
	boolean isUserAvailable;
	boolean isLoginSuccess;
	String userStatus;
	boolean isUserCreated;
	String loginToken;
}
