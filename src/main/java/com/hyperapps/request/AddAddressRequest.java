package com.hyperapps.request;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@ToString
public class AddAddressRequest {

	public int store_id;
	public int customer_id;
	public String address_label;
	public String door_no;
	public String street_name;
	public String city_name;
	public String pin_code;
	public String state;
	public String country;
	public String address_latitude;
	public String address_longitude;
}
