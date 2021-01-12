package com.hyperapps.constants;

public interface OrderQueryConstants {
	
	String GET_RETAILER_ORDERS = "select o.id,o.order_details,o.created_at,o.updated_at,o.customer_id,o.status,o.store_id,\r\n" + 
			"o.order_total,o.order_grand_total,o.payment_details,c.customers_firstname,c.customers_email_address,\r\n" + 
			"c.customers_telephone,oda.location, " + 
			"from orders o,customers c,order_delivery_address oda \r\n" + 
			"where o.customer_id = c.id \r\n" + 
			"and o.id = oda.order_id \r\n" + 
			"and o.store_id = ?";
	
	String GET_ORDER_ITEMS_BYID = "select o2.id,o2.order_item_quantity,o2.price_per_unit,o2.item_status,o2.total,\r\n" + 
			"p.name,p.image_path from orderitems o2,products p where o2.product_id = p.id and o2.order_id = ?";
	
	String GET_OFFER_DETAILS_BYID = "select o.id,o.offer_heading,o.offer_description,o.offer_percentage,o.offer_flat_amount,o.offer_type,\r\n" + 
			"	o.offer_start_date,o.offer_valid,o.active,o.store_id from offers o,offer_order oo where o.id = oo.offer_id and oo.order_id = ?";
	
	String GET_CUSTOMER_ORDERS = "select o.id,o.order_details,o.created_at,o.updated_at,o.customer_id,o.status,o.store_id,\r\n" + 
			"o.order_total,o.order_grand_total,o.payment_details,c.customers_firstname,c.customers_email_address,\r\n" + 
			"c.customers_telephone,ca.street_name,ca.pin_code,ca.city_name,ca.state,ca.country,\r\n" + 
			"p.business_name,p.physical_store_address,p.business_phone,p.user_image,\r\n" + 
			"oda.location " + 
			"from orders o,customers c,order_delivery_address oda,customer_addresses ca,profiles p \r\n" + 
			"where o.customer_id = c.id \r\n" + 
			"and o.customer_id = ca.customer_id \r\n" + 
			"and o.id = oda.order_id \r\n" + 
			"and p.id = o.store_id \r\n" + 
			"and oda.address_type = ca.address_label and o.customer_id = ?";
	
	String GET_ORDER_STATUS = "select status from orders where id= ?";
	
	String UPDATE_ORDER_STATUS = "update orders set status= ? where id = ?";
	
	String INSERT_ORDER_TABLE = "insert into orders (created_at,updated_at,customer_id,store_id,status,order_total,order_grand_total,payment_details )\r\n" + 
			"values (current_timestamp,current_timestamp,?,?,?,?,?,?)";
	
	String INSERT_ORDER_ITEMS_TABLE ="insert into orderitems (order_id,order_item_quantity,price_per_unit,product_id,item_status,total,created_at,updated_at) values\r\n" + 
			"(?,?,?,?,?,?,current_timestamp,current_timestamp);";
	
	String INSERT_ORDER_DELIVERY_TABLE = "insert into order_delivery_address (address_type,location,order_id,created_at,updated_at) values (?,?,?,current_timestamp,current_timestamp);";
	
	String INSERT_OFFER_ORDER = "insert into offer_order (offer_id,order_id,created_at,updated_at) values (?,?,current_timestamp,current_timestamp)";
	
	

	}
