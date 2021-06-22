package com.hyperapps.constants;

public interface StoreQueryConstants {

	public String GET_STORE_LOC_TIME_QUERY = "select d.delivery_areas,p.business_operating_timings from deliveries d,profiles p where p.id = d.store_id and d.store_id=?";
	
	public String GET_ALL_WELCOME_MESSAGE = "select w.id,w.message,w.designation from welcomes w,users u where u.store_id = w.store_id and u.remember_token = ?";
	
	public String UPDATE_WELCOME_MESSAGE = "UPDATE welcomes SET message=?, designation=? WHERE store_id=(select store_id from users where id = ?)";
	
	public String GET_DESIGNATIONS = "select * from designations";
	
	public String GET_DELIVERY_SETTINGS = "select d.id,d.delivery_type,d.min_order_amount,d.delivery_charge,d.free_delivery_above,d.delivery_areas,d.home_delivery,d.store_id from deliveries d where d.store_id = ?";
	
	public String STORE_RUNNING_STATUS_UPDATE = "update profiles set status = ? where id = ?";
	
	public String STORE_TAX_INFO_UPDATE = "update profiles set store_tax_status =?,store_tax_percentage=?,store_tax_gst=? where id = ?";
	
	public String ADD_DELIVERY_TIME_DETAILS = "INSERT INTO deliveries (delivery_type, min_order_amount, delivery_charge, free_delivery_above, delivery_areas, home_delivery, store_id) VALUES(?,?,?,?,?,?,?)";
	
	public String UPDATE_DELIVERY_TIME_DETAILS = "UPDATE deliveries SET delivery_type=?, min_order_amount=?, delivery_charge=?, free_delivery_above=?, delivery_areas=?, home_delivery=? WHERE store_id= ?";
	
	public String GET_OFFER_DETAILS = "select\r\n" + 
			"	o.id,\r\n" + 
			"	o.store_id,\r\n" + 
			"	o.active,\r\n" + 
			"	o.offer_valid,\r\n" + 
			"	o.offer_start_date,\r\n" + 
			"	o.offer_type,\r\n" + 
			"	o.offer_flat_amount,\r\n" + 
			"	o.offer_percentage,\r\n" + 
			"	o.offer_description,\r\n" + 
			"	o.offer_heading,\r\n" + 
			"	o.offer_max_apply_count,\r\n" + 
			"	o.offer_percentage_max_amount\r\n" + 
			"from\r\n" + 
			"	offers o\r\n" + 
			"where\r\n" + 
			"o.store_id = ?\r\n" + 
			"	and o.offer_start_date <= current_date\r\n" + 
			"	and o.offer_valid >= current_date";
	
	public String GET_OLD_OFFER_DETAILS = "select\r\n" + 
			"	o.id,\r\n" + 
			"	o.store_id,\r\n" + 
			"	o.active,\r\n" + 
			"	o.offer_valid,\r\n" + 
			"	o.offer_start_date,\r\n" + 
			"	o.offer_type,\r\n" + 
			"	o.offer_flat_amount,\r\n" + 
			"	o.offer_percentage,\r\n" + 
			"	o.offer_description,\r\n" + 
			"	o.offer_heading,\r\n" + 
			"	o.offer_max_apply_count,\r\n" + 
			"	o.offer_percentage_max_amount\r\n" + 
			"from\r\n" + 
			"	offers o\r\n" + 
			"where\r\n" + 
			"o.store_id = ?\r\n" + 
			"	and o.offer_valid >= current_date";
	
	public String ADD_OFFER_DETAILS = "INSERT INTO offers (offer_heading, offer_description, offer_percentage, offer_flat_amount, offer_type, offer_percentage_max_amount, offer_max_apply_count, offer_start_date, offer_valid, active, store_id) VALUES(?,?,?,?,?,?,?,STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s'),STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s'),?,?)";
	
	public String UPDATE_OFFER_DETAILS = "UPDATE offers SET offer_heading=?, offer_description=?, offer_percentage=?, offer_flat_amount=?, offer_type=?, offer_percentage_max_amount=?, offer_max_apply_count=?, offer_start_date=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s'), offer_valid=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s'), active=? WHERE id=?";
	
	public String RESUME_OFFER = "UPDATE offers SET offer_start_date=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s'), offer_valid=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') WHERE id=?";
	
	public String DELETE_OFFER_DETAILS = "DELETE FROM offers WHERE id=?";
	
	public String GET_REWARD_POINTS = "select reward_point,active from rewardprograms where store_id=?";
	
	public String UPDATE_REWARD_POINTS = "update rewardprograms set reward_point = ? where store_id=?";
	
	public String GET_DEVICE_TOKEN = "select device_token from user_devicetoken where user_id = ?";
	
	public String GET_EMAIL_ID = "select c.customers_email_address from customers c where c.id = ?";
	
	public String GET_BUSINESS_DEVICE_TOKEN = "select ud.device_token from customers c,users u,user_devicetoken ud where c.store_id = u.store_id and u.id = ud.user_id and ud.user_type = 1 and c.id = ?";
}
