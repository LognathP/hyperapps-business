package com.hyperapps.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CommonSingleResponse {

	public String status;
	public String message;
	public Object data;
}
