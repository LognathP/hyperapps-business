package com.hyperapps.constants;

public interface RetailerQueryConstants {
	
	String STORE_ID_CHECK = "select count(1) from profiles where id	= ?";
	
	String STORE_RUNNING_STATUS_UPDATE = "update profiles set business_operating_mode = ? where id = ?";
	
	String STORE_TAX_INFO_UPDATE = "update profiles set store_tax_status =?,store_tax_percentage=?,store_tax_gst=? where id = ?";

	}

