package com.hyperapps.model;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

	@Qualifier("id")
	@JsonProperty("id")
    public int id;
    @Qualifier("customers_gender")
	@JsonProperty("customers_gender")
    public String customers_gender;
    @Qualifier("customers_firstname")
   	@JsonProperty("customers_firstname")
	public String customers_firstname;
    @Qualifier("customers_lastname")
	@JsonProperty("customers_lastname")
    public String customers_lastname;
    @Qualifier("customers_dob")
	@JsonProperty("customers_dob")
    public String customers_dob;
    @Qualifier("customers_email_address")
	@JsonProperty("customers_email_address")
    public String customers_email_address;
    @Qualifier("customers_default_address_id")
	@JsonProperty("customers_default_address_id")
    public int customers_default_address_id;
    @Qualifier("customers_telephone")
	@JsonProperty("customers_telephone")
    public String customers_telephone;
    @Qualifier("customers_fax")
	@JsonProperty("customers_fax")
    public String customers_fax;
    @Qualifier("customers_password")
	@JsonProperty("customers_password")
    public String customers_password;
    @Qualifier("customers_newsletter")
	@JsonProperty("customers_newsletter")
    public int customers_newsletter;
//    @Qualifier("created_at")
//	@JsonProperty("created_at")
//    public String created_at;
//    @Qualifier("updated_at")
//	@JsonProperty("updated_at")
//    public String updated_at;
//	@JsonProperty("otp")
//	@Qualifier("otp")
//    public String otp;
//    @Qualifier("type")
//	@JsonProperty("type")
//    public String type;
//    @Qualifier("enable")
//	@JsonProperty("enable")
//    public int enable;
}
