package com.hyperapps.model;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {

	@Qualifier("status")
	@JsonProperty("status")
    public Integer status;
	@Qualifier("message")
	@JsonProperty("message")
    public String message;
	@Qualifier("result")
	@JsonProperty("result")
    public Result result;
	@Qualifier("errorCode")
	@JsonProperty("errorCode")
    public Object errorCode;
	@Qualifier("responseCode")
	@JsonProperty("responseCode")
    public Object responseCode;
}
