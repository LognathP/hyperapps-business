package com.hyperapps.constants;

public interface StoreQueryConstants {

	public String GET_STORE_LOC_TIME_QUERY = "select d.delivery_areas,p.business_operating_timings from deliveries d,profiles p where p.id = d.store_id and d.store_id=?";
}
