package com.hyperapps.constants;

public interface StoreQueryConstants {

	public String GET_STORE_LOC_TIME_QUERY = "select d.delivery_areas,p.business_operating_timings from deliveries d,profiles p where p.id = d.store_id and d.store_id=?";
	
	public String GET_ALL_CATEGORIES = "select r.id,r.name,r.image_path,r.active,r.category_status from rootcategories r,invrootcategories i where r.id = i.rootcategory_id\r\n" + 
			" and r.active = 1 and i.active = 1 and i.store_id = ?";
}
