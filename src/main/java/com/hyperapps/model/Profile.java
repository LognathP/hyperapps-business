package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {

	    public int id;
	    public String business_name;
	    public String business_short_desc;
	    public String business_long_desc;
	    public String store_category_ids;
	    public int physical_store_status;
	    public String physical_store_address;
	    public List<Business_phone> business_phone;
	    public int business_operating_mode;
	    public List<Business_operating_timings> business_operating_timings;
	    public int status;
	    public String created_at;
	    public String updated_at;
	    public int user_id;
	    public String user_image;
	    public List<Category> category_list;
	    public int store_tax_status;
	    public String store_tax_percentage;
	    public String store_tax_gst;

	    @Getter
	    @Setter
	    public static class Business_phone {
	        public String phone;
	    }

	    @Getter
	    @Setter
	    public static class Business_operating_timings {
	        public String day;
	        public String from;
	        public String to;
	    }
}
