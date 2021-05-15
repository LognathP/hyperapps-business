package com.hyperapps.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {
	
	int id;
    public String store_id;
    public String custom_message;
    public String customer_type;
    public String enable;
    public String type;
    public String otp;
    public String updated_at;
    public String created_at;
    public String customers_newsletter;
    public String customers_password;
    public String customers_fax;
    public String customers_telephone;
    public String customers_default_address_id;
    public String customers_email_address;
    public String customers_dob;
    public String customers_lastname;
    public String customers_firstname;
    public String customers_gender;
    public boolean isSelected;

}
