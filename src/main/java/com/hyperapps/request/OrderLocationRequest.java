package com.hyperapps.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class OrderLocationRequest {

	public String address;
	public String name;
	public String lat;
	public String lng;
	
}
