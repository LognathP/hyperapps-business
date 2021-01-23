package com.hyperapps.model;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Response {
	
	public String status;
	public String message;
	public String error;
	public Object data;
}
