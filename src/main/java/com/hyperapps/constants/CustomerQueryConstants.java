package com.hyperapps.constants;

public interface CustomerQueryConstants {
	
	String LOGIN_CUSTOMER_CHECK = "select id from customers where customers_telephone=?";
	
	String CUSOMER_ADDRESS_INSERT = "insert into customer_addresses (customer_id,address_label,door_no,street_name,pin_code,city_name,state,country,address_latitude,address_longitude,created_at,updated_at ) values\r\n" + 
			"(?,?,?,?,?,?,?,?,?,?,current_timestamp,current_timestamp)";
	
	String CUSTOMER_ADDRESS_UPDATE = "update customer_addresses set customer_id=?,address_label=?,door_no=?,street_name=?,pin_code=?,"
			+ "city_name=?,state=?,country=?,address_latitude=?,address_longitude=?,updated_at=current_timestamp where id=?";
	
	String CUSTOMER_ADDRESS_DELETE = "delete from customer_addresses where id=?";
	
	String GET_CUSTOMER_ADDRESS = "select * from customer_addresses where customer_id= ?";
	
	String CUSTOMER_EXIST_CHECK_QUERY = "select count(1) from customers where id=?";
	
	String CUSTOMER_ADDRESS_EXIST_CHECK_QUERY = "select count(1) from customer_addresses where id=?";
	
	String GET_ALL_CATEGORIES = "select r.id,r.name,r.image_path,r.active from rootcategories r,invrootcategories i where r.id = i.rootcategory_id\r\n" + 
			" and r.active = 1 and i.active = 1 and i.store_id = ?";
	
	String GET_ALL_PARENT_CATEGORIES = "select p.id,p.name,p.image_path from parentcategories p,invparentcategories i where p.id = i.parentcategory_id and  p.active = 1 and i.active = 1 and i.rootcategory_id = ? and i.store_id = ?";
	
	String GET_ALL_CHILD_CATEGORIES = "select c.id,c.name,c.image_path from childcategories c ,invchildcategories i where c.parentcategory_id = i.parentcategory_id  and c.active = 1 and i.active = 1 and i.parentcategory_id = ? and i.store_id = ?";

	String GET_ALL_PRODUCTS = "select p.id,p.name,p.image_path from products p,invproducts i where p.id = i.product_id and p.category_id = i.category_id\r\n" + 
			"and p.active = 1 and i.active = 1 and p.category_id = ? and i.store_id = ?";
	
	String GET_CUSTOMER_PROFILE = "SELECT id, customers_gender, customers_firstname, customers_lastname, DATE_FORMAT(customers_dob,'%Y-%m-%d') as customers_dob, customers_email_address, customers_default_address_id, customers_telephone, customers_fax, customers_password, customers_newsletter FROM customers WHERE id=?";
	
	String UPDATED_CUSTOMER_PROFILE = "update customers set customers_firstname=?,customers_lastname=?,customers_dob=STR_TO_DATE(?,'%Y-%m-%d'),customers_email_address=?,customers_telephone=?,customers_default_address_id=?,customers_fax=?,customers_password=?,customers_newsletter=?,customers_gender=? where id = ?";

	String GET_STORE_DETAILS = "select p.id,p.business_name,p.business_short_desc,p.user_image,p.business_long_desc,p.business_operating_mode,p.physical_store_status,\r\n" + 
			"p.physical_store_address,p.business_phone,p.business_operating_mode,p.business_operating_timings,d.delivery_areas,d.home_delivery,\r\n" + 
			"d.min_order_amount,d.delivery_charge,p.store_tax_status,p.store_tax_percentage,p.store_tax_gst,p.status from profiles p,deliveries d where p.id = d.store_id \r\n" + 
			"and p.id = ?";
	
	
	String GET_SLIDER_DETAILS = "select store_id,image_path,productids from slider_image where store_id=?";
	
	String GET_PRODUCT_DETAILS_BY_ID = "select p.id,p.name,p.category_id,p.description,p.image_path,p.active,i.product_id,i.store_id,i.price,\r\n" + 
			"i.special_price,i.promotional_price,i.weight,i.color,i.`size`,i.quantity,i.option1,i.option2 from products p,invproducts i where p.id = i.product_id and p.id = ?";
	
	String GET_PROMOTIONS_DETAILS = "select p.promotionurl,p.promotion_title,p.discount_percentage,p.image,p.store_id,p.productids from promotions p where p.store_id = ?";

	String GET_PRODUCT_DETAILS_BY_STOREID = "select p.id,p.name,p.category_id,p.description,p.image_path,p.active,i.product_id,i.store_id,i.price,\r\n" + 
			"i.special_price,i.promotional_price,i.weight,i.color,i.`size`,i.quantity,i.option1,i.option2 from products p,invproducts i where p.id = i.product_id and i.store_id = ? and p.name like ?";
	
	
	String GET_STORE_CHILD_CATEGORY_BY_STORE="select c.id,c.rootcategory_id,c.parentcategory_id,c.active,c.name,c.image_path,c.IsDummy from childcategories c,invchildcategories i where c.rootcategory_id = i.rootcategory_id and c.parentcategory_id = i.parentcategory_id and c.rootcategory_id = ? and  c.parentcategory_id = ? and i.store_id=? ";
	
	public String GET_PRODUCT_DETAILS_BY_CATEGORY= "select p.id,p.name,p.category_id,p.description,p.image_path,p.active,i.product_id,i.store_id,i.price,\r\n" + 
			"i.special_price,i.promotional_price,i.weight,i.color,i.`size`,i.quantity,i.option1,i.option2 from products p,invproducts i where p.id = i.product_id and i.store_id = ? and i.category_id = ?";
	

	String CHECK_DEVICETOKEN_EXISTS = "select count(1) from user_devicetoken where user_id = ?";
	
	String ADD_DEVICETOKEN = "INSERT INTO user_devicetoken (user_id, device_token, device_type, user_type, created_at, updated_at) VALUES(?, ?, ?, ?, current_timestamp, current_timestamp)";

	String UPDATE_DEVICE_TOKEN = "UPDATE user_devicetoken SET device_token=?, device_type=?, user_type=?,updated_at=current_timestamp WHERE user_id=?";
}

