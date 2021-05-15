package com.hyperapps.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileUpdateRequest {

		public int id;
	    public String business_name;
	    public String business_short_desc;
	    public String business_long_desc;
	    public String store_category_ids;
	    public int physical_store_status;
	    public String physical_store_address;
	    public String business_phone;
	    public int business_operating_mode;
	    public String business_operating_timings;
	    public int status;
	    public int user_id;
	    public String category_list;
	    public String userImage;
}
