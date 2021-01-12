package com.hyperapps.model;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class CustomerAddress {

	public int id;
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
	public String created_at;
	public String updated_at;
	public String deleted_at;
}
