package com.hyperapps.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CustomerInfo {
	public String customer_name;
	public String customers_email_address;
	public String customers_telephone;
	public String street_address;
	public String suburb;
	public int postcode;
	public String city;
    public String state;
    public String country;
    
}