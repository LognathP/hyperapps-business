package com.hyperapps.request;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OrderLocationRequest {

	public String address;
	public String name;
	public String lat;
	public String lng;
	
}
