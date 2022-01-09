package com.hyperapps.constants;

public interface RetailerQueryConstants {
	
	String STORE_ID_CHECK = "select count(1) from profiles where id	= ?";
	
	String GET_USER_DETAILS = "select u.id,u.name,u.email,u.parent_id,u.user_group,u.store_id,u.is_owner,w.message,w.designation from users u,welcomes w where u.store_id = w.store_id and u.remember_token = ?";
	
	String GET_STORE_PROFILE_DETAILS = "select p.business_name,p.business_short_desc,p.business_long_desc,p.status,p.id,p.user_image from users u,profiles p where u.id = p.user_id and u.store_id = p.id and u.id = ?";
	
	String GET_TEAM_MEMBERS_DETAILS = "select u.id,u.name,u.email,u.status,u.mobile,u.parent_id,u.user_group,u.created_at,u.updated_at from users u where u.parent_id = ?";

	String GET_PROFILE_DETAILS = "select p.id,p.business_name,p.business_short_desc,p.business_long_desc,p.store_category_ids,p.physical_store_status,p.physical_store_address,p.business_phone,p.business_operating_mode,p.business_operating_timings,p.status,p.created_at,p.updated_at,p.user_id,p.user_image,p.store_tax_status,p.store_tax_percentage,p.store_tax_gst from profiles p where p.id = (select u.store_id from users u where u.id = ?)";	

	String GET_CATEGORY_DETAILS = "select r.id,r.name,r.image_path,i.active,r.category_status from rootcategories r,invrootcategories i where r.id = i.rootcategory_id and r.id = ? and i.store_id = ?";
	
	String UPDATE_PROFILE_DETAILS = "UPDATE profiles SET business_name=?, business_short_desc=?, business_long_desc=?, store_category_ids=?, physical_store_status=?, physical_store_address=?, business_phone=?, business_operating_mode=?, business_operating_timings=?, status=?,updated_at=current_timestamp, user_id=?,user_image=? WHERE id=?";
	
	String MOBILE_NUM_EXIST_CHECK = "select count(1) from users where mobile=?";
	
	String EMAIL_EXIST_CHECK = "select count(1) from users where email=?";
	
	String GET_PARENTUSER_DETAILS = "SELECT id,name,is_owner, team_member_count FROM users WHERE id=?";
	
	String ADD_TEAM_MEMBER = "INSERT INTO users (name,email,status,mobile,parent_id,user_group,is_owner,team_member_count,store_id,password,created_at,updated_at) VALUES"
			+ " (?,?,1,?,?,?,0,0,?,?,current_timestamp,current_timestamp)";
	
	String UPDATE_TEAM_COUNT = "update users set team_member_count = team_member_count + 1 where id = ?";
	
	String REMOVE_TEAM_MEMBER = "DELETE FROM users WHERE email=? and id=?";
	
	String ADD_CUSTOMER = "INSERT INTO customers (customers_firstname,customers_email_address,customers_telephone) VALUES(?,?,?)";
	
	String ADD_FEEDBACK = "INSERT INTO feedback (message,store_id) VALUES(?,(select store_id from users where id=?))";
	
	String GET_CUSTOMER_LIST = "SELECT id, customers_gender, customers_firstname, customers_lastname,  DATE_FORMAT(customers_dob,'%Y-%m-%d') as customers_dob,"
			+ " customers_email_address, customers_default_address_id, customers_telephone, customers_fax,"
			+ " customers_password, customers_newsletter,  DATE_FORMAT(created_at,'%Y-%m-%d') as created_at, DATE_FORMAT(updated_at,'%Y-%m-%d') as updated_at, otp, `type`, enable, "
			+ "customer_type, custom_message, store_id, is_selected FROM customers";
	
}


